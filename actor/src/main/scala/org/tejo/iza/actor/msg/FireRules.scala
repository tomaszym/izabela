package org.tejo.iza.actor.msg

/** Fires currently loaded rules.
 */
case object FireRules

// perhaps: case class FireRulesWithTime(currentTime: DateTime) ?
// so the question is: how to represent time in the rules?