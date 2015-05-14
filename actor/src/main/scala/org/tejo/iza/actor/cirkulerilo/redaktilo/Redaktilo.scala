package org.tejo.iza.actor.cirkulerilo.redaktilo

import org.tejo.iza.rules.facts.CardFact
import org.tejo.model.{TEJO, Kontribuo}

trait Redaktilo {
  def redaktu(kontribuoj: List[Kontribuo], tejo: TEJO): String
}

class DummyRedaktilo extends Redaktilo {
  override def redaktu(kontribuoj: List[Kontribuo], tejo: TEJO): String = "Dummy"
}