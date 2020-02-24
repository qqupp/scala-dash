package com.github.qqupp.scaladash

import io.circe.Json
import io.circe.literal._
import com.github.qqupp.scaladash.utils.JsonUtils._
/*
class Metric:
    def __init__(self, target, right_y_axis_metric_name=None, hide=False):
        self.target = target
        self.right_y_axis_metric_name = right_y_axis_metric_name
        self.hide = hide

    def build(self, ref_id):
        json = {
            "refId": ref_id,
            "target": self.target
        }
        if self.hide:
            json["hide"] = True
        return json


 */
final case class Metric(target: String, right_y_axis_metric_name: Option[String], hide: Boolean) {

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

object Metric {
  def apply(target: String): Metric = Metric(target, None, false)
}
