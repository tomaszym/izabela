package org.tejo.iza.rules

import clara.rules.QueryResult
import java.lang.Iterable

/** Usually a query has only one result. The trait
  * provides support for this common case.
  * @tparam T type returned by the query
  */
trait SingleResultQuery[T] extends ClaraQuery[Option[T]] {

  protected def resultQueryKey: String

  def extract(it: Iterable[QueryResult]): Option[T] = {
    import scala.collection.convert.wrapAsScala._
    it.headOption.map(_.getResult(resultQueryKey).asInstanceOf[T])
  }
}
