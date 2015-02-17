package org.tejo.iza.rules.test

import clara.rules.{RuleLoader, WorkingMemory}
import org.scalatest.Suite

/** Functions to test rules
  */
trait RuleTestBase { this: Suite =>

  def runTest(testData: RuleTestData): Unit = {

    val emptyMemory: WorkingMemory = RuleLoader.loadRules(testData.clojureNamespace)

    import scala.collection.convert.wrapAll._
    val memory: WorkingMemory = emptyMemory.insert(testData.facts).fireRules


    testData.queryResultMap.map { case (query, (resultName, result)) =>

      assert(memory.query(query).headOption.map(_.getResult(resultName)) === result) // TODO .map(_.getResult("alvoko"))
    }

  }
}
