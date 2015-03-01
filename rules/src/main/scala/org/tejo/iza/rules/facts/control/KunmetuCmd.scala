package org.tejo.iza.rules.facts.control

import scala.beans.BeanInfo

/** An instance is inserted to the memory when rule discovers
  * the situation. Actors might query for it and take actions
  * accordingly.
  */
@BeanInfo case class KunmetuCmd(listId: String)
