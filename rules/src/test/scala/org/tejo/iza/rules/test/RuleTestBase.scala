package org.tejo.iza.rules.test

import clara.rules.{RuleLoader, WorkingMemory}
import org.scalatest.Suite
import org.tejo.iza.rules.ClojureNamespace

/** Functions to test rules
  */
trait RuleTestBase { this: Suite =>

  import scala.collection.convert.wrapAll._

  def loadRulesAndFacts(testData: RuleTestData): WorkingMemory = {
    val emptyMemory: WorkingMemory = RuleLoader.loadRules(ClojureNamespace.toLoadCirkuleriloRules)
    emptyMemory.insert(testData.facts).fireRules
  }

  def runTest(testData: RuleTestData): Unit = {
    testData.queryResultMap.map { case (query, result) =>

      val extracted = query.extract(loadRulesAndFacts(testData).query(query.toString))
      assert(extracted === result)

    }
  }
}

