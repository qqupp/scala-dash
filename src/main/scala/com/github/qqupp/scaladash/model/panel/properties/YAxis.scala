package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties.YAxisScale.Linear
import com.github.qqupp.scaladash.model.panel.properties.YAxisValue.Auto
import io.circe.{Encoder, Json}
import io.circe.generic.semiauto._

final case class YAxis(show: Boolean, unit: YAxisUnit, logBase: YAxisScale, min: YAxisValue, max: YAxisValue, label: String, decimals: Option[Int])

object YAxis {

  val default: YAxis =
    YAxis(
      show = true,
      unit = YAxisUnit.Misc.NoFormat,
      logBase = Linear,
      min = Auto,
      max = Auto,
      label = "",
      decimals = None
    )

  implicit val jsonEncoder: Encoder[YAxis] = deriveEncoder[YAxis]


}
