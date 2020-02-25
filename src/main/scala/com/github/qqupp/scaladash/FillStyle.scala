package com.github.qqupp.scaladash

import io.circe.{Encoder, Json}

sealed trait FillStyle
object FillStyle {

  case object Filled extends FillStyle
  case object Unfilled extends FillStyle

  implicit val jsonEncoder: Encoder[FillStyle] = {
    case Filled => Json.fromInt(10)
    case Unfilled => Json.fromInt(0)
  }
}
