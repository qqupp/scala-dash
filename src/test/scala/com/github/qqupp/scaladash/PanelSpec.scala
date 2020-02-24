package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.JsonTestUtils._
import io.circe.Json
import io.circe.literal._
import org.scalatest.{FlatSpec, Matchers}

class PanelSpec extends FlatSpec with Matchers {

  behavior of "a Panel"

  it should "add a prometheus metric" in {
    val panel =
      Panel("test_panel")
        .with_metric(PrometheusMetric("tar_get"))

    val expected: Json = json"""[{"refId": "A", "expr": "tar_get"}]"""

   panel.build(1) should containKeyValue("targets", expected)
  }



}
