package org.tejo.iza.rules.test

import org.scalatest.FunSuite
import org.tejo.iza.rules.test.cirkulero.DissenduAlvokonData

class CirkuleroSuite extends FunSuite with RuleTestBase {

  test("Dissendi alvokon")(runTest(DissenduAlvokonData))


}
