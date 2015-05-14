package org.tejo.iza.tests

import akka.actor.ActorSystem
import org.scalatest.WordSpec
import org.tejo.iza.actor.cirkulerilo.redaktilo.html.HtmlRedaktilo
import org.tejo.iza.actor.ws.{TrelloWS, TrelloService}
import org.tejo.iza.rules.facts.CardFact
import scala.concurrent.duration._

import scala.concurrent.Await

class TrelloServiceSuite extends WordSpec with TestData {

  val actorSystem = ActorSystem("trello-suite-system")

  "TrelloService" when {
    "something" should {
      "do stuff"  in {

        val trelloService: TrelloService = {
          val builder = new com.ning.http.client.AsyncHttpClientConfig.Builder()
          val client = new play.api.libs.ws.ning.NingWSClient(builder.build())

          new TrelloWS(client)(actorSystem.dispatcher)
        }

        val cardsFuture = trelloService.cardFacts("54c4e18f2ab48fa15f8a1120")
        val cards: List[CardFact] = Await.result(cardsFuture, 25 seconds)

        val stirkarto = cards.find(_.name == "Stirkarto")

        println(stirkarto)

      }
    }
  }
}
