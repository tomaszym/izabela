package org.tejo.model

import org.tejo.iza.rules.facts.CardFact

case class Cirkulero(tejo: TEJO) {

  /** Converts trello cards to `Contribution`: takes description
    * as content and searches an 'Aktivulo' by name, which should be an email
    * @param cardFact
    * @return
    */
  def kontribuo(cardFact: CardFact): Option[Kontribuo] = {

    implicit def str2mrkd(str: String): MarkdownValue = MarkdownValue(str)

    if(cardFact.desc != "") {
      // TODO throw on missing emails in the database
      val persono = tejo.getPersonoByRetadreso(cardFact.name)
      if(persono.roloj.nonEmpty) {
        Some(Kontribuo(cardFact.desc, persono))
      } else None
    } else None
  }
  
  private implicit class CirkuleroOrderedPosteno(p: Posteno) {
    def priority: String = p match {
        case Estrarano(f, _) => "1" + (f match {
          case Some(Prezidanto) => "0"
          case Some(Gxensek) => "1"
          case Some(Kasisto) => "2"
          case Some(Vicprezidanto) => "3"
          case Some(DuaVicprezidanto) => "4"
          case None => "5"
        })

        case Volontulo => "19999" // post la estraro, antau komisiitoj

        case p@Komisiito(k) => "2" + k.plenaNomo
        case KomitatanoA(s) => "3" + s.plenaNomo
        case KomitatanoB => "4"
        case KomitatanoC => "5"
        case Redaktoro(revuo) => "6" + revuo
      }
  }

  implicit def postenoOrdering: Ordering[Posteno] = Ordering.by(_.priority)

  implicit def kontribuoOrdering: Ordering[Kontribuo] = Ordering.by(_.autoro.roloj.sorted.headOption)
}