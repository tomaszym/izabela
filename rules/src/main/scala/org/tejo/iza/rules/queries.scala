package org.tejo.iza.rules

/** Objects representing string queries in .clj code.
  * Just to avoid typos.
  */
sealed abstract class QueryIdentifier {
  def stringId: String
  // TODO resultKeys?
}

case object AlvokuQueryId extends QueryIdentifier {
  def stringId: String = "cirkulerilo/alvoku-query"
}
case object MemoriguQueryId extends QueryIdentifier {
  def stringId: String = "cirkulerilo/memorigu-query"
}
case object KunmetuQueryId extends QueryIdentifier {
  def stringId: String = "cirkulerilo/kunmetu-query"
}

case object KontribuintojQueryId extends QueryIdentifier {
  def stringId: String = "cirkulerilo/kontribuintoj-query"
}