package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.okish.PrometheusMetricFormat
import io.circe.Json
import io.circe.literal._
import org.scalatest.{FlatSpec, Matchers}

class PrometheusMetricSpec extends FlatSpec with Matchers {


  behavior of "PrometheusMetric"

  it should "Render json" in {
    val target: String = "someTarget"

    val expected: Json =
      json"""
        {
            "refId": "A",
            "expr": $target
        }"""

    PrometheusMetric(target).build("A") shouldBe expected
  }

  it should "Render complex json" in {
    val expected = json"""{
      "refId": "A",
      "expr": "tar_get",
      "legendFormat": "metric",
      "format": "table",
      "instant": true,
      "interval": 3,
      "intervalFactor": 5,
      "hide": true
    }"""

    PrometheusMetric("tar_get", None, Some("metric"), Some(PrometheusMetricFormat.Table), Some(true), Some(3), Some(5), true).build("A") shouldBe expected

  }
}
