package com.github.qqupp.scaladash.model.alert

import com.github.qqupp.scaladash.model.query.Query
import com.github.qqupp.scaladash.model.time.Duration
import com.github.qqupp.scaladash.model.time.Duration.Minutes
import io.circe.Json
import io.circe.literal._

final case class Condition(reducer: Reducer, query: Query, evaluator: Evaluator, duration: Duration, operatorType: OperatorType, datasourceId: Int) {

  def build(toBuildQueries: List[(String, Query)]): Json = {
    val matchingQuery = toBuildQueries.find{ case (_, candidateQuery)  => candidateQuery == query}

    val queryRefId = matchingQuery.fold("notFound"){ case (id, _) => id }
    val queryJson = matchingQuery.fold(json"{}"){ case (id, _) => query.build(id)}

    json"""{
      "evaluator": ${evaluator}
      ,
      "operator": {
        "type": $operatorType
      }
      ,
      "query": {
        "datasourceId": $datasourceId,
        "model": $queryJson,
        "params": [${s"${queryRefId}"}, $duration, "now"]
      }
      ,
      "reducer": {
        "params": [],
        "type": $reducer
      }
      ,
      "type": "query"
    }"""
  }
}

object Condition {

  def apply(query: Query, evaluator: Evaluator): Condition =
    Condition(
      query = query,
      evaluator = evaluator,
      duration = Minutes(5),
      operatorType = OperatorType.And,
      reducer = Reducer.Last,
      datasourceId = 1
    )

}
