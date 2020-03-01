package com.github.qqupp.scaladash.model.alert

import com.github.qqupp.scaladash.model.metric.Metric
import io.circe.Json
import io.circe.literal._

final case class Condition(metric: Metric, evaluator: Evaluator, reducer: Reducer, operatorType: OperatorType, datasourceId: Int) {

  def build(toBuildMetrics: List[(String, Metric)]): Json = {
    val matchingMetric = toBuildMetrics.find{ case (_, candidateMetric)  => candidateMetric == metric}

    val metricRefId = matchingMetric.fold("notFound"){ case (id, _) => id }
    val metricJson = matchingMetric.fold(json"{}"){ case (id, metricf) => metric.build(id)}

    json"""{
      "evaluator": ${evaluator.asJson}
      ,
      "operator": {
        "type": $operatorType
      }
      ,
      "query": {
        "datasourceId": $datasourceId,
        "model": $metricJson,
        "params": [${s"${metricRefId}"}, "5m", "now"]
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

  def apply(metric: Metric, evaluator: Evaluator): Condition =
    Condition(
      metric = metric,
      evaluator = evaluator,
      operatorType = OperatorType.And,
      reducer = Reducer.Last,
      datasourceId = 1
    )

}
