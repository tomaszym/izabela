package org.tejo.iza.actor.cirkulerilo

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.pattern.{ ask, pipe }
import org.tejo.iza.actor.IzaActor
import org.tejo.iza.actor.msg.{ResponseMsg, QueryMsg, RulesFired}
import org.tejo.iza.rules.KunmetuQueryId
import scaldi.Injector
import scaldi.akka.AkkaInjectable

class KunmetuActor(implicit inj: Injector) extends Actor with AkkaInjectable {

  val iza = injectActorRef [IzaActor]

  override def receive: Receive = {

    case RulesFired =>
      val queryResponse = (iza ? QueryMsg(KunmetuQueryId)).mapTo[ResponseMsg]





  }

}
