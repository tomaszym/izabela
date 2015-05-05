package org.tejo.model

trait Estrarano extends Aktivulo

case class Prezidanto(adreso: String, personajInformoj: PersonajInformoj) extends Estrarano
case class UnuaVicprezidanto(adreso: String, personajInformoj: PersonajInformoj) extends Estrarano
case class DuaVicprezidanto(adreso: String, personajInformoj: PersonajInformoj) extends Estrarano
case class GhenSek(adreso: String, personajInformoj: PersonajInformoj) extends Estrarano
case class Kasisto(adreso: String, personajInformoj: PersonajInformoj) extends Estrarano
case class AnoDeEstraro(adreso: String, personajInformoj: PersonajInformoj) extends Estrarano