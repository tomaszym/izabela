package org.tejo.iza.actor.cirkulerilo

import akka.actor.Actor
import akka.actor.Actor.Receive
import org.tejo.iza.actor.cirkulerilo.DissenduActor.Msg.Cirkulero
import scaldi.Injector
import scaldi.akka.AkkaInjectable

class DissenduActor(implicit inj: Injector) extends Actor with AkkaInjectable {
  override def receive: Receive = {
    case Cirkulero(str) => println(str)
  }
}

object DissenduActor {
  object Msg {
    case class Cirkulero(str: String)
  }
}