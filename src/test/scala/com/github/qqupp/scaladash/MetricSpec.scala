package com.github.qqupp.scaladash

import io.circe._
import io.circe.literal._
import org.scalatest.{FlatSpec, Matchers}

class MetricSpec extends FlatSpec with Matchers {

  behavior of "Metric"

  it should "Render json" in {
    val target: String = "someTarget"

    val expected: Json =
      json"""
        {
            "refId": "A",
            "target": $target
        }"""

    Metric(target).build("A") shouldBe expected
  }
}
