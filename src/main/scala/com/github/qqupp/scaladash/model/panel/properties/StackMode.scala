package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json, JsonObject}
import io.circe.syntax._
import io.circe.literal._

sealed abstract class StackMode(val value: Boolean, val percent: Boolean)


object StackMode {

  case object StackedPercent extends StackMode(true, true)
  case object Stacked extends StackMode(true, false)
  case object Unstacked extends StackMode(false, false)

  implicit val jsonEncoder: Encoder[StackMode] =
    stackMode =>
      json"""{
          "stack": ${stackMode.value},
          "percentage" : ${stackMode.percent},
          "tooltip": {
            "value_type": "individual"
          }
        }
      """

}
