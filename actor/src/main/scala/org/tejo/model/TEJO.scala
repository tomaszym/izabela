package org.tejo.model

import org.tejo.iza.rules.facts.CardFact

case class TEJO(aktivuloj: List[Persono], komisionoj: List[Komisiono], sekcioj: List[Sekcio]) {

  private val aktivulojMap: Map[String, Persono] = aktivuloj.map { a =>
    (a.retadreso, a)
  }.toMap

  /** Converts trello cards to `Contribution`: takes description
    * as content and searches an 'Aktivulo' by name, which should be an email
    * @param cardFact
    * @return
    */
  def kontribuo(cardFact: CardFact): Option[Kontribuo] = {
    implicit def str2mrkd(str: String): MarkdownValue = MarkdownValue(str)

    if(cardFact.desc != "") {
      aktivulojMap.get(cardFact.name).map { persono =>
        Kontribuo(cardFact.desc, persono)
      }
    } else None
  }

}
