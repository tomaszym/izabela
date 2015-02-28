package org.tejo.model

import org.joda.time.DateTime
import org.tejo.iza.rules.facts.CardFact


case class PersonajInformoj(plenaNomo: String, naskighDato: DateTime)

abstract class Aktivulo(adreso: String, personajInformoj: PersonajInformoj)


case class KomitatanoA(adreso: String, personajInformoj: PersonajInformoj, sekcio: String) extends Aktivulo(adreso, personajInformoj)
case class KomitatanoB(adreso: String, personajInformoj: PersonajInformoj) extends Aktivulo(adreso, personajInformoj)
case class KomitatanoC(adreso: String, personajInformoj: PersonajInformoj) extends Aktivulo(adreso, personajInformoj)

case class Redaktoro(adreso: String, personajInformoj: PersonajInformoj, revuo: String) extends Aktivulo(adreso, personajInformoj)
case class Volontulo(adreso: String, personajInformoj: PersonajInformoj) extends Aktivulo(adreso, personajInformoj)
