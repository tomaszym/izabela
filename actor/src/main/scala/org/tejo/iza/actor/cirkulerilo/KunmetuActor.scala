package org.tejo.iza.actor.cirkulerilo

import akka.actor.Actor
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

class KunmetuActor(implicit inj: Injector) extends Actor with AkkaInjectable with ClaraQueryHandler {

  val iza = injectActorRef [IzaActor]
  implicit val ec = inject [ExecutionContext]
  val tejo = inject [TEJO]
  val redaktilo = inject [Redaktilo]
  val dissenduActor = injectActorRef [DissenduActor]

  def askIzaQuery[T: ClassTag](query: ClaraQuery[T]) = askQuery(query,iza)

  override def receive: Receive = {

    case RulesFired =>
      askIzaQuery(KunmetuQuery).flatMap {
        case Some(_) => askIzaQuery(KontribuintojQuery).map { kontribuojFacts =>
          val result = redaktilo.redaktu(kontribuojFacts, tejo)
          dissenduActor ! Cirkulero(result)
        }

        case None => Future { println("ne estas komando por kunmeti cirkuleron") }

      }




  }

}
