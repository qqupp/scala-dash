package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Json, JsonObject}


sealed abstract class PointsMode(val value: Boolean, val rad: Float){ self =>
  def asJson: Json = Json.fromJsonObject(
    JsonObject(
      "points" -> Json.fromBoolean(value),
      "pointradius" -> Json.fromFloat(rad).getOrElse(Json.fromInt(0))
    )
  )
}

case object PointsMode {

  case object NoPoints extends PointsMode(false, 0F)
  final case class Points(radius: Float) extends PointsMode(true, radius)

}
