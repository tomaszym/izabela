package org.tejo.model

abstract class Sekcio

case class LandaSekcio(plenaNomo: String, mallongigo: String, lando: String, adreso: String, retejo: String) extends Sekcio
case class FakaSekcio(plenaNomo: String, mallongigo: String) extends Sekcio
