package com.github.qqupp.scaladash

import io.circe.{Encoder, Json}

sealed  trait VariableSort

object VariableSort {

  case object Disabled extends  VariableSort
  case object AlphaAsc extends  VariableSort
  case object AlphaDesc extends  VariableSort
  case object NumericalAsc extends  VariableSort
  case object NumericalDesc extends  VariableSort
  case object AlphaCaseInsensitiveAsc extends  VariableSort
  case object AlphaCaseInsensitiveDesc extends  VariableSort

  implicit val jsonEncoder: Encoder[VariableSort] =
    variableSort =>
      Json.fromInt(
        variableSort match {
          case Disabled => 0
          case AlphaAsc => 1
          case AlphaDesc => 2
          case NumericalAsc => 3
          case NumericalDesc => 4
          case AlphaCaseInsensitiveAsc => 5
          case AlphaCaseInsensitiveDesc => 6

        }
      )
}
