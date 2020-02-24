package com.github.qqupp.scaladash

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
class Condition {

}
