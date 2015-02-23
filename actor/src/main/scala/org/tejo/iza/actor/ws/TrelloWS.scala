package org.tejo.iza.actor.ws

import com.fasterxml.jackson.databind.RuntimeJsonMappingException
import org.tejo.iza.rules.facts._
import pl.pej.trelloilaro.api.model._
import pl.pej.trelloilaro.api.requestBuilder._
import play.api.libs.json.{JsError, JsSuccess}
import play.api.libs.ws.WSClient

import scala.concurrent.{Await, Future}


class TrelloWS(client: WSClient) {

//    def facts: Future[TrelloFacts] =
//      TrelloFacts(
//        board = boardFact,
//        lists = listFacts,
//        cards = cardFacts,
//        checklists = checklistFacts
//      )
//
//    def factsBlocking: TrelloFacts = {
//      import scala.concurrent.duration._
//      Await.result(facts, 10 seconds)
//    }

  def boardFact(boardId: String): Future[BoardFact] = {
    val request = GetBoard(boardId)
    val boardFuture: Future[BoardJson] = client.url(request.toString()).get().map { response =>

      response.json.validate[BoardJson] match {
        case s: JsSuccess[BoardJson] =>

          s.get
        case e: JsError =>
//            val msg = JsError.toFlatJson(e).toString()
//            logger.error("Json error while trying to parse a Board: " + msg)
//            throw JsonParseErrorException(msg)
          throw new RuntimeJsonMappingException(e.toString)

      }
    }
    boardFuture.map { json =>
      BoardFact(json)
    }
  }

  def checklistFacts(boardId: String): Future[List[(ChecklistFact, List[CheckItemFact])]] = {
    val request = GetBoardChecklists(boardId)

    val checklistFactsFuture: Future[List[ChecklistJson]] = client.url(request.toString()).get().map { response =>

      response.json.validate[List[ChecklistJson]] match {

        case s: JsSuccess[List[ChecklistJson]] => s.get
        case e: JsError => throw new RuntimeJsonMappingException(e.toString)
      }
    }

    checklistFactsFuture.map { json =>
      json.map(ChecklistFact(_))
    }
  }

  def listFacts(boardId: String): Future[List[ListFact]] = {
    val request = GetBoardLists(boardId)
    val listFactsFuture: Future[List[ListJson]] = client.url(request.toString()).get().map { response =>

      response.json.validate[List[ListJson]] match {

        case s: JsSuccess[List[ListJson]] => s.get
        case e: JsError => throw new RuntimeJsonMappingException(e.toString)
      }
    }

    listFactsFuture.map { json =>
      json.map(ListFact(_))
    }
  }

  def cardFacts(boardId: String): Future[List[CardFact]] = {
    val request = GetBoardCards(boardId)
    val cardFactsFuture: Future[List[CardJson]] = client.url(request.toString).get().map { response =>

      response.json.validate[List[CardJson]] match {
        case s: JsSuccess[List[CardJson]] => s.get
        case e: JsError => throw new RuntimeJsonMappingException(e.toString)
      }
    }

    cardFactsFuture.map { json =>
      json.map(CardFact(_))
    }
  }

  def actionFacts(boardId: String): Future[List[ActionFact]] = {
    val request = GetBoardActions(boardId)
    val actionFactsFuture: Future[List[ActionJson]] = client.url(request.toString).get().map { response =>

      response.json.validate[List[ActionJson]] match {
        case s: JsSuccess[List[ActionJson]] => s.get
        case e: JsError => throw new RuntimeJsonMappingException(e.toString)
      }
    }

    actionFactsFuture.map { json =>
      json.map(ActionFact(_))
    }
  }

}
