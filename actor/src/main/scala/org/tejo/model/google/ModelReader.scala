package org.tejo.model.google

import java.io.File
import java.net.URL

import com.typesafe.config.ConfigFactory
import org.tejo.model._

object ModelReader {
  lazy val config = ConfigFactory.load()
  implicit lazy val accountConfig = new AccountConfig(
    serviceAccountId = config.getString("googleModel.serviceAccountId"),
    p12PrivateKey = new File(config.getString("googleModel.p12"))//MalmpompaAligxilo-0f665c366cc1.p12")
  )

  lazy val spreadsheet = new Spreadsheet(new URL(
    config.getString("googleModel.documentUrl")
  ))

  def komisionoj: List[Komisiono] = {
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

  def personoj: List[Persono] = {
    spreadsheet.readRows("Komitato"){c =>
      val adreso = c("retadreso").getOrElse("???")
      val nomo = c("Nomo").getOrElse("???")
      val trello = c("trello")


      val rolo = c("rolo") match {
        case Some("estrarano") =>
          AnoDeEstraro
        case Some("prezidanto") =>
          Prezidanto
        case Some("vicprezidanto") =>
          Vicprezidanto
        case Some("kasisto") =>
          Kasisto
        case Some("ĝensek") =>
          Gxensek

        case Some("KomA") =>
          KomitatanoA
        case Some("KomB") =>
          KomitatanoB
        case Some("KomC") =>
          KomitatanoC

        case Some("komisiito") =>
          Komisiito
        case Some(s) if s.startsWith("Redaktoro") =>
          Redaktoro
        case Some("volontulo") =>
          Volontulo
      }
      Persono(nomo, adreso, trello, rolo)
    }
  }

  def tejo = TEJO(
    aktivuloj = personoj,
    komisionoj = komisionoj,
    sekcioj = List()
  )
}
