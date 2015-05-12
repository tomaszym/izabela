package org.tejo.xmodel

import org.tejo.iza.rules.facts.CardFact
import org.tejo.model.{Persono, Aktivulo}

case class Markdown(str: String) extends AnyVal

case class Kontribuo(text: Markdown, autoro: Persono)
