package org.tejo.iza.rules

import clara.rules.QueryResult
import java.lang.Iterable

import org.tejo.iza.rules.facts.CardFact
import org.tejo.iza.rules.facts.control.{KunmetuCmd, MemoriguCmd, AlvokuCmd}

import scala.language.higherKinds

case object AlvokuQuery extends SingleResultQuery[AlvokuCmd] {
  override def toString: String = "cirkulerilo/alvoku-query"

  protected def resultQueryKey: String = "?alvoku"
}
case object MemoriguQuery extends SingleResultQuery[MemoriguCmd] {
  override def toString: String = "cirkulerilo/memorigu-query"
  protected def resultQueryKey: String = "?memorigu"
}
case object KunmetuQuery extends SingleResultQuery[KunmetuCmd] {
  override def toString: String = "cirkulerilo/kunmetu-query"
  protected def resultQueryKey: String = "?kunmetu"
}

case object KontribuintojQuery extends ClaraQuery[List[CardFact]] {
  override def toString: String = "cirkulerilo/kontribuintoj-query"
  private def resultQueryKey: String = "?kontribuintoj"

  def extract(it: Iterable[QueryResult]): List[CardFact] = {
    import scala.collection.convert.wrapAll._

    it.toList.flatMap(_.getResult(resultQueryKey).asInstanceOf[java.util.List[CardFact]].toList)
  }
}