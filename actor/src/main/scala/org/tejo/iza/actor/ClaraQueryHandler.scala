package org.tejo.iza.actor

import akka.actor.{ActorRef, Actor}
import akka.util.Timeout
import org.tejo.iza.rules.ClaraQuery
import scala.concurrent.duration._

import scala.concurrent.Future
import scala.reflect.ClassTag

trait ClaraQueryHandler { this: Actor =>

  def askQuery[T: ClassTag](query: ClaraQuery[T], actor: ActorRef): Future[T] = {
    import akka.pattern.ask

    implicit val timeout = Timeout(5 seconds)

    (actor ? query).mapTo[T]
  }



}
