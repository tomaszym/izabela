package org.tejo.iza.actor.cirkulerilo.redaktilo

import org.tejo.iza.rules.facts.CardFact
import org.tejo.model.TEJO

trait Redaktilo {
  def redaktu(kontribuoj: List[CardFact], tejo: TEJO): String
}

class DummyRedaktilo extends Redaktilo {
  override def redaktu(kontribuoj: List[CardFact], tejo: TEJO): String = "Dummy"
}