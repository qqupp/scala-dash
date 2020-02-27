package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.Json
import io.circe.literal._
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import generators._

class PanelSpec extends FlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "a Panel"

  it should "add a prometheus metric" in {
    val panel =
      Panel("test_panel")
        .withMetric(Metric.prometheusMetric("tar_get"))

    val expected: Json = json"""[{"refId": "A", "expr": "tar_get"}]"""

   panel.build(1) should containKeyValue("targets", expected)
  }

  it should "add xlkjdslkfj" in {
    forAll { (metric1: Metric, metric2: Metric) =>


    }
  }
}
