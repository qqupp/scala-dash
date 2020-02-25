package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.okish.PrometheusMetricFormat
import io.circe.Json
import io.circe.literal._
import com.github.qqupp.scaladash.utils.JsonUtils._
/*

class PrometheusMetric:
    def __init__(self, expr, legend_format=None, format=None, instant=None, interval=None, interval_factor=None, hide=False):
        self.expr = expr
        self.legend_format = legend_format
        self.format = format
        self.instant = instant
        self.interval = interval
        self.interval_factor = interval_factor
        self.hide = hide

    def build(self, ref_id):
        json = {
            "refId": ref_id,
            "expr": self.expr
        }
        if self.legend_format:
            json["legendFormat"] = self.legend_format
        if self.format:
            json["format"] = self.format
        if self.instant:
            json["instant"] = True
        if self.interval:
            json["interval"] = self.interval
        if self.interval_factor:
            json["intervalFactor"] = self.interval_factor
        if self.hide:
            json["hide"] = True
        return json

 */

final case class PrometheusMetric(expr: String,
                                  legendFormat: Option[String],
                                  format: Option[PrometheusMetricFormat],
                                  instant: Option[Boolean],
                                  interval:Option[Int],
                                  intervalFactor: Option[Int],
                                  hide: Boolean) {

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

object PrometheusMetric {
  def apply(expression: String): PrometheusMetric = PrometheusMetric(expression, None, None, None, None, None, false)
}