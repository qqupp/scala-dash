package com.github.qqupp.scaladash.model.alert

import io.circe.{Encoder, Json}

sealed trait NoDataState

object NoDataState {

  case object Alerting extends NoDataState
  case object KeepState extends NoDataState
  case object NoData extends NoDataState
  case object Ok extends NoDataState

  implicit val jsonEncoder: Encoder[NoDataState] =
    state =>
      Json.fromString(
        state match {
          case Alerting => "alerting"
          case KeepState => "keep_state"
          case NoData => "no_data"
          case Ok => "ok"
        }
      )

}
