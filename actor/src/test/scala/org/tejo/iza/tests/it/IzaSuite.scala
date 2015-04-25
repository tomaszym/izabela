package org.tejo.iza.tests.it

import akka.actor.{ActorRef, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalamock.scalatest.MockFactory
import org.scalatest._
import org.tejo.iza.actor.IzaActor
import org.tejo.iza.actor.cirkulerilo.DissenduActor.Msg.Cirkulero
import org.tejo.iza.actor.cirkulerilo.redaktilo.Redaktilo
import org.tejo.iza.actor.di.IzaActorModule
import org.tejo.iza.actor.ws.TrelloService
import org.tejo.iza.rules.ClojureNamespace
import org.tejo.iza.rules.facts._
import org.tejo.model.TEJO
import scala.concurrent.duration._

import scala.concurrent.Future

class IzaSuite (_system: ActorSystem) extends TestKit(_system) with ImplicitSender with FunSuiteLike with BeforeAndAfterAll with MockFactory with TestData {

  def this() = this(ActorSystem("test"))

  val trelloMock = mock[TrelloService]

  lazy val dissenduProbe = TestProbe()
  
  class TestModule extends IzaActorModule {
    override def actorSystem: ActorSystem = _system

    override def trelloService: TrelloService = trelloMock

    override val redaktilo: Redaktilo = new Redaktilo {
      override def redaktu(kontribuoj: List[CardFact], tejo: TEJO): String = cirkuleroText
    }
    override lazy val dissenduActor: ActorRef = dissenduProbe.ref
  }

  test("integation test") {

//    (trelloMock.actionFacts _).expects(boardId).returning(Future.successful{List[ActionFact]()})
    (trelloMock.boardFact _).expects(boardId).returning(Future.successful{BoardFact(boardId)})
    (trelloMock.cardFacts _).expects(boardId).returning(Future.successful{cardFacts})
    (trelloMock.checklistFacts _).expects(boardId).returning(Future.successful{checklistFacts})
    (trelloMock.listFacts _).expects(boardId).returning(Future.successful{List[ListFact](listFact)})

    val m = new TestModule
    val iza = m.izaActor

    import org.tejo.iza.actor.IzaActor.Msg._

    iza ! ResetWorkingMemory(ClojureNamespace.toLoadCirkuleriloRules::Nil)

    iza ! LoadFacts(boardId)

    iza ! FireRules

    dissenduProbe.expectMsg(20 seconds, Cirkulero(cirkuleroText))
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