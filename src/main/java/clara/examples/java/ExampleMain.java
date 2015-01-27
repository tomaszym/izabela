package clara.examples.java;

import clara.rules.QueryResult;
import clara.rules.RuleLoader;
import clara.rules.WorkingMemory;
import org.tejo.stashek.cirkulerilo.TrelloClientFacade;
import org.tejo.stashek.cirkulerilo.facts.Customer;
import org.tejo.stashek.cirkulerilo.facts.ListFact;
import org.tejo.stashek.cirkulerilo.facts.TrelloFacts;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
* Main class for our example.
*/
public class ExampleMain {

    /**
     * Since the working memory is immutable, we can load it once and reuse it for every
     * new working memory we need. This doesn't really matter in this example, but would
     * be a good pattern for code that frequently creates sessions to evaluate a set of
     * objects.
     */



    /**
     * Query used in the example.
     */
    static final String QUERY = "jshopping/get-promotions";

    /**
     * Main function for the example.
     */
    public static void main(String [] args) {

        Thread.currentThread().setContextClassLoader(ExampleMain.class.getClassLoader());
//
        TrelloClientFacade trelloClient = new TrelloClientFacade("ea98147379cdbf813a69190a9228ca34","53aef54598654cd1f4486f08");

        TrelloFacts facts = trelloClient.factsBlocking();


//        val emptyMemory: WorkingMemory = RuleLoader.loadRules("cirkulerilo")
        WorkingMemory emptyMemory = RuleLoader.loadRules("cirkulerilo");
//        WorkingMemory emptyMemory = RuleLoader.loadRules("jshopping");


        // Insert some facts and fire the rules.
//        val memory: WorkingMemory = emptyMemory.insert(facts.asJavaIterable).fireRules

//        ListFact fact = new ListFact("1337", "Cirkulero");
//        LinkedList<ListFact> list = new LinkedList<ListFact>();

//        list.add(fact);
//


        WorkingMemory memory = emptyMemory.insert(facts.asJavaIterable()).fireRules();





//        System.out.println(Thread.currentThread().getContextClassLoader().toString()+ "blabla");
//        Thread.currentThread().setContextClassLoader(ExampleMain.class.getClassLoader());
//        System.out.println(ExampleMain.class.getClassLoader().toString());
//
//
//        final WorkingMemory emptyMemory = RuleLoader.loadRules("jshopping");
//        // Create some facts to add to the working memory.
//        List facts = Arrays.asList(new Customer("Tim", true), new Order(250));
//
//        // Insert some facts and fire the rules.
//        WorkingMemory memory = emptyMemory.insert(facts).fireRules();
//
        // Run the promotion query and print the results.
//        for (QueryResult result: memory.query(QUERY)) {
//            System.out.println("Query result: " + result.getResult("?promotion"));
//        }
    }
}
