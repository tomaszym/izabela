package org.tejo.model

sealed abstract class Posteno

sealed abstract class Komitatano extends Posteno

case class KomitatanoA(sekcio: Sekcio) extends Komitatano
case object KomitatanoB extends Komitatano
case object KomitatanoC extends Komitatano

case class Redaktoro(revuo: String) extends Posteno

case object Volontulo extends Posteno

sealed abstract class EstraraFako
case object Prezidanto extends EstraraFako
case object Vicprezidanto extends EstraraFako
case object DuaVicprezidanto extends EstraraFako
case object Gxensek extends EstraraFako
case object Kasisto extends EstraraFako

case class Estrarano(fako: Option[EstraraFako] = None, respondecasPri: List[Komisiono] = List()) extends Posteno

case class Komisiito(komisiono: Komisiono) extends Posteno
