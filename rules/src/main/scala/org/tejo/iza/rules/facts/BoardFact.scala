package org.tejo.iza.rules.facts

import pl.pej.trelloilaro.api.model.BoardJson

import scala.beans.BeanInfo

@BeanInfo
case class BoardFact(boardId: String)

object BoardFact {
  def apply(json: BoardJson): BoardFact = {
    BoardFact(json.id)
  }
}
