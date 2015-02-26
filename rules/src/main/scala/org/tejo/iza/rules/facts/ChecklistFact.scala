package org.tejo.iza.rules.facts

import pl.pej.trelloilaro.api.model.ChecklistJson
import pl.pej.trelloilaro.api.model.checklist.CheckItemJson

import scala.beans.BeanInfo
import scala.util.Try

@BeanInfo case class ChecklistFact(
                          id: String,
                          cardId: String
                          ) {
//
//  /** Returns an Option of a checkitem at
//    * given index. Simplifies clojure rules.
//    * @param idx Index from which checkItem is tried to be returned in an option.
//    * @return
//    */
//  def checkedAt(idx: Int): Boolean =
//    if(checkItems.isDefinedAt(idx))
//      checkItems(idx).complete
//    else false
//
//  def uncheckedAt(idx: Int) : Boolean =
//    if (checkItems.isDefinedAt(idx))
//      checkItems(idx).incomplete
//    else false

}

object ChecklistFact {
  def apply(listJson: ChecklistJson): (ChecklistFact, List[CheckItemFact]) = {
    Try(
      (
        ChecklistFact(
          id = listJson.id,
          cardId = listJson.idCard.get),
        listJson.checkItems.get.zipWithIndex.map { case (itemJson, idx) =>
          CheckItemFact(idx, listJson.id, itemJson)
        }
      )
    ).get
  }
}

@BeanInfo case class CheckItemFact(idx: Int, id: String, checklistId: String, name: String, pos: Int, complete: Boolean)

object CheckItemFact {

  def apply(idx: Int, idList: String, json: CheckItemJson): CheckItemFact = {

    CheckItemFact(idx, json.id, checklistId = idList, json.name.get, json.pos.get, json.state.get == "complete")

  }
}