package org.tejo.iza.actor.cirkulerilo

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.pattern.{ ask, pipe }
import org.tejo.iza.actor.{TEJOModelActor, IzaActor}
import org.tejo.iza.actor.msg.{ResponseMsg, QueryMsg, RulesFired}
import org.tejo.iza.rules.facts.control.KunmetuCmd
import org.tejo.iza.rules.{KunmetuQuery, KunmetuQueryId}
import org.tejo.iza.rules.facts.CardFact
import scaldi.Injector
import scaldi.akka.AkkaInjectable

import scala.concurrent.Future

class KunmetuActor(implicit inj: Injector) extends Actor with AkkaInjectable {

  val iza = injectActorRef [IzaActor]
  val tejoModel = injectActorRef [TEJOModelActor]

  override def receive: Receive = {

    case RulesFired =>
      val queryResponse = (iza ? KunmetuQuery).mapTo[Option[KunmetuCmd]]


  }

}
