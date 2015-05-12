package org.tejo.xmodel

abstract class Sekcio

case class LandaSekcio(plenaNomo: String, mallongigo: String, lando: String, adreso: String, retejo: String) extends Sekcio
case class FakaSekcio(plenaNomo: String, mallongigo: String) extends Sekcio
