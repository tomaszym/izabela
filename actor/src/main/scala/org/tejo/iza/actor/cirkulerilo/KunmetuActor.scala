package org.tejo.iza.actor.cirkulerilo

import akka.actor.{Actor, ActorLogging}
import org.tejo.iza.actor.ClaraQueryHandler
import org.tejo.iza.actor.IzaActor.Msg.ClaraQueryResult
import org.tejo.iza.actor.cirkulerilo.DissenduActor.Msg.CirkuleroMsg
import org.tejo.iza.actor.cirkulerilo.redaktilo.Redaktilo
import org.tejo.iza.actor.msg.RulesFired
import org.tejo.iza.rules.facts.control.KunmetuCmd
import org.tejo.iza.rules.facts.{CardFact, ListFact}
import org.tejo.iza.rules.{KontribuintojQuery, KunmetuQuery}
import org.tejo.model.{Kontribuo, Cirkulero, TEJO}

import scala.concurrent.{ExecutionContext, Future}
class KunmetuActor(
                    tejo: TEJO,
                    redaktilo: Redaktilo) extends Actor
  with ClaraQueryHandler with ActorLogging {

  implicit val actorDispatcher: ExecutionContext = this.context.dispatcher

  override def receive: Receive = {

    case RulesFired =>
      sender() ! KunmetuQuery

    case ClaraQueryResult(None) => log.debug("Nenio interesa.")



    case ClaraQueryResult(res) =>

      val iza = sender()
      res match {
        case Some(KunmetuCmd(id)) =>
          iza ! KontribuintojQuery

        case kontribuoKartoj: List[CardFact] =>
          val cirk = Cirkulero(tejo, kontribuoKartoj)

          iza ! CirkuleroMsg(redaktilo.redaktu(cirk))
      }
  }

}

trait KunmetuActorTag
