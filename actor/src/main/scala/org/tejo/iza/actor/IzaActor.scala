package org.tejo.iza.actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import clara.rules.{QueryResult, RuleLoader, WorkingMemory}
import org.tejo.iza.actor.msg._
import org.tejo.iza.actor.ws.{FactList, TrelloWS}
import play.api.libs.ws.WSClient
import scaldi.Injector
import scaldi.akka.AkkaInjectable
import scala.collection.JavaConversions._

import scala.collection.mutable
import scala.concurrent.Future

/** Provides HTTP client to trelloilaro
  * and creates akka interface to it.
  */
class IzaActor(implicit inj: Injector) extends Actor with AkkaInjectable with FactList {

  var workingMemory: WorkingMemory = _

  val wsClient = inject [WSClient]

  implicit val trello = new TrelloWS(wsClient)
  val executionQueue: mutable.Queue[Any] = mutable.Queue()
  
  override def receive: Receive = {
    case FireRules => workingMemory.fireRules()

    case QueryMsg(q) =>
      sender() ! ResponseMsg(workingMemory.query(q.stringId).toList)

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


}
