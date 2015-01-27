package org.tejo.stashek.cirkulerilo

import clara.rules.{RuleLoader, WorkingMemory}
import org.tejo.stashek.cirkulerilo.facts.TrelloFacts

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

class Cirkulerilo {

  def run: Unit = {

    val trelloClient = TrelloClientFacade("ea98147379cdbf813a69190a9228ca34","53aef54598654cd1f4486f08")

    import trelloClient.client.system.dispatcher

    val factsFuture: Future[TrelloFacts] = trelloClient.facts

    val facts = Await.result(factsFuture, 10 seconds)

    println(facts)

    Thread.currentThread.setContextClassLoader(classOf[Cirkulerilo].getClassLoader)

    val emptyMemory: WorkingMemory = RuleLoader.loadRules("cirkulerilo")


    // Insert some facts and fire the rules.
    val memory: WorkingMemory = emptyMemory.insert(facts.asJavaIterable).fireRules

//    // Run the promotion query and print the results.
//    import scala.collection.JavaConversions._
//    for (result <- memory.query(QUERY)) {
//      System.out.println("Query result: " + result.getResult("?promotion"))
//    }
  }
}


