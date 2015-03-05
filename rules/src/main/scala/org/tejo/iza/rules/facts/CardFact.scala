package org.tejo.iza.rules.facts

import pl.pej.trelloilaro.api.model.CardJson

import scala.beans.BeanInfo
import scala.util.Try

@BeanInfo
case class CardFact(id: String, listId: String, name: String, due: String = "", desc: String = "") {
  val hasDue: Boolean = !due.isEmpty
}

object CardFact {

  def apply(json: CardJson): CardFact = {
    Try(CardFact(
      id = json.id,
      listId = json.ids.idList.get,
      name = json.name.get,
      desc = json.desc.get,
      due = json.due.get.getOrElse("")
    )).get
  }
}

