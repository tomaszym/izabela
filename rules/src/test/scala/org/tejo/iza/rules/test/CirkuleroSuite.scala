package org.tejo.iza.rules.test

import org.scalatest.FunSuite
import org.tejo.iza.rules.test.cirkulero.{KunmetuTestData, DuaMemorigoTestData, UnuaMemorigoTestData, DissenduAlvokonTestData}

class CirkuleroSuite extends FunSuite with RuleTestBase {

  test("Dissendi alvokon")(runTest(DissenduAlvokonTestData))
  test("Memorigo a week before")(runTest(UnuaMemorigoTestData))
  test("Memorigo a day before")(runTest(DuaMemorigoTestData))
  test("Kunmetu")(runTest(KunmetuTestData))


}
