package org.tejo.model

import org.tejo.iza.rules.facts.CardFact

case class TEJO(aktivuloj: List[Aktivulo], komisionoj: List[Komisiono], sekcioj: List[Sekcio]) {

  private val aktivulojMap: Map[String, Aktivulo] = aktivuloj.map { a =>
    (a.adreso, a)
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
