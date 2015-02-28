package org.tejo.iza.rules.test

import clara.rules.{RuleLoader, WorkingMemory}
import org.scalatest.Suite

/** Functions to test rules
  */
trait RuleTestBase { this: Suite =>

  import scala.collection.convert.wrapAll._

  def loadRulesAndFacts(testData: RuleTestData): WorkingMemory = {
    val emptyMemory: WorkingMemory = RuleLoader.loadRules(testData.clojureNamespace)
    emptyMemory.insert(testData.facts).fireRules
  }



  private def assertResult(testData: RuleTestData, memory: WorkingMemory) = {

    testData.queryResultMap.map { case (query, (resultName, result)) =>

      assert(memory.query(query).headOption.map(_.getResult(resultName)) === result) // TODO .map(_.getResult("alvoko"))
    }

  }

  /** Returning clojure lists brings some boilerplate code,
    * so we have another assert for it.
    *
    * @param testData
    * @param memory
    */
  private def assertWithClojureListResult(testData: RuleTestData, memory: WorkingMemory) = {

    testData.queryResultMap.map { case (query, (resultName, result)) =>

      assert(memory.query(query).headOption.map(_.getResult(resultName).asInstanceOf[java.util.List[_]].toList) === result)
    }
  }



  def runTest(testData: RuleTestData) = {
    assertResult(testData, loadRulesAndFacts(testData))
  }



  def runTestListOutput(testData: RuleTestData) = {
    assertWithClojureListResult(testData, loadRulesAndFacts(testData))
  }

}

