package org.tejo.model

import org.tejo.iza.rules.facts.CardFact

case class TEJO(aktivuloj: List[Persono], komisionoj: List[Komisiono], sekcioj: List[Sekcio]) {

  private val aktivulojMap: Map[String, Persono] = aktivuloj.map { a =>
    (a.retadreso, a)
  }.toMap

  def findPersonoByRetadreso(retadreso: String): Option[Persono] = aktivulojMap.get(retadreso)
  def getPersonoByRetadreso(retadreso: String): Persono = findPersonoByRetadreso(retadreso).getOrElse(throw PersonoNeTrovita(retadreso))

  private val sekciojMap: Map[String, Sekcio] = sekcioj.map { a =>
    (a.identigilo, a)
  }.toMap

  def findSekcioById(id: String): Option[Sekcio] = sekciojMap.get(id)
  def getSekcioById(id: String): Sekcio = findSekcioById(id).getOrElse(throw SekcioNeTrovita(id))



}

case class PersonoNeTrovita(retadreso: String) extends RuntimeException("En tejo ne ekzistas persono kun la adreso " + retadreso)
case class SekcioNeTrovita(id: String) extends RuntimeException("En tejo ne ekzistas Sekcio kun la identigilo " + id)