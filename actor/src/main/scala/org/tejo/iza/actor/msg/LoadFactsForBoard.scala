package org.tejo.iza.actor.msg


/** When a message is received an actor
  * fetches the facts via HTTP API, deserializes,
  * converts to fact objects and puts into the
  * knowledge base
  *
  * @param id
  */
case class LoadFactsForBoard(id: String)
