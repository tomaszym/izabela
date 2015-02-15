package org.tejo.iza.rules.facts

import pl.pej.trelloilaro.api.model.ListJson

import scala.beans.BeanInfo
import scala.util.Try

@BeanInfo
case class ListFact(id: String, name: String)

object ListFact {

  def apply(json: ListJson): ListFact = {
    Try(ListFact(json.id, json.name.get)).get
  }
}

