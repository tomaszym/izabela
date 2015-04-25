package org.tejo.iza.actor.cirkulerilo

import akka.actor.{ActorLogging, Actor}
import org.tejo.iza.actor.cirkulerilo.DissenduActor.Msg.Cirkulero
import org.tejo.iza.actor.cirkulerilo.redaktilo.Redaktilo
import org.tejo.iza.actor.msg.RulesFired
import org.tejo.iza.actor.{ClaraQueryHandler, IzaActor}
import org.tejo.iza.rules.{ClaraQuery, KontribuintojQuery, KunmetuQuery}
import org.tejo.model.TEJO
import scaldi.Injector
import scaldi.akka.AkkaInjectable

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

class KunmetuActor(implicit inj: Injector) extends Actor with AkkaInjectable with ClaraQueryHandler with ActorLogging {

//  val iza = injectActorRef [IzaActor]
  implicit val ec = inject [ExecutionContext]
  val tejo = inject [TEJO]
  val redaktilo = inject [Redaktilo]
  val dissenduActor = injectActorRef [DissenduActor]


  override def receive: Receive = {

    case RulesFired =>
      log.debug("Rules Fired.")
      val iza = sender()
      askQuery(KunmetuQuery, iza).flatMap {
        case Some(_) => askQuery(KontribuintojQuery, iza).map { kontribuojFacts =>
          val result = redaktilo.redaktu(kontribuojFacts, tejo)
          dissenduActor ! Cirkulero(result)
        }

        case None => Future { println("ne estas komando por kunmeti cirkuleron") }

      }




  }

}
