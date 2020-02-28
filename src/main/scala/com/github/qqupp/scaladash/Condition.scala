package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.okish.{EvaluatorType, OperatorType, Reducer}
import io.circe.Json
import io.circe.literal._
/*

class Condition:
    def __init__(self, metric, evaluator_type, value, operator_type=OperatorType.And, reducer=Reducer.Last, datasource_id=1):
        self.metric = metric
        self.evaluator_type = evaluator_type
        self.value = value
        self.operator_type = operator_type
        self.reducer = reducer
        self.datasource_id = datasource_id

    def build(self, panel_metrics):
        filtered = filter(lambda possible_metric: possible_metric["target"] == self.metric.target, panel_metrics)
        matching_metric = list(filtered).pop(0)
        return {
            "evaluator": {
                "params": [self.value],
                "type": self.evaluator_type
            },
            "operator": {
                "type": self.operator_type
            },
            "query": {
                "datasourceId": self.datasource_id,
                "model": {
                    "refId": matching_metric["refId"],
                    "target": matching_metric["target"]
                },
                "params": [matching_metric["refId"], "5m", "now"]
            },
            "reducer": {
                "params": [],
                "type": self.reducer
            },
            "type": "query"
        }


 */
final case class Condition(metric: Metric, evaluator_type: EvaluatorType, value: Int, operator_type: OperatorType, reducer: Reducer, datasource_id: Int) {
  def build(panel_metrics: List[(String, Metric)]): Json = {
    val matchingMetric = panel_metrics.find{ case (_, candidateMetric)  => candidateMetric == metric}

    val metricRefId = matchingMetric.fold("notFound"){ case (id, _) => id }
    val metricJson = matchingMetric.fold(json"{}"){ case (id, metric) => metric.build(id)}

    json"""{
      "evaluator": {
        "params": [$value],
        "type": $evaluator_type
      }
      ,
      "operator": {
        "type": $operator_type
      }
      ,
      "query": {
        "datasourceId": $datasource_id,
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
  def apply(metric: Metric, evaluator_type: EvaluatorType, value: Int): Condition = Condition(metric, evaluator_type, value, OperatorType.And, Reducer.Last, 1)
}
