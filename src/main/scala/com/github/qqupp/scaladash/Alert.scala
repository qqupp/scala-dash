package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.okish.{ExecutionErrorState, NoDataState, Notification}
import com.github.qqupp.scaladash.utils.JsonUtils._
import io.circe.Json
import io.circe.literal._

final case class Alert(name: String,
                       frequencySeconds: Int,
                       message: Option[String],
                       noDataState: NoDataState,
                       executionErrorState: ExecutionErrorState,
                       conditions: List[Condition],
                       notifications: List[Notification]
                      ) {

  def withNotification(notification: Notification): Alert =
    copy(notifications = notifications ++ List(notification))

  def withCondition(condition: Condition): Alert =
    this.copy(conditions = conditions ++ List(condition))

  def build(metrics: List[(String, Metric)]): Json = {

    val alertJson = json"""{
      "conditions": ${conditions.map(_.build(metrics))},
      "executionErrorState": $executionErrorState,
      "frequency": ${frequencySeconds.toString + "s"} ,
      "handler": 1,
      "name": $name,
      "noDataState": $noDataState,
      "notifications": $notifications
    }"""

    alertJson.addOpt("message", message)
  }

}

object Alert {

  def apply(name: String, frequencySeconds: Int): Alert =
    Alert(name = name,
      frequencySeconds = frequencySeconds,
      message = None,
      noDataState = NoDataState.NoData,
      executionErrorState = ExecutionErrorState.Alerting,
      conditions = List.empty,
      notifications = List.empty
    )

}
