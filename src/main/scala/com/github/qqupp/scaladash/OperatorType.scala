package com.github.qqupp.scaladash

import io.circe.{Encoder, Json}

sealed trait OperatorType

object OperatorType {

  case object And extends OperatorType
  case object Or extends OperatorType

  implicit val jsonEncoder: Encoder[OperatorType] =
    state =>
      Json.fromString(
        state match {
          case And => "and"
          case Or => "or"
        }
      )
}
