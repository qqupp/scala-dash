package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json, JsonObject}
import io.circe.syntax._
import io.circe.literal._

sealed abstract class StackMode(val value: Boolean, val percent: Boolean, val valueType: String)


object StackMode {

  sealed abstract class StackedTooltipType(val valueType: String)
  case object Individual extends StackedTooltipType("individual")
  case object Cumulative extends StackedTooltipType("cumulative")

  case class StackedPercent(tooltipMode: StackedTooltipType) extends StackMode(true, true, tooltipMode.valueType)
  case class Stacked(tooltipMode: StackedTooltipType) extends StackMode(true, false, tooltipMode.valueType)
  case object Unstacked extends StackMode(false, false, "individual")

  implicit val jsonEncoder: Encoder[StackMode] =
    stackMode =>
      json"""{
          "stack": ${stackMode.value},
          "percentage" : ${stackMode.percent},
          "tooltip": {
            "value_type": ${stackMode.valueType}
          }
        }
      """

}
