package com.github.qqupp.scaladash.model.query

import io.circe.{Encoder, Json}

sealed trait PrometheusQueryFormat

object PrometheusQueryFormat {

  case object Heatmap extends PrometheusQueryFormat
  case object Table extends PrometheusQueryFormat
  case object TimeSeries extends PrometheusQueryFormat

  implicit val jsonEncoder: Encoder[PrometheusQueryFormat] =
    pqf =>
      Json.fromString(
        pqf match {
          case Heatmap => "heatmap"
          case Table => "table"
          case TimeSeries => "time_series"
        }
      )

}
