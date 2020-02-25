package com.github.qqupp.scaladash.okish

import io.circe.{Encoder, Json}

sealed trait FillStyle

object FillStyle {

  case object Filled extends FillStyle
  case object Unfilled extends FillStyle

  implicit val jsonEncoder: Encoder[FillStyle] =
    fillStyle =>
      Json.fromInt(
        fillStyle match {
          case Filled => 10
          case Unfilled => 0
        }
      )

}
