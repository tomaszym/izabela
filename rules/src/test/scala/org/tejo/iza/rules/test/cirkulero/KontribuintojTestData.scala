package org.tejo.iza.rules.test.cirkulero

import org.joda.time.DateTime
import org.joda.time.format.{ISODateTimeFormat, DateTimeFormatter}
import org.tejo.iza.rules.facts.control.Memorigu
import org.tejo.iza.rules.test.RuleTestData
import org.tejo.iza.rules.{KontribuintojQueryId, KunmetuQueryId, MemoriguQueryId, AlvokuQueryId}
import org.tejo.iza.rules.facts.{CheckItemFact, ChecklistFact, CardFact, ListFact}

object KontribuintojTestData extends RuleTestData {

  val listId = "LISTID"
  val cardId = "CARDID"
  val checklistId = "CHECKLISTID"
  val now = new DateTime()
  val tomorrow = now.plusDays(2)
  val fmt: DateTimeFormatter = ISODateTimeFormat.dateTime()
  val dueDate = tomorrow.toString(fmt)

  val clojureNamespace: String = "cirkulerilo"

  val kontribuintoj = List(
    CardFact("", listId, "asd@tejo.org",""),
    CardFact("", listId, "qwe@tejo.org",""),
    CardFact("", listId, "zxc@tejo.org",""),
    CardFact("", listId, "tyu@tejo.org",""),
    CardFact("", listId, "ghj@tejo.org","")
  )
  override def facts: List[Any] = List(
    ListFact(listId, name = "Aktuala"),
    CardFact(cardId, listId = listId, name = "Stirkarto", due = dueDate)
  ) ++ kontribuintoj

  val queryResultMap = Map( // result is a list which we need to cast and toList, so it doesn't fit in RuleTestData framework

    KontribuintojQueryId.stringId -> ("?kontribuintoj", Some(kontribuintoj))
  )
}
