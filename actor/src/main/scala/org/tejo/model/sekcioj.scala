package org.tejo.model

import org.tejo.model.google.SekcioNeTrovita

abstract class Sekcio {
  def identigilo: String
  def plenaNomo: String
  def mallongigo: String
  def retejo: Option[String]
  def fejsbukaPagho: Option[String]
}

case class LandaSekcio(
                        identigilo: String,
                        mallongigo: String,
                        plenaNomo: String,
                        regiono: String,
                        retejo: Option[String],
                        fejsbukaPagho: Option[String]
                        ) extends Sekcio

case class FakaSekcio(
                       identigilo: String,
                       mallongigo: String,
                       plenaNomo: String,
                       retejo: Option[String],
                       fejsbukaPagho: Option[String]
                       ) extends Sekcio


object Sekcio {

  implicit class SearchableSekcioList(sekcioj: List[Sekcio]) {

    def findById(id: String) = sekcioj.find(_.identigilo == id).getOrElse(throw SekcioNeTrovita(id))

  }

}