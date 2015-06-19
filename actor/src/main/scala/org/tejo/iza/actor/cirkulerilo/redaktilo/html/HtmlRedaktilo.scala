package org.tejo.iza.actor.cirkulerilo.redaktilo.html

import java.io.File
import java.net.URL

import org.fusesource.scalate._
import org.tejo.iza.actor.cirkulerilo.redaktilo.Redaktilo
import org.tejo.model.{Cirkulero, TEJO, Kontribuo}

class HtmlRedaktilo extends Redaktilo {

  override def redaktu(cirk: Cirkulero): String = {
    val tejo = cirk.tejo

    val engine = new TemplateEngine

    val params: Map[String, Any] = Map(
      "cirk" -> cirk
    )

    val template: URL = getClass.getResource("/cirkulero.scaml")

    val output = engine.layout(template.getPath, params)


    output
  }
}
