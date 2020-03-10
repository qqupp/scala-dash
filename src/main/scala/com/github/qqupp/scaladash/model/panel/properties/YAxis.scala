package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties.YAxisScale.Linear
import com.github.qqupp.scaladash.model.panel.properties.YAxisUnit.Misc.NoFormat
import com.github.qqupp.scaladash.model.panel.properties.YAxisValue.Auto
import io.circe.Encoder
import io.circe.generic.semiauto._

final case class YAxis(show: Boolean, format: YAxisUnit, logBase: YAxisScale, min: YAxisValue, max: YAxisValue, label: String, decimals: Option[Int])

object YAxis {

  def apply(format: YAxisUnit = NoFormat, show: Boolean = true): YAxis =
    YAxis(
      show = show,
      format = format,
      logBase = Linear,
      min = Auto,
      max = Auto,
      label = "",
      decimals = None
    )

  implicit val jsonEncoder: Encoder[YAxis] = deriveEncoder[YAxis]


}
