//package org.tejo.iza.client
//
//import pl.pej.trelloilaro.api.model._
//import pl.pej.trelloilaro.api.requestBuilder._
//
//import scala.concurrent.{Await, Future}
//
//case class TrelloClientFacade(trelloApiKey: String, boardId: String) {
//
//
//  val client = new TrelloHttpClient(trelloApiKey)
//
//  def facts: Future[TrelloFacts] =
//    TrelloFacts(
//      board = boardFact,
//      lists = listFacts,
//      cards = cardFacts,
//      checklists = checklistFacts
//    )
//
//  def factsBlocking: TrelloFacts = {
//    import scala.concurrent.duration._
//    Await.result(facts, 10 seconds)
//  }
//
//  def boardFact: Future[BoardFact] = {
//    val boardFuture: Future[BoardJson] = client.getBoard(GetBoard(boardId))
//
//    boardFuture.map { json =>
//      BoardFact(json)
//    }
//  }
//
//  def checklistFacts: Future[List[ChecklistFact]] = {
//    val checklistFactsFuture: Future[List[ChecklistJson]] = client.getBoardChecklists(GetBoardChecklists(boardId))
//
//    checklistFactsFuture.map { json =>
//      json.map(ChecklistFact(_))
//    }
//  }
//
//  def listFacts: Future[List[ListFact]] = {
//    val listFactsFuture: Future[List[ListJson]] = client.getBoardLists(GetBoardLists(boardId))
//
//    listFactsFuture.map { json =>
//      json.map(ListFact(_))
//    }
//  }
//
//  def cardFacts: Future[List[CardFact]] = {
//    val cardFactsFuture: Future[List[CardJson]] = client.getBoardCards(GetBoardCards(boardId))
//
//    cardFactsFuture.map { json =>
//      json.map(CardFact(_))
//    }
//  }
//  def actionFacts: Future[List[ActionFact]] = {
//    val actionFactsFuture: Future[List[ActionJson]] = client.getBoardActions(GetBoardActions(boardId))
//
//    actionFactsFuture.map { json =>
//      json.map(ActionFact(_))
//    }
//  }
//
//}
