package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json}

sealed abstract class FillStyle(val value: Int)

object FillStyle {

  case object Filled extends FillStyle(10)
  case object HalfFilled extends FillStyle(5)
  case object Unfilled extends FillStyle(0)

  implicit val jsonEncoder: Encoder[FillStyle] =
    f => Json.fromInt(f.value)
}
