package org.tejo.iza.actor.cirkulerilo

import akka.actor.{Actor, ActorLogging}
import org.tejo.iza.actor.ClaraQueryHandler
import org.tejo.iza.actor.cirkulerilo.DissenduActor.Msg.Cirkulero
import org.tejo.iza.actor.cirkulerilo.redaktilo.Redaktilo
import org.tejo.iza.actor.msg.RulesFired
import org.tejo.iza.rules.{KontribuintojQuery, KunmetuQuery}
import org.tejo.model.TEJO

import scala.concurrent.{ExecutionContext, Future}
class KunmetuActor(
                    tejo: TEJO,
                    redaktilo: Redaktilo) extends Actor
  with ClaraQueryHandler with ActorLogging {

  implicit val actorDispatcher: ExecutionContext = this.context.dispatcher

  override def receive: Receive = {

    case RulesFired =>
      log.debug("Rules Fired.")
      val iza = sender()
      askQuery(KunmetuQuery, iza).flatMap {
        case Some(_) => askQuery(KontribuintojQuery, iza).map { kontribuojFacts =>
          val result = redaktilo.redaktu(kontribuojFacts, tejo)
          iza ! Cirkulero(result)
        }

        case None => Future { log.debug("ne estas komando por kunmeti cirkuleron") }
      }
  }

}

trait KunmetuActorTag
