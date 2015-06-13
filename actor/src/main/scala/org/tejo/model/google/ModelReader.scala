package org.tejo.model.google

import java.io.File
import java.net.URL

import com.typesafe.config.ConfigFactory
import org.tejo.model._

object ModelReader {
  lazy val config = ConfigFactory.load()
  implicit lazy val accountConfig = new AccountConfig(
    serviceAccountId = config.getString("googleModel.serviceAccountId"),
    p12PrivateKey = new File(config.getString("googleModel.p12"))
  )

  lazy val spreadsheet = new Spreadsheet(new URL(
    config.getString("googleModel.documentUrl")
  ))

  lazy val komisionoj: List[Komisiono] = {
    spreadsheet.readRows("Komisionoj"){c =>
      Komisiono(
        plenaNomo = c("Plenanomo").get,
        mallongaNomo = c("mallonganomo").getOrElse(""),
        mallongigo = c("mallongigo").getOrElse(""),
        adreso = c("retpoŝtadreso").getOrElse(""),
        estraranaAdreso = c("estraranaretpoŝtadreso").getOrElse(""),
        komisiitaAdreso = c("komisiitaretpoŝtadreso"),
        tttAdreso = c("tejo-paĝo").getOrElse(""),
        trelloTabulo = c("publikatrellotabulo"),
        diskutlisto = c("diskutlisto")
      )
    }
  }
  lazy val landajSekcioj: List[LandaSekcio] = {
    spreadsheet.readRows("LSoj"){ c =>
      LandaSekcio(
        identigilo = c("identigilo").get,
        mallongigo = c("mallongigo").get,
        plenaNomo = c("plenanomo").get,
        regiono = c("regiono").getOrElse(""),
        retejo = c("retejo"),
        fejsbukaPagho = c("fejsbukpaĝo")
      )
    }
  }
  lazy val fakajSekcioj: List[FakaSekcio] = {
    spreadsheet.readRows("FSoj"){ c =>
      FakaSekcio(
        identigilo = c("identigilo").get,
        mallongigo = c("mallongigo").get,
        plenaNomo = c("plena nomo").get,
        retejo = c("retejo"),
        fejsbukaPagho = c("fejsbukpaĝo")
      )
    }
  }

  def sekcioj: List[Sekcio] = landajSekcioj ++ fakajSekcioj

  def personoj: List[Persono] = {
    spreadsheet.readRows("Komitato"){ c =>
      val retadreso = c("retadreso").getOrElse(throw NevalidaRetadreso(""))
      val nomo = c("Nomo").getOrElse("???")
      val trello = c("trello")

      def respondecataj: List[Komisiono] = komisionoj.filterRespondecataj(retadreso)
      def komisiitataj: List[Komisiito] = komisionoj.filterKomisiitaj(retadreso).map(k => Komisiito(k))
      def chefrolo: Option[Posteno] = c("rolo") match {

        case Some("estrarano") =>
          Some(Estrarano(None, respondecataj))
        case Some("prezidanto") =>
          Some(Estrarano(Some(Prezidanto), respondecataj))
        case Some("vicprezidanto") =>
          Some(Estrarano(Some(Vicprezidanto), respondecataj))
        case Some("2a vicprezidanto") =>
          Some(Estrarano(Some(DuaVicprezidanto), respondecataj))
        case Some("kasisto") =>
          Some(Estrarano(Some(Kasisto), respondecataj))
        case Some("ĝensek") =>
          Some(Estrarano(Some(Gxensek), respondecataj))

        case Some("KomA") =>
          Some(KomitatanoA(sekcioj.find(_.identigilo == c("sekcio").get).getOrElse(throw SekcioNeTrovita(c("sekcio").get))))
        case Some("KomB") =>
          Some(KomitatanoB)
        case Some("KomC") =>
          Some(KomitatanoC)

        case Some("redaktoro") =>
          Some(Redaktoro(c("revuo").get))
        case Some("volontulo") =>
          Some(Volontulo)
        case els: Any => None // komisiitecoj estas aldonataj poste al la rollisto
      }
      Persono(nomo, retadreso, trello, chefrolo.toList ++ komisiitataj)
    }
  }

  def tejo = TEJO(
    aktivuloj = personoj,
    komisionoj = komisionoj,
    sekcioj = List()
  )
}

case class SekcioNeTrovita(id: String) extends RuntimeException("Sekcio identigata de " + id + " ne ekzistas en la datumbazo")
case class NevalidaRetadreso(s: String) extends RuntimeException(s"La retadreso $s ne estas valida")