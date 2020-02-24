package com.github.qqupp.scaladash

/*
class Alert:
    def __init__(self, name, frequency, message=None, no_data_state=NoDataState.NoData, execution_error_state=ExecutionErrorState.Alerting):
        self.name = name
        self.frequency = frequency
        self.message = message
        self.conditions = []
        self.notifications = []
        self.no_data_state = no_data_state
        self.execution_error_state = execution_error_state

    def with_notification(self, notification):
        self.notifications.append(notification)
        return self

    def with_condition(self, condition):
        self.conditions.append(condition)
        return self

    def build(self, panel_metrics):
        alert = {
            "conditions": list(map(lambda condition: condition.build(panel_metrics), self.conditions)),
            "executionErrorState": self.execution_error_state,
            "frequency": "%ds" % self.frequency,
            "handler": 1,
            "name": self.name,
            "noDataState": self.no_data_state,
            "notifications": list(map(lambda notification: notification.build(), self.notifications))
        }
        if self.message:
            alert["message"] = self.message
        return alert

 */
class Alert {

}
