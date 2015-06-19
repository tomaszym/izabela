package org.tejo.model

sealed abstract class Posteno {
  def titolo: String = this.getClass.getSimpleName.dropRight(1)
  def subtitolo: Option[String] = None
}

sealed abstract class Komitatano extends Posteno

case class KomitatanoA(sekcio: Sekcio) extends Komitatano {
  override def titolo: String = s"Komitatano A"
  override def subtitolo = Some(sekcio.plenaNomo)
}
case object KomitatanoB extends Komitatano {
  override def titolo: String = "Komitatano B"

}
case object KomitatanoC extends Komitatano {
  override def titolo: String = "Komitatano C"
}

case class Redaktoro(revuo: String) extends Posteno {
  override def titolo: String = s"Redaktoro de $revuo"
}

case object Volontulo extends Posteno

sealed abstract class EstraraFako
case object Prezidanto extends EstraraFako
case object Vicprezidanto extends EstraraFako
case object DuaVicprezidanto extends EstraraFako
case object Gxensek extends EstraraFako {
  override def toString: String = "Ĝenerala Sekretario"
}
case object Kasisto extends EstraraFako

case class Estrarano(fako: Option[EstraraFako] = None, respondecasPri: List[Komisiono] = List()) extends Posteno {
  override def titolo: String = fako match {
    case Some(f) => f.toString
    case None => "Estrarano"
  }
  override def subtitolo = if(respondecasPri.nonEmpty) {
    Some("respondeca pri: " + respondecasPri.map(_.plenaNomo).mkString(", "))
  } else None
}

case class Komisiito(komisiono: Komisiono) extends Posteno {
  override def titolo = "Komisiito pri " + komisiono.plenaNomo
}
