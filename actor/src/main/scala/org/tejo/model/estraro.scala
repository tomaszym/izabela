package org.tejo.model

sealed abstract class AbstraktaEstrarano(adreso: String, personajInformoj: PersonajInformoj) extends Aktivulo(adreso, personajInformoj)

case class Prezidanto(adreso: String, personajInformoj: PersonajInformoj) extends AbstraktaEstrarano(adreso, personajInformoj)
case class UnuaVicprezidanto(adreso: String, personajInformoj: PersonajInformoj) extends AbstraktaEstrarano(adreso, personajInformoj)
case class DuaVicprezidanto(adreso: String, personajInformoj: PersonajInformoj) extends AbstraktaEstrarano(adreso, personajInformoj)
case class GhenSek(adreso: String, personajInformoj: PersonajInformoj) extends AbstraktaEstrarano(adreso, personajInformoj)
case class Kasisto(adreso: String, personajInformoj: PersonajInformoj) extends AbstraktaEstrarano(adreso, personajInformoj)
case class Estrarano(adreso: String, personajInformoj: PersonajInformoj) extends AbstraktaEstrarano(adreso, personajInformoj)
