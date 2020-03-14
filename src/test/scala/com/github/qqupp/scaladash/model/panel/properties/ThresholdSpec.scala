package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties.Color.{Green1, Red0}
import com.github.qqupp.scaladash.model.panel.properties.Threshold._
import io.circe.literal._
import org.scalatest.{FlatSpec, Matchers}
import io.circe.syntax._

class ThresholdSpec extends FlatSpec with Matchers {

  behavior of "a Threshold"

  it should "produce json" in {
    val threshold: Threshold = Threshold(Critical , Gt(-2.983), fill = true, line = true)

    val expected =
      json"""{
               "value": -2.983,
               "colorMode": "critical",
               "op": "gt",
               "fill": true,
               "line": true,
               "yaxis": "left"
              }"""

    threshold.asJson shouldBe expected
  }

  it should "produce json for custom values" in {
    val threshold: Threshold = Threshold(Custom(fill = Red0, line = Green1), Lt(7), fill = true, line = true)

    val expected =
      json"""{
               "value": 7,
               "colorMode": "custom",
               "op": "lt",
               "fill": true,
               "line": true,
               "yaxis": "left",
               "fillColor": "rgb(242, 73, 92)",
               "lineColor": "rgb(55, 135, 45)"
             }"""

    threshold.asJson shouldBe expected

  }


}
