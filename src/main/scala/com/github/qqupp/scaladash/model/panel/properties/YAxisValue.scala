package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json}

sealed trait YAxisValue

object YAxisValue {

  final case class Value(v: Double) extends YAxisValue
  case object Zero extends YAxisValue
  case object Auto extends YAxisValue

  implicit val jsonEncoder: Encoder[YAxisValue] = {
    case Zero => Json.fromInt(0)
    case Value(x) => Json.fromDoubleOrNull(x)
    case Auto => Json.Null
  }

}
