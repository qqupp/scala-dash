package com.github.qqupp.scaladash.model.panel

import io.circe.{Json, JsonObject}


sealed abstract class PointsMode(val value: Boolean){ self =>
  def asJson: Json = Json.fromJsonObject(
    JsonObject("points" -> Json.fromBoolean(value))
  )
}

case object PointsMode {

  case object NoPoints extends PointsMode(false)
  final case class Points(radius: Double) extends PointsMode(true)

}
