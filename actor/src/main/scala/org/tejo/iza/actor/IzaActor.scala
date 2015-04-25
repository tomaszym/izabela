package org.tejo.iza.actor

import akka.actor.{ActorLogging, Actor}
import akka.event.LoggingReceive
import clara.rules.{RuleLoader, WorkingMemory}
import org.tejo.iza.actor.cirkulerilo.KunmetuActor
import org.tejo.iza.actor.msg._
import org.tejo.iza.actor.ws.{FactList, TrelloService}
import org.tejo.iza.rules.ClaraQuery
import scaldi.Injector
import scaldi.akka.AkkaInjectable

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.concurrent.ExecutionContext

/** Provides HTTP client to trelloilaro
  * and creates akka interface to it.
  */
class IzaActor(implicit inj: Injector) extends Actor with AkkaInjectable with FactList with ActorLogging {

  import org.tejo.iza.actor.IzaActor.Msg._
  var workingMemory: WorkingMemory = _

  implicit val ec: ExecutionContext = context.dispatcher

  implicit val trello = inject [TrelloService]
  val executionQueue: mutable.Queue[Any] = mutable.Queue()

  val kunmetuActor = injectActorRef [KunmetuActor]

  val rulesFiredObservers = kunmetuActor :: Nil


  /** Main Receive - no facts loading.
    */
  override def receive: Receive =  LoggingReceive {

    case FireRules =>
      log.debug(s"FireRules on working memory: $workingMemory")
      workingMemory = workingMemory.fireRules()
      rulesFiredObservers.map { actor =>
        actor ! RulesFired
      }


    case query: ClaraQuery[_] =>
      log.debug(s"ClaraQuery on working memory: $workingMemory")
      val res = ClaraQueryResult(query(workingMemory))
      log.debug(s"ClaraQuery results: $res")
      sender() ! res


    case LoadFacts(boardId) =>

      val factsFuture = factListFuture(boardId)
      factsFuture.map { facts =>
        log.debug(s"Inserting into Iza($workingMemory): $facts")
        workingMemory = workingMemory.insert(seqAsJavaList(facts))
        self ! FactsLoaded
      }
      // facts loading, we do not want to fire in this state
      log.debug("Iza goes to loadingFacts state")
      context.become(loadingFacts)


    case ResetWorkingMemory(ruleNames) =>
      workingMemory = RuleLoader.loadRules(ruleNames:_*)
      log.debug(s"ResetWorkingMemory on working memory: $workingMemory")
  }

  private case object FactsLoaded

  /** Facts loading state
    */
  def loadingFacts: Receive = LoggingReceive {
    case FactsLoaded =>
      processQueue()
      log.debug("Iza goes back to previous state")
      context.unbecome()
    case x: Any => executionQueue.enqueue(x)
  }

  def processQueue(): Unit = {
    if(executionQueue.nonEmpty) executionQueue.dequeue() match {
      case l@LoadFacts(id) => receive(l)

      case anythingElse: Any =>
        log.debug(s"ProcessingQueue: $anythingElse")
        receive(anythingElse)
//        this.wait(500)
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
    case class ResetWorkingMemory(ruleNames: List[String])

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
