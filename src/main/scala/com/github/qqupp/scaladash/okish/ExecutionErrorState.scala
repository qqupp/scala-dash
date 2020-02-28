package com.github.qqupp.scaladash.okish

import io.circe.{Encoder, Json}

sealed  trait ExecutionErrorState

object ExecutionErrorState {

  case object Alerting extends ExecutionErrorState
  case object KeepState extends ExecutionErrorState

  implicit val jsonEncoder: Encoder[ExecutionErrorState] =
    state =>
      Json.fromString(
        state match {
          case Alerting => "alerting"
          case KeepState => "keep_state"
        }
      )

}
