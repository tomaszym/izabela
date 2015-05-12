package org.tejo.xmodel

import org.tejo.iza.rules.facts.CardFact
import org.tejo.model.{Persono, Komisiono}

case class TEJO(aktivuloj: List[Persono], komisionoj: List[Komisiono], sekcioj: List[Sekcio]) {

  private val aktivulojMap: Map[String, Persono] = aktivuloj.map { a =>
    (a.retadreso, a)
  }.toMap

  /** Converts trello cards to `Contribution`: takes description
    * as content and searches an 'Aktivulo' by name, which should be an email
    * @param cardFact
    * @return
    */
  def kontribuo(cardFact: CardFact): Kontribuo = {
    implicit def str2mrkd(str: String): Markdown = Markdown(str)

    Kontribuo(cardFact.desc, aktivulojMap(cardFact.name))
  }

}
