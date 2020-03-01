package com.github.qqupp.scaladash.model.template

import io.circe.{Encoder, Json}

sealed trait VariableRefresh

object VariableRefresh {

  case object Never extends VariableRefresh
  case object OnDashboardLoad extends VariableRefresh
  case object OnTimeRangeChange extends VariableRefresh

  implicit val jsonEncoder: Encoder[VariableRefresh] =
    variableRefresh =>
      Json.fromInt(
        variableRefresh match {
          case Never => 0
          case OnDashboardLoad => 1
          case OnTimeRangeChange => 2
        }
      )

}
