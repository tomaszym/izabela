package org.tejo.model

case class Markdown(str: String) extends AnyVal

case class Kontribuo(text: Markdown, autoro: Persono)
