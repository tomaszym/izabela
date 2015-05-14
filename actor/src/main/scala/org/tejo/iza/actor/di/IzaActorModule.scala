package org.tejo.iza.actor.di

import akka.actor.{Props, ActorRef, ActorSystem}
import org.tejo.iza.actor.cirkulerilo.redaktilo.Redaktilo
import org.tejo.iza.actor.cirkulerilo.redaktilo.html.HtmlRedaktilo
import org.tejo.iza.actor.{IzaActorTag, IzaActor}
import org.tejo.iza.actor.cirkulerilo.{DissenduActorTag, KunmetuActorTag, DissenduActor, KunmetuActor}
import org.tejo.iza.actor.ws.{TrelloWS, TrelloService}
import org.tejo.model.TEJO
import org.tejo.model.google.ModelReader


trait IzaActorModule {

  import com.softwaremill.macwire._

  def actorSystem: ActorSystem

  def trelloService: TrelloService = {
    val builder = new com.ning.http.client.AsyncHttpClientConfig.Builder()
    val client = new play.api.libs.ws.ning.NingWSClient(builder.build())

    new TrelloWS(client)(actorSystem.dispatcher)
  }

  val redaktilo: Redaktilo = wire[HtmlRedaktilo]

  lazy val kunmetuActor: ActorRef = actorSystem.actorOf(Props(wire[KunmetuActor]),"kunmetu").taggedWith[KunmetuActorTag]
  lazy val dissenduActor: ActorRef = actorSystem.actorOf(Props(wire[DissenduActor]),"dissendu").taggedWith[DissenduActorTag]

  lazy val izaActor: ActorRef = actorSystem.actorOf(Props(classOf[IzaActor], trelloService, dissenduActor, kunmetuActor :: Nil),"iza").taggedWith[IzaActorTag]

  lazy val tejoModel: TEJO = ModelReader.tejo
}
