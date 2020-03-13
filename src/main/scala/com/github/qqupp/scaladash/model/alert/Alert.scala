package com.github.qqupp.scaladash.model.alert

import com.github.qqupp.scaladash.model.alert.OperatorType._
import com.github.qqupp.scaladash.model.query.Query
import com.github.qqupp.scaladash.utils.JsonUtils._
import io.circe.Json
import io.circe.literal._
import io.circe.syntax._

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

  // multiple conditions are evaluated foldLeft ((A op B) op C)
  def withCondition(condition: Condition): Alert =
    this.copy(conditions = conditions ++ List(condition))

  def andCondition(condition: Condition): Alert = opCondition(condition, And)
  def orCondition(condition: Condition): Alert = opCondition(condition, Or)

  private def opCondition(condition: Condition, opT: OperatorType): Alert =
    withCondition(condition.copy(operatorType = opT))

  def build(panelIdAndQueries: List[(String, Query)]): Json = {

    val conditionsJ: Json = conditions.map(_.build(panelIdAndQueries)).asJson
    val executionErrorStateJ: Json = executionErrorState.asJson
    val frequencySecondsJ: Json = Json.fromString(frequencySeconds.toString + "s")

    val alertJson = json"""{
      "conditions": $conditionsJ,
      "executionErrorState": $executionErrorStateJ,
      "frequency": $frequencySecondsJ ,
      "handler": 1,
      "name": $name,
      "noDataState": $noDataState,
      "notifications": $notifications
    }"""

    alertJson.addOpt("message", message)
  }

}

object Alert {

  def apply(name: String, message: String = "", frequencySeconds: Int = 30): Alert =
    Alert(name = name,
      frequencySeconds = frequencySeconds,
      message = if (message.isEmpty) None else Some(message),
      noDataState = NoDataState.NoData,
      executionErrorState = ExecutionErrorState.Alerting,
      conditions = List.empty,
      notifications = List.empty
    )

}
