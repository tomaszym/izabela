package org.tejo.iza.actor.di

import akka.actor.ActorSystem
import org.tejo.iza.actor.IzaActor
import org.tejo.iza.actor.cirkulerilo.KunmetuActor
import org.tejo.iza.actor.ws.TrelloService
import org.tejo.model.TEJO
import scaldi.Module

import scala.concurrent.ExecutionContext

object Modules {

 class IzaActorModule extends Module {

   val actorSystem = ActorSystem("iza")
   bind [ActorSystem] to actorSystem destroyWith (_.shutdown())

   bind [ExecutionContext] to inject[ActorSystem].dispatcher

   binding toProvider new IzaActor

//   bind [TrelloService] to ??? TODO


   binding toProvider new KunmetuActor

   bind [TEJO] to TEJO(Nil, Nil, Nil)

 }

}
