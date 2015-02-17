package org.tejo.iza.actor

trait CirkuleroHandler {

  /** Dissends information about new cirkulero issue
    * to those, who have not their trello acounts
    * @param except trello logins of these who are on trello, so don't need email spam
    */
  def alvoko(except: List[String])
}
