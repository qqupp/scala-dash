package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.okish.{EvaluatorType, OperatorType, Reducer}
import io.circe.Json
import io.circe.literal._

final case class Condition(metric: Metric, evaluatorType: EvaluatorType, value: Int, operatorType: OperatorType, reducer: Reducer, datasourceId: Int) {

  def build(toBuildMetrics: List[(String, Metric)]): Json = {
    val matchingMetric = toBuildMetrics.find{ case (_, candidateMetric)  => candidateMetric == metric}

    val metricRefId = matchingMetric.fold("notFound"){ case (id, _) => id }
    val metricJson = matchingMetric.fold(json"{}"){ case (id, metricf) => metric.build(id)}

    json"""{
      "evaluator": {
        "params": [$value],
        "type": $evaluatorType
      }
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

  def apply(metric: Metric, evaluatorType: EvaluatorType, value: Int): Condition =
    Condition(
      metric = metric,
      evaluatorType = evaluatorType,
      value = value,
      operatorType = OperatorType.And,
      reducer = Reducer.Last,
      datasourceId = 1
    )

}
