package org.tejo.iza.tests

import org.scalatest.WordSpec
import org.tejo.iza.actor.cirkulerilo.redaktilo.html.HtmlRedaktilo
import org.tejo.iza.rules.facts.CardFact
import org.tejo.model.{Cirkulero, Kontribuo, MarkdownValue}


class RedaktiloSuite extends WordSpec with TestData {

  "A HTML redaktilo" when {
    "There are contributions" should {
      "produce result that contains contribution texts and authors"  in {
        val redaktilo = new HtmlRedaktilo

        val tejo = tejoData

        val fact = CardFact("id", "listid", "lukasz@tejo.org", desc = "Faris:\n-------\n- verkis KER-raporton pri trejnado en Kenjo (https://docs.google.com/document/d/1bheyVA5uDn14MIPYJoWaIzgWvUqy0M_UkFdKSSMT5qw/edit?usp=sharing)\n- partoprenos kunvenon de PEJ\n- kunlaboris pri finpretigo kaj sendado de EVS-subvencipeto\n\nFaras:\n-------\n\n- kunlaboras kun afrikaj subvenciitoj pri ilia vojaĝo al IJK\n- kune kun Tim kaj Tuśka laboras pri la nova tejo.org kaj ĝia tradukado (kaj problemoj aperantaj dum tradukado)\n- kunlaboras pri kreado de katalogo de trejnmaterialoj ĉe tejo.org\n- kolektas informojn pri subvenciebloj por Afriko\n\nFaros:\n-------\n- finpreparos la trijaran strategio de TEJO\n- verkos artikolon por Revuo Esperanto")

//        val kontribuoj: List[Kontribuo] = List(// cardFacts.filterNot(_.name == "Stirkarto").map(tejo.kontribuo).flatten
//          Kontribuo(
//            text = MarkdownValue(),
//            autoro = tejoData.aktivuloj.find(_.retadreso == "lukasz@tejo.org").get
//          )
//        )

        val res = redaktilo.redaktu(Cirkulero(tejo, fact::Nil))

        assert(res.contains("<ul>"))
        assert(res.contains("verkis KER-raporton"))
        assert(res.contains("Łukasz ŻEBROWSKI"))
      }
    }
  }
}



