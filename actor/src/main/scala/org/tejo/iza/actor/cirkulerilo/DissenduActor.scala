package org.tejo.iza.actor.cirkulerilo

import akka.actor.Actor
import org.tejo.iza.actor.cirkulerilo.DissenduActor.Msg.Cirkulero

class DissenduActor extends Actor {
  override def receive: Receive = {
    case Cirkulero(str) => println(str)
      scala.tools.nsc.io.File("cirkulero.html").writeAll(str)
  }
}

trait DissenduActorTag

object DissenduActor {
  object Msg {
    case class Cirkulero(str: String)
  }
}