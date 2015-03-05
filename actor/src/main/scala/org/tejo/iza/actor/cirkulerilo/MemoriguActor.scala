package org.tejo.iza.actor.cirkulerilo

import akka.actor.Actor
import akka.actor.Actor.Receive
import scaldi.Injector
import scaldi.akka.AkkaInjectable

class MemoriguActor(implicit inj: Injector) extends Actor with AkkaInjectable {

  override def receive: Receive = ???
}
