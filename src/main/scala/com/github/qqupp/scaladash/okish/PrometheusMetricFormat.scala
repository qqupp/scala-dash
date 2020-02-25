package com.github.qqupp.scaladash.okish

import io.circe.{Encoder, Json}

sealed trait PrometheusMetricFormat

object PrometheusMetricFormat {

  case object Heatmap extends PrometheusMetricFormat
  case object Table extends PrometheusMetricFormat
  case object TimeSeries extends PrometheusMetricFormat

  implicit val jsonEncoder: Encoder[PrometheusMetricFormat] =
    prometheusMetricFormat =>
      Json.fromString(
        prometheusMetricFormat match {
          case Heatmap => "heatmap"
          case Table => "table"
          case TimeSeries => "time_series"
        }
      )

}
