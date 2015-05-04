package org.tejo.model

import org.joda.time.DateTime
import org.tejo.iza.rules.facts.CardFact


case class PersonajInformoj(plenaNomo: String, naskighDato: DateTime)

trait Aktivulo {
  def adreso: String
  def personajInformoj: PersonajInformoj
}


case class KomitatanoA(adreso: String, personajInformoj: PersonajInformoj, sekcio: String) extends Aktivulo
case class KomitatanoB(adreso: String, personajInformoj: PersonajInformoj) extends Aktivulo
case class KomitatanoC(adreso: String, personajInformoj: PersonajInformoj) extends Aktivulo

case class Redaktoro(adreso: String, personajInformoj: PersonajInformoj, revuo: String) extends Aktivulo
case class Volontulo(adreso: String, personajInformoj: PersonajInformoj) extends Aktivulo
