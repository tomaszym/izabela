package org.tejo.iza.actor.cirkulerilo

import org.tejo.iza.actor.CirkuleroHandler


class CirkuleriloActorDelegator extends CirkuleroHandler {

  /** Dissends information about new cirkulero issue
    * to those, who have not their trello acounts
    * @param except trello logins of these who are on trello, so don't need email spam
    */
  override def alvoko(except: List[String]): Unit = {
    // cirkuleriloActor ! DoAlvoko
    ???
  }
}
