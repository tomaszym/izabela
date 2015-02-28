package org.tejo.iza.actor.msg

import clara.rules.QueryResult
import org.tejo.iza.rules.QueryIdentifier

/** Rule engine makes a query and responses
  * with result
  */
case class QueryMsg(id: QueryIdentifier)

case class ResponseMsg(results: List[QueryResult])
