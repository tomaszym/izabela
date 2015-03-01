package org.tejo.iza.actor

import akka.actor.Actor
import akka.actor.Actor.Receive
import clara.rules.{QueryResult, RuleLoader, WorkingMemory}
import org.tejo.iza.actor.msg._
import org.tejo.iza.actor.ws.{FactList, TrelloWS}
import org.tejo.iza.rules.ClaraQuery
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

  import IzaActor.Msg._
  var workingMemory: WorkingMemory = _
  val wsClient = inject [WSClient]

  implicit val trello = new TrelloWS(wsClient)
  val executionQueue: mutable.Queue[Any] = mutable.Queue()

  /** Main Receive - no facts loading.
    */
  override def receive: Receive = {

    case FireRules => workingMemory.fireRules()


    case query: ClaraQuery =>
      sender() ! ClaraQueryResult(query(workingMemory))// TODO messages considering query type


    case LoadFacts(boardId) =>

      val factsFuture = factListFuture(boardId)
      factsFuture.map { facts =>
        workingMemory.insert(facts)
        self ! FactsLoaded
      }
      // facts loading, we do not want to fire in this state
      context.become(loadingFacts)


    case ResetWorkingMemory(ruleNames) =>
      workingMemory = RuleLoader.loadRules(ruleNames:_*)
  }

  private case object FactsLoaded
  
  /** Facts loading state
    */
  def loadingFacts: Receive = {
    case FactsLoaded =>
      processQueue()
      context.unbecome()
    case x: Any => executionQueue.enqueue(x)
  }

  def processQueue(): Unit = {
    executionQueue.dequeue() match {
      case l@LoadFacts(id) => receive(l)

      case anythingElse: Any =>
        receive(anythingElse)
        processQueue()
    }
  }


}

object IzaActor {

  object Msg {

    /** Fires currently loaded rules.  */
    case object FireRules


    /** 1. Purges facts and rules 2. sets new rules
      *
      * @param ruleNames names of rules to be set after reset
      */
    case class ResetWorkingMemory(ruleNames: Array[String])

    case class ClaraQueryResult(res: Any)



    /** When a message is received an actor
      * fetches the facts via HTTP API, deserializes,
      * converts to fact objects and puts into the
      * knowledge base
      *
      * @param boardId
      */
    case class LoadFacts(boardId: String)

  }


}
