package org.tejo.iza.rules.test.cirkulero

import org.joda.time.DateTime
import org.joda.time.format.{ISODateTimeFormat, DateTimeFormatter}
import org.tejo.iza.rules.{KunmetuQueryId, MemoriguQueryId, AlvokuQueryId}
import org.tejo.iza.rules.facts._
import org.tejo.iza.rules.facts.control.{Memorigu, DissenduAlvokon}
import org.tejo.iza.rules.test.RuleTestData

object UnuaMemorigoTestData extends RuleTestData {

  val listId = "LISTID"
  val cardId = "CARDID"
  val checklistId = "CHECKLISTID"
  val now = new DateTime()
  val tomorrow = now.plusDays(2)
  val fmt: DateTimeFormatter = ISODateTimeFormat.dateTime()
  val dueDate = tomorrow.toString(fmt)

  val clojureNamespace: String = "cirkulerilo"

  override def facts: List[Any] = List(
    ListFact(listId, name = "Aktuala"),
    CardFact(cardId, listId = listId, name = "Stirkarto", due = dueDate),
    ChecklistFact(id = checklistId, cardId = cardId),
    CheckItemFact(idx = 0, id = "", checklistId = checklistId, name = "", pos = 1, complete = true),
    CheckItemFact(idx = 1, id = "", checklistId = checklistId, name = "", pos = 1, complete = true),
    CheckItemFact(idx = 2, id = "", checklistId = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 3, id = "", checklistId = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 4, id = "", checklistId = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 5, id = "", checklistId = checklistId, name = "", pos = 1, complete = false),
    CheckItemFact(idx = 6, id = "", checklistId = checklistId, name = "", pos = 1, complete = false)
  )

  val queryResultMap = Map(
    AlvokuQueryId.stringId -> (AlvokuQueryId.resultQueryKey, None),
    MemoriguQueryId.stringId -> (MemoriguQueryId, Some(Memorigu(dueDate))),
    KunmetuQueryId.stringId -> (KunmetuQueryId.resultQueryKey, None)
  )

}
