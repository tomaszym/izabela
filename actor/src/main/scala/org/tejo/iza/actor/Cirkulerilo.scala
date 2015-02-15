//package org.tejo.iza.client
//
//import clara.rules.{RuleLoader, WorkingMemory}
//
//import scala.concurrent.duration._
//import scala.concurrent.{Await, Future}
//
//class Cirkulerilo {
//
//  def run: Unit = {
//
////    Thread.currentThread.setContextClassLoader(classOf[Cirkulerilo].getClassLoader)
//
//    val trelloClient = TrelloClientFacade("ea98147379cdbf813a69190a9228ca34","54c4e18f2ab48fa15f8a1120")
//
//    val factsFuture: Future[TrelloFacts] = trelloClient.facts
//
//    val facts = Await.result(factsFuture, 10 seconds)
//
//    println(facts)
//
//
//    val emptyMemory: WorkingMemory = RuleLoader.loadRules("cirkulerilo")
//
//
//    // Insert some facts and fire the rules.
//    val memory: WorkingMemory = emptyMemory.insert(facts.asJavaIterable).fireRules
//
//
//    println("rules fired!")
//
////    // Run the promotion query and print the results.
////    import scala.collection.JavaConversions._
////    for (result <- memory.query(QUERY)) {
////      System.out.println("Query result: " + result.getResult("?promotion"))
////    }
//  }
//}
//
//
