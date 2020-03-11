package com.github.qqupp.scaladash.model.panel.properties

import org.scalatest.{FlatSpec, Matchers}
import io.circe.syntax._
import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.Json

class YAxisSpec extends FlatSpec with Matchers {

  behavior of "a YAxis"

  it should "produce json" in {
    val axisJson = YAxis().asJson

    axisJson should containKeyValue("show", true)
    axisJson should containKeyValue("min", Json.Null)
    axisJson should containKeyValue("max", Json.Null)
    axisJson should containKeyValue("decimals", Json.Null)
    axisJson should containKeyValue("logBase", 1)
    axisJson should containKeyValue("label", "")
  }

  it should "produce json for specific axis" in {
    val axisJson =
      YAxis(
        show = false,
        YAxisUnit.Computation.TFLOPS,
        YAxisScale.LogBase32,
        AxisValue.Value(-3.1234),
        AxisValue.Value(33194.48),
        "testLabel",
        Some(3)
      ).asJson

    axisJson should containKeyValue("show", false)
    axisJson should containKeyValue("format", "tflops")
    axisJson should containKeyValue("min", -3.1234)
    axisJson should containKeyValue("max", 33194.48)
    axisJson should containKeyValue("decimals", 3)
    axisJson should containKeyValue("logBase", 32)
    axisJson should containKeyValue("label", "testLabel")
  }

}
