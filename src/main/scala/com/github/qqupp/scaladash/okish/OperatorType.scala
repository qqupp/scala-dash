package com.github.qqupp.scaladash.okish

import io.circe.{Encoder, Json}

sealed trait OperatorType

object OperatorType {

  case object And extends OperatorType
  case object Or extends OperatorType

  implicit val jsonEncoder: Encoder[OperatorType] =
    operatorType =>
      Json.fromString(
        operatorType match {
          case And => "and"
          case Or => "or"
        }
      )
}
