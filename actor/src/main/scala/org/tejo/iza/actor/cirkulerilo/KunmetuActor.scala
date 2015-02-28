package org.tejo.iza.actor.cirkulerilo

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.pattern.{ ask, pipe }
import org.tejo.iza.actor.IzaActor
import org.tejo.iza.actor.msg.{ResponseMsg, QueryMsg, RulesFired}
import org.tejo.iza.rules.KunmetuQueryId
import org.tejo.iza.rules.facts.CardFact
import scaldi.Injector
import scaldi.akka.AkkaInjectable

import scala.concurrent.Future

class KunmetuActor(implicit inj: Injector) extends Actor with AkkaInjectable {

  val iza = injectActorRef [IzaActor]

  override def receive: Receive = {

    case RulesFired =>
      val queryResponse: Future[List[CardFact]] = (iza ? QueryMsg(KunmetuQueryId)).mapTo[ResponseMsg].map { response =>
        import scala.collection.convert.wrapAll._
        response.results.headOption.map(_.getResult(KunmetuQueryId.resultQueryKey).asInstanceOf[java.util.List[CardFact]].toList)
          .getOrElse(throw new Exception("theres no expected result :-( btw refactor this code pls"))
      }

      queryResponse.map { kontribuoFacts =>

      }





  }

}
