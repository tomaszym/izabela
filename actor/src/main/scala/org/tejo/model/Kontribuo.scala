package org.tejo.model

case class MarkdownValue(str: String) extends AnyVal

sealed trait KontribuEnskribo {
  def text: MarkdownValue
  def autoro: Persono
}

case class Kontribuo(text: MarkdownValue, autoro: Persono) extends KontribuEnskribo
case class MankoDeKontribuo(autoro: Persono) extends KontribuEnskribo {
  override def text = MarkdownValue("Ne kontribuis")
}