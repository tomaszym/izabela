package org.tejo.iza.actor.msg

import clara.rules.QueryResult

/** Rule engine makes a query and responses
  * with result
  */
case class Query(queryName: String)

case class Response(results: List[QueryResult])
