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
    CardFact(cardId, name = "Stirkarto"),
    ChecklistFact(checklistId = checklistId, idCard = cardId, checkItems = List(
      CheckItem(id = "", name = "", pos = 1, state = "complete"),
      CheckItem(id = "", name = "", pos = 1, state = "incomplete"),
      CheckItem(id = "", name = "", pos = 1, state = "incomplete"),
      CheckItem(id = "", name = "", pos = 1, state = "incomplete"),
      CheckItem(id = "", name = "", pos = 1, state = "incomplete"),
      CheckItem(id = "", name = "", pos = 1, state = "incomplete"),
      CheckItem(id = "", name = "", pos = 1, state = "incomplete")
    ))
  )

  val queryResultMap: Map[String, (String, Some[DissenduAlvokon])] = Map(
    "cirkulerilo/alvoko-query" -> ("?alvoko", Some(DissenduAlvokon()))
  )

}
