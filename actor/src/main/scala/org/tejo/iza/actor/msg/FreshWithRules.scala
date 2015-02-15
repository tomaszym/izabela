package org.tejo.iza.actor.msg

/** 1. Purges facts and rules 2. sets new rules
  * 
  * @param ruleNames names of rules to be set after reset
  */
case class FreshWithRules(ruleNames: Array[String])
