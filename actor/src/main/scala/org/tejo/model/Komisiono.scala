package org.tejo.model

case class Komisiono(
                      plenaNomo: String,
                      mallongaNomo: String,
                      mallongigo: String,
                      adreso: String,
                      estraranaAdreso: String,
                      komisiitaAdreso: Option[String],
                      tttAdreso: String,
                      trelloTabulo: Option[String],
                      diskutlisto: Option[String])



object Komisiono {

  implicit class RichKomisionoList(komisionoj: List[Komisiono]) {

    def filterKomisiitaj(retadreso: String): List[Komisiono] = {
      komisionoj.filter(_.komisiitaAdreso.contains(retadreso))
    }
    def filterRespondecataj(retadreso: String): List[Komisiono] = {
      komisionoj.filter(_.estraranaAdreso == retadreso)
    }
  }
}