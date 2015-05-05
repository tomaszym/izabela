package org.tejo.iza.tests.redaktilo

import org.scalatest.WordSpec
import org.tejo.iza.actor.cirkulerilo.redaktilo.html.HtmlRedaktilo
import org.tejo.iza.tests.it.TestData


class RedaktiloSuite extends WordSpec with TestData {

  "A HTML redaktilo" when {
    "There are contributions" should {
      "produce result that contains contribution texts and authors"  in {
        val redaktilo = new HtmlRedaktilo

        val tejo = tejoData

        val kontribuoj = cardFacts.filterNot(_.name == "Stirkarto").map(tejo.kontribuo)

        val res = redaktilo.redaktu(kontribuoj, tejo)

        assert(res.contains("Mi faris multe"))
        assert(res.contains("Mi faris malmulte"))
        assert(res.contains("Łukasz ŻEBROWSKI"))
        assert(res.contains("Tomasz SZYMULA"))
      }
    }
  }
}



