package org.tejo.iza.rules.test.cirkulero

import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}
import org.tejo.iza.rules._
import org.tejo.iza.rules.facts.{CardFact, ListFact}
import org.tejo.iza.rules.test.RuleTestData

object KontribuintojTestData extends RuleTestData {

  val listId = "LISTID"
  val cardId = "CARDID"
  val checklistId = "CHECKLISTID"
  val now = new DateTime()
  val tomorrow = now.plusDays(2)
  val fmt: DateTimeFormatter = ISODateTimeFormat.dateTime()
  val dueDate = tomorrow.toString(fmt)

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

  override def queryResultMap: Map[ClaraQuery[_], Any] = Map(
    KontribuintojQuery -> kontribuintoj
  )
}
