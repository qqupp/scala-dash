package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.model.query.{Query, PrometheusQueryFormat}
import com.github.qqupp.scaladash.model.query.Query.PrometheusQuery
import io.circe._
import io.circe.literal._
import org.scalatest.{FlatSpec, Matchers}

class QuerySpec extends FlatSpec with Matchers {

  behavior of "Query"

  it should "Render json for generic query" in {
    val target: String = "someTarget"

    val expected: Json =
      json"""
        {
            "refId": "A",
            "target": $target
        }"""

    Query.genericQuery(target).build("A") shouldBe expected
  }


  it should "Render json  prometheus query" in {
    val target: String = "someTarget"

    val expected: Json =
      json"""
        {
            "refId": "A",
            "expr": $target
        }"""

    Query.prometheusQuery(target).build("A") shouldBe expected
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

    PrometheusQuery("tar_get", Some("metric"), Some(PrometheusQueryFormat.Table), Some(true), Some(3), Some(5), true).build("A") shouldBe expected

  }
}
