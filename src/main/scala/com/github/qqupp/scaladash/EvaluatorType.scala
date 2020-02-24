package com.github.qqupp.scaladash

import io.circe.{Encoder, Json}

sealed trait EvaluatorType
object EvaluatorType {

  case object GreaterThan extends EvaluatorType
  case object LessThan extends EvaluatorType
  case object Outside extends EvaluatorType
  case object Within extends EvaluatorType
  case object NoValue extends EvaluatorType

  implicit val jsonEncoder: Encoder[EvaluatorType] =
    state =>
      Json.fromString(
        state match {
          case GreaterThan => "gt"
          case LessThan => "lt"
          case Outside => "outside_range"
          case Within => "within_range"
          case NoValue => "no_value"
        }
      )

}
