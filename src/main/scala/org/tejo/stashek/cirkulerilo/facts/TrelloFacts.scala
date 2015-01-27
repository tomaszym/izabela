package org.tejo.stashek.cirkulerilo.facts

import scala.concurrent.{ExecutionContext, Future}

case class TrelloFacts(
                        board: BoardFact,
                        lists: List[ListFact],
                        cards: List[CardFact],
                        checklists: List[ChecklistFact]
                      ) {

  def asJavaIterable: java.lang.Iterable[_] = {

    import scala.collection.convert.wrapAsJava._

    (board :: lists ++ cards ++ checklists)
  }
}


object TrelloFacts {

  def apply(board: Future[BoardFact], lists: Future[List[ListFact]], cards: Future[List[CardFact]], checklists: Future[List[ChecklistFact]])(implicit executor: ExecutionContext): Future[TrelloFacts] = {

    board.flatMap { boardFact =>
      lists.flatMap{ listFacts =>
        cards.flatMap { cardsFacts =>
          checklists.map {checklistFacts =>
            TrelloFacts(boardFact, listFacts, cardsFacts, checklistFacts)
          }
        }
      }
    }
  }

}