package org.tejo.iza.rules.test.cirkulero

import org.tejo.iza.rules.facts._
import org.tejo.iza.rules.facts.control.DissenduAlvokon
import org.tejo.iza.rules.test.RuleTestData

object DissenduAlvokonData extends RuleTestData {

  val listId = "LISTID"
  val cardId = "CARDID"
  val checklistId = "CHECKLISTID"


  val clojureNamespace: String = "cirkulerilo"

//    [ListFact (= name "Aktuala")
//  (= ?listId id)]
//
//  [CardFact (= name "Agordoj")
//  (= ?agrodojCardId id)]
//
//  [ChecklistFact (= idCard ?agordojCardId)
//  ;                 (= "Dissendi alvokon @tomaszszymula" (.name (.head checkItems)))
//  (= "complete" (.state (.head checkItems)))
//  ;                 (= "Alvoko dissendita @stashek" (.name (.head (.tail checkItems))))]
//  (= "incomplete" (.state (.head (.tail checkItems))))]


  override def facts: List[Any] = List(
    ListFact(listId, name = "Aktuala"),
    CardFact(cardId, name = "Stirkarto", due = Some("2000-02-01")),
    ChecklistFact(id = checklistId, idCard = cardId),
    CheckItemFact(idx = 0, id = "", idList = checklistId, name = "", pos = 1, complete = true),
    CheckItemFact(idx = 1, id = "", idList = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 2, id = "", idList = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 3, id = "", idList = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 4, id = "", idList = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 5, id = "", idList = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 6, id = "", idList = checklistId, name = "", pos = 1, complete = false)
  )

  val queryResultMap: Map[String, (String, Some[DissenduAlvokon])] = Map(
    "cirkulerilo/alvoko-query" -> ("?alvoko", Some(DissenduAlvokon()))
  )

}
