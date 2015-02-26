package org.tejo.iza.rules.facts.control

import scala.beans.BeanInfo

/** Class inserted to working memory when rule discovers
  * the situation. Other actors might then query for it
  * and take actions.
  */
@BeanInfo case class DissenduAlvokon(due: String)
