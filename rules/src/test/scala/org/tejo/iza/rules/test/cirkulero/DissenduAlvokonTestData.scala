package org.tejo.iza.rules.test.cirkulero

import org.tejo.iza.rules._
import org.tejo.iza.rules.facts._
import org.tejo.iza.rules.facts.control.{KunmetuCmd, AlvokuCmd}
import org.tejo.iza.rules.test.RuleTestData

object DissenduAlvokonTestData extends RuleTestData {

  val listId = "LISTID"
  val cardId = "CARDID"
  val checklistId = "CHECKLISTID"
  val dueDate = "2015-02-26T01:08:44.490Z"


  val rulesNamespace: String = "cirkulerilo"

  override def facts: List[Any] = List(
    ListFact(listId, name = "Aktuala"),
    CardFact(cardId, listId = listId, name = "Stirkarto", due = dueDate),
    ChecklistFact(id = checklistId, cardId = cardId),
    CheckItemFact(idx = 0, id = "", checklistId = checklistId, name = "", pos = 1, complete = true),
    CheckItemFact(idx = 1, id = "", checklistId = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 2, id = "", checklistId = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 3, id = "", checklistId = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 4, id = "", checklistId = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 5, id = "", checklistId = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 6, id = "", checklistId = checklistId, name = "", pos = 1, complete = false)
  )

  override def queryResultMap: Map[ClaraQuery[_], Any] = Map(
    AlvokuQuery -> Some(AlvokuCmd(dueDate)),
    MemoriguQuery -> None,
    KunmetuQuery -> None
  )
}
