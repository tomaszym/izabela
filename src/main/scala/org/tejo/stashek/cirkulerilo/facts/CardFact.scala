package org.tejo.stashek.cirkulerilo.facts

import pl.pej.trelloilaro.api.model.CardJson

import scala.beans.BeanInfo

@BeanInfo
case class CardFact(cardId: String)

object CardFact {

  def apply(json: CardJson): CardFact = {
    CardFact(json.id)
  }
}

