package com.github.qqupp.scaladash

import io.circe.{Encoder, Json}

sealed trait PrometheusMetricFormat

object PrometheusMetricFormat {

  case object Heatmap extends PrometheusMetricFormat
  case object Table extends PrometheusMetricFormat
  case object TimeSeries extends PrometheusMetricFormat

  implicit val jsonEncoder: Encoder[PrometheusMetricFormat] = {
    case Heatmap => Json.fromString("heatmap")
    case Table => Json.fromString("table")
    case TimeSeries => Json.fromString("time_series")
  }

}
