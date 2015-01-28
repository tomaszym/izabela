package org.tejo.stashek.cirkulerilo.facts

import pl.pej.trelloilaro.api.model.CardJson

import scala.beans.BeanInfo
import scala.util.Try

@BeanInfo
case class CardFact(id: String, name: String)

object CardFact {

  def apply(json: CardJson): CardFact = {
    Try(CardFact(
      id = json.id,
      name = json.name.get
    )).get
  }
}

