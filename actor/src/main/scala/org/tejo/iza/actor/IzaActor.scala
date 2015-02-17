package org.tejo.iza.actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import clara.rules.{QueryResult, RuleLoader, WorkingMemory}
import org.tejo.iza.actor.msg._
import org.tejo.iza.actor.ws.TrelloWS
import play.api.libs.ws.WSClient
import scaldi.Injector
import scaldi.akka.AkkaInjectable
import scala.collection.JavaConversions._

import scala.collection.mutable
import scala.concurrent.Future

/** Provides HTTP client to trelloilaro
  * and creates akka interface to it.
  */
class IzaActor(implicit inj: Injector) extends Actor with AkkaInjectable {

  var workingMemory: WorkingMemory = _

  val wsClient = inject [WSClient]
  val handler = inject [CirkuleroHandler]

  val trello = new TrelloWS(wsClient)
  val executionQueue: mutable.Queue[Any] = mutable.Queue()
  
  override def receive: Receive = {
    case FireRules => workingMemory.fireRules()

    case Query(q) =>
      sender() ! Response(workingMemory.query(q).toList)

    case LoadFactsForBoard(boardId) =>

      val factsFuture = factListFuture(boardId)
      factsFuture.map { facts =>
        workingMemory.insert(facts)
        self ! FactsLoaded
      }
      // facts loading, we do not want to fire in this state
      context.become(loadingFacts)
      
    case FreshWithRules(ruleNames) =>
      workingMemory = RuleLoader.loadRules(ruleNames:_*)
      workingMemory.insert(List(handler))
  }

  private case object FactsLoaded
  def loadingFacts: Receive = {
    case FactsLoaded =>
      processQueue()
      context.unbecome()
    case x: Any => executionQueue.enqueue(x)
  }

  def processQueue(): Unit = {
    executionQueue.dequeue() match {
      case l@LoadFactsForBoard(id) => receive(l)

      case anythingElse: Any =>
        receive(anythingElse)
        processQueue()
    }
  }
  
  // TODO move this function *somewhere*
  def factListFuture(boardId: String): Future[List[_]] = {

    for {
      board <- trello.boardFact(boardId)
      lists <- trello.listFacts(boardId)
      cards <- trello.cardFacts(boardId)
      checklists <- trello.checklistFacts(boardId)
    } yield {
      board :: lists ++ cards ++ checklists
    }
  }

}
