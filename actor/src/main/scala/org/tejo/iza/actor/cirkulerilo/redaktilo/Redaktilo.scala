package org.tejo.iza.actor.cirkulerilo.redaktilo

import org.tejo.iza.rules.facts.CardFact


abstract class Redaktilo {
  def redaktu(kontribuoj: List[CardFact]): String
}
