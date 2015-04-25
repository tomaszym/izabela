//package org.tejo.iza.actor
//
//import akka.actor.Actor
//import org.tejo.iza.actor.TEJOModelActor.PlsTEJO
//import org.tejo.model.TEJO
//import scaldi.akka.AkkaInjectable
//
//class TEJOModelActor extends Actor with AkkaInjectable {
//  override def receive: Receive = {
//
//    case PlsTEJO => sender() ! TEJO(List(), List(), List())
//  }
//}
//
//object TEJOModelActor {
//
//  case object PlsTEJO
//}