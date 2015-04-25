package org.tejo.iza.tests.it

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{TestActorRef, TestProbe, TestKit, ImplicitSender}
import org.scalamock.scalatest.MockFactory
import org.scalatest._
import org.tejo.iza.actor.IzaActor
import org.tejo.iza.actor.cirkulerilo.DissenduActor
import org.tejo.iza.actor.cirkulerilo.DissenduActor.Msg.Cirkulero
import org.tejo.iza.actor.cirkulerilo.redaktilo.Redaktilo
import org.tejo.iza.actor.di.Modules.IzaActorModule
import org.tejo.iza.actor.msg.RulesFired
import org.tejo.iza.actor.ws.{TrelloService, TrelloWS}
import org.tejo.iza.rules.ClojureNamespace
import org.tejo.iza.rules.facts._
import org.tejo.model.TEJO
import scaldi.{Module, Injectable}
import scaldi.akka.AkkaInjectable
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class IzaSuite (_system: ActorSystem) extends TestKit(_system) with ImplicitSender with FunSuiteLike with BeforeAndAfterAll with AkkaInjectable with MockFactory with TestData {

  def this() = this(ActorSystem("test"))

  val mainModule = new Module { bind [ActorSystem] to _system } :: new IzaActorModule

  test("integation test") {

    val trelloMock = mock[TrelloService]

//    (trelloMock.actionFacts _).expects(boardId).returning(Future{List[ActionFact]()})
    (trelloMock.boardFact _).expects(boardId).returning(Future{BoardFact(boardId)})
    (trelloMock.cardFacts _).expects(boardId).returning(Future{cardFacts})
    (trelloMock.checklistFacts _).expects(boardId).returning(Future{checklistFacts})
    (trelloMock.listFacts _).expects(boardId).returning(Future{List[ListFact](listFact)})

    lazy val probe = TestProbe()

    lazy val testModule = new Module {

      bind [TrelloService] to trelloMock

//      val dissenduActor =

      bind [Redaktilo] to new Redaktilo {
        override def redaktu(kontribuoj: List[CardFact], tejo: TEJO): String = cirkuleroText
      }

    }

    implicit lazy val module = testModule :: mainModule


    implicit lazy val ec: ExecutionContext = inject [ExecutionContext]

    val iza: TestActorRef[IzaActor] = TestActorRef( new IzaActor )

    val kunmetuActorRef = iza.underlyingActor.kunmetuActor


    probe.watch(kunmetuActorRef)

    import IzaActor.Msg._

    iza ! ResetWorkingMemory(ClojureNamespace.toLoadCirkuleriloRules::Nil)

    iza ! LoadFacts(boardId)


    val cancellable =
      system.scheduler.schedule(2 seconds , 5 seconds, iza, FireRules)

    probe.expectMsg(10 seconds, RulesFired)

//    iza ! FireRules



//    probe.expectMsg(15 seconds, Cirkulero(cirkuleroText))

  }



  override def afterAll() {
    TestKit.shutdownActorSystem(system)
  }

}


trait TestData {

  val boardId = "<boardId>"

  val listId = "<listId>"
  val checklistId = "<checklistId>"
  val stirkartoId = "<cardId>"
  
  val listFact = ListFact(listId, name = "Aktuala")

  val cardFacts: List[CardFact] = List(
    CardFact(stirkartoId, listId, name = "Stirkarto"),
    CardFact("1", listId, "tomasz@tejo.org", desc = "Mi faris malmulte."),
    CardFact("2", listId, "lukasz@tejo.org", desc = "Mi faris multe.")
  )

  val checklistFacts: List[(ChecklistFact, List[CheckItemFact])] = List((
    ChecklistFact(id = checklistId, cardId = stirkartoId),
    List(
      CheckItemFact(idx = 0, id = "", checklistId = checklistId, name = "", pos = 1, complete = true),
      CheckItemFact(idx = 1, id = "", checklistId = checklistId, name = "", pos = 1, complete = true),
      CheckItemFact(idx = 2, id = "", checklistId = checklistId, name = "", pos = 1, complete = true),
      CheckItemFact(idx = 3, id = "", checklistId = checklistId, name = "", pos = 1, complete = true),
      CheckItemFact(idx = 4, id = "", checklistId = checklistId, name = "", pos = 1, complete = true),
      CheckItemFact(idx = 5, id = "", checklistId = checklistId, name = "", pos = 1, complete = false),
      CheckItemFact(idx = 6, id = "", checklistId = checklistId, name = "", pos = 1, complete = false)
    )
  ))



  val cirkuleroText = "Freŝa, bela cirkulero."

}