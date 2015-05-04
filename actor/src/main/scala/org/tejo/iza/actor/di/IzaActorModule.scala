package org.tejo.iza.actor.di

import akka.actor.{Props, ActorRef, ActorSystem}
import org.tejo.iza.actor.cirkulerilo.redaktilo.{DummyRedaktilo, Redaktilo}
import org.tejo.iza.actor.{IzaActorTag, IzaActor}
import org.tejo.iza.actor.cirkulerilo.{DissenduActorTag, KunmetuActorTag, DissenduActor, KunmetuActor}
import org.tejo.iza.actor.ws.TrelloService
import org.tejo.model.TEJO


trait IzaActorModule {

  import com.softwaremill.macwire._

  def actorSystem: ActorSystem

  def trelloService: TrelloService

  val redaktilo: Redaktilo = wire[DummyRedaktilo]

  lazy val kunmetuActor: ActorRef = actorSystem.actorOf(Props(wire[KunmetuActor]),"kunmetu").taggedWith[KunmetuActorTag]
  lazy val dissenduActor: ActorRef = actorSystem.actorOf(Props(wire[DissenduActor]),"dissendu").taggedWith[DissenduActorTag]

  lazy val izaActor: ActorRef = actorSystem.actorOf(Props(classOf[IzaActor], trelloService, dissenduActor, kunmetuActor :: Nil),"iza").taggedWith[IzaActorTag]

  lazy val tejoModel: TEJO = TEJO(Nil,Nil,Nil)//TEJO(???,???,???) // TODO PH (sufiĉos elŝuti la datumojn unufoje en/per lazy val)
}
