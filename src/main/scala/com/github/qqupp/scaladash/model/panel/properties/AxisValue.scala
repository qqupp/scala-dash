package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json}

sealed trait AxisValue

object AxisValue {

  final case class Value(v: Double) extends AxisValue
  case object Zero extends AxisValue
  case object Auto extends AxisValue

  implicit val jsonEncoder: Encoder[AxisValue] = {
    case Zero => Json.fromInt(0)
    case Value(x) => Json.fromDoubleOrNull(x)
    case Auto => Json.Null
  }

}
