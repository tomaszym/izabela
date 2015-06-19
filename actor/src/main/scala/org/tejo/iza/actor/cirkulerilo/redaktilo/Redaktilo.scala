package org.tejo.iza.actor.cirkulerilo.redaktilo

import org.tejo.iza.rules.facts.CardFact
import org.tejo.model.{KontribuEnskribo, Cirkulero, TEJO, Kontribuo}

trait Redaktilo {
  def redaktu(cirk: Cirkulero): String
}