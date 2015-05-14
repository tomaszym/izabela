package org.tejo.model

case class MarkdownValue(str: String) extends AnyVal

case class Kontribuo(text: MarkdownValue, autoro: Persono)
