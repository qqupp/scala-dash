package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json, JsonObject}
import io.circe.syntax._
import io.circe.literal._

final case class Axes(leftY: YAxis, rightY: YAxis, xAxis: XAxis)

object Axes {

  val default: Axes = Axes(YAxis(), YAxis(show = false), XAxis.default)

  implicit val jsonEncoder: Encoder[Axes] =
    axes =>
      JsonObject(
        "yaxes" -> List(axes.leftY, axes.rightY).asJson,
        "xaxis" -> axes.xAxis.asJson
      ).asJson

}
