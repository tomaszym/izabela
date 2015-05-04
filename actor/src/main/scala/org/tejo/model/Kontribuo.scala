package org.tejo.model

import org.tejo.iza.rules.facts.CardFact

case class Markdown(str: String) extends AnyVal

case class Kontribuo(text: Markdown, autoro: Aktivulo)
