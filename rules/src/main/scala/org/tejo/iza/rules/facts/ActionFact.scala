package org.tejo.iza.rules.facts

import pl.pej.trelloilaro.api.model.ActionJson

import scala.beans.BeanInfo
import scala.util.Try

@BeanInfo
case class ActionFact(cardId: String)

object ActionFact {

  def apply(json: ActionJson): ActionFact = {
    Try(ActionFact(json.id)).get
  }
}
