package org.tejo.iza.rules.facts

import pl.pej.trelloilaro.api.model.ChecklistJson
import pl.pej.trelloilaro.api.model.checklist.CheckItemJson

import scala.beans.BeanInfo
import scala.util.Try

@BeanInfo
case class ChecklistFact(
                          checklistId: String,
                          idCard: String,
                          checkItems: List[CheckItem]
                          )

object ChecklistFact {
  def apply(json: ChecklistJson): ChecklistFact = {
    Try(ChecklistFact(
      checklistId = json.id,
      idCard = json.idCard.get,
      checkItems = json.checkItems.get.map(CheckItem(_))
    )).get
  }
}

@BeanInfo
case class CheckItem(id: String, name: String, pos: Int, state: String)

object CheckItem {

  def apply(json: CheckItemJson): CheckItem = {

    CheckItem(json.id, json.name.get, json.pos.get, json.state.get)

  }
}