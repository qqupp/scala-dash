package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.okish.PrometheusMetricFormat
import io.circe.Json
import io.circe.literal._
import com.github.qqupp.scaladash.utils.JsonUtils._

sealed trait Metric {
  def rightYAxisMetricName: Option[String]
  def build(ref_id: String): Json
}

object  Metric {

  final case class PrometheusMetric(expr: String,
                                    rightYAxisMetricName: Option[String],
                                    legendFormat: Option[String],
                                    format: Option[PrometheusMetricFormat],
                                    instant: Option[Boolean],
                                    interval: Option[Int],
                                    intervalFactor: Option[Int],
                                    hide: Boolean) extends Metric {

    def build(ref_id: String): Json = {

      val metricJson =
        json"""{
        "refId": $ref_id,
        "expr": $expr
      }"""


      metricJson
        .addOpt("legendFormat", legendFormat)
        .addOpt("format", format)
        .addOpt("instant", instant)
        .addOpt("interval", interval)
        .addOpt("intervalFactor", intervalFactor)
        .addOpt("hide", if (hide) Some(true) else None)
    }

  }


  final case class GenericMetric(target: String, rightYAxisMetricName: Option[String], hide: Boolean) extends Metric {

    def build(ref_id: String): Json = {

      val metricJson =
        json"""{
        "refId": $ref_id,
        "target": $target
      }"""

      metricJson
        .addOpt("hide", if (hide) Some(true) else None)

    }

  }

  def prometheusMetric(expression: String): Metric = PrometheusMetric(expression, None, None, None, None, None, None, false)

  def genericMetric(target: String): Metric = GenericMetric(target, None, false)

}