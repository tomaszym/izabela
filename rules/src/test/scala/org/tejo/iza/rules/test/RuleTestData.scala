package org.tejo.iza.rules.test

/** Base class for instances which contains data
  * which tests one rule: query is made and result
  * is (not) expected.
  *
  */
abstract class RuleTestData {

  // TODO make use of it - factor out stuff like query names from TestData classes
  def clojureNamespace: String

  def facts: List[Any]

  def queryResultMap: Map[String, (String, Option[Any])]
}
