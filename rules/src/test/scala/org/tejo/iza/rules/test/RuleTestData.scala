package org.tejo.iza.rules.test

import org.tejo.iza.rules.ClaraQuery

/** Base class for instances which contains data
  * which tests one rule: query is made and result
  * is (not) expected.
  *
  */
abstract class RuleTestData {

  def facts: List[Any]

  def queryResultMap: Map[ClaraQuery[_], Any]
}
