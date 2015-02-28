package org.tejo.iza.actor.ws

import scala.concurrent.Future

/** Provides a way to download all the facts trough TrelloWS
  * which concerns a board.
  */
trait FactList {

  def factListFuture(boardId: String)(implicit trello: TrelloWS): Future[List[_]] = {

    for {
      board <- trello.boardFact(boardId)
      lists <- trello.listFacts(boardId)
      cards <- trello.cardFacts(boardId)
      checklists <- trello.checklistFacts(boardId)
    } yield {
      board :: lists ++ cards ++ checklists.map(_._1) ++ checklists.flatMap(_._2)
    }
  }
}
