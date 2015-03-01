package org.tejo.iza.rules

import clara.rules.{WorkingMemory, QueryResult}
import java.lang.Iterable

/** Objects representing string queries in .clj code. They also handles
  * extracting results and holds result type as well.
  */
trait ClaraQuery[T] {
  def toString: String

  def extract(it:Iterable[QueryResult]): T

  def apply(wm: WorkingMemory): T = extract(wm.query(toString))

}
