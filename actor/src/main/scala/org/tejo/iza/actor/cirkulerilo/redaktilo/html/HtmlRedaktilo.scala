package org.tejo.iza.actor.cirkulerilo.redaktilo.html

import java.net.URL

import org.fusesource.scalate._
import org.tejo.iza.actor.cirkulerilo.redaktilo.Redaktilo
import org.tejo.model.{Kontribuo, TEJO}

class HtmlRedaktilo  extends Redaktilo {


  override def redaktu(kontribuoj: List[Kontribuo], tejo: TEJO): String = {

    val engine = new TemplateEngine

    val params: Map[String, Any] = Map(
      "kontribuoj" -> kontribuoj, "tejo" -> tejo
    )

    val template: URL = getClass.getResource("templates/cirkulero.scaml")

    val output = engine.layout(template.getPath, params)

    output
  }
}
