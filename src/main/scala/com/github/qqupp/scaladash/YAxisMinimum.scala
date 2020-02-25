package com.github.qqupp.scaladash

import io.circe.{Encoder, Json}

sealed trait YAxisMinimum

object YAxisMinimum {

  case object Zero extends YAxisMinimum
  case object Auto extends YAxisMinimum

  implicit val jsonEncoder: Encoder[YAxisMinimum] = {
    case Zero => Json.fromInt(0)
    case Auto => Json.Null
  }

}
