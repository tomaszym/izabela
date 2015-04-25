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

  lazy val kunmetuActor: ActorRef = actorSystem.actorOf(Props(wire[KunmetuActor])).taggedWith[KunmetuActorTag]
  lazy val dissenduActor: ActorRef = actorSystem.actorOf(Props(wire[DissenduActor])).taggedWith[DissenduActorTag]

  lazy val izaActor: ActorRef = actorSystem.actorOf(Props(classOf[IzaActor], trelloService, kunmetuActor :: Nil)).taggedWith[IzaActorTag]

  val tejoModel = TEJO(Nil, Nil, Nil)
}
