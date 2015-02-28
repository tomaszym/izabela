package org.tejo.iza.rules

/** Objects representing string queries in .clj code.
  * Just to avoid typos.
  */
sealed abstract class QueryIdentifier {
  def stringId: String

  def resultQueryKey: String
}

case object AlvokuQueryId extends QueryIdentifier {
  def stringId: String = "cirkulerilo/alvoku-query"

  def resultQueryKey: String = "?alvoku"
}
case object MemoriguQueryId extends QueryIdentifier {
  def stringId: String = "cirkulerilo/memorigu-query"
  def resultQueryKey: String = "?memorigu"
}
case object KunmetuQueryId extends QueryIdentifier {
  def stringId: String = "cirkulerilo/kunmetu-query"
  def resultQueryKey: String = "?kunmetu"
}

case object KontribuintojQueryId extends QueryIdentifier {
  def stringId: String = "cirkulerilo/kontribuintoj-query"
  def resultQueryKey: String = "?kontribuintoj"
}