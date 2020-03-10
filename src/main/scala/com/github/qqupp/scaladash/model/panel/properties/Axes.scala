package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json, JsonObject}
import io.circe.syntax._
import io.circe.literal._

final case class Axes(leftY: YAxis, rightY: YAxis)

object Axes {

  val default: Axes = Axes(YAxis(), YAxis(show = false))

  implicit val jsonEncoder: Encoder[Axes] =
    axes =>
      JsonObject(
        "yaxes" -> List(axes.leftY, axes.rightY).asJson,
        "xaxis" -> json"""{
    "show": false,
    "mode": "time",
    "name": null,
    "values": [],
    "buckets": null
  }"""
      ).asJson

}
