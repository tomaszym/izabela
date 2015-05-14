package org.tejo

import akka.actor.ActorSystem
import org.tejo.iza.actor.di.IzaActorModule
import org.tejo.iza.actor.ws.TrelloService
import org.tejo.iza.rules.ClojureNamespace


object Cirkulerilo extends App {

  val m = new IzaActorModule {
    override def actorSystem: ActorSystem = ActorSystem("iza-system")
  }


  val iza = m.izaActor

  import org.tejo.iza.actor.IzaActor.Msg._

  iza ! ResetWorkingMemory(ClojureNamespace.toLoadCirkuleriloRules::Nil)

  iza ! LoadFacts("54c4e18f2ab48fa15f8a1120")

  iza ! FireRules


}
