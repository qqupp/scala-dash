package com.github.qqupp.scaladash.model.panel.properties

import org.scalatest.{FlatSpec, Matchers}
import io.circe.syntax._
import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.Json

class YAxisSpec extends FlatSpec with Matchers {

  behavior of "a YAxis"

  it should "produce json" in {
    val axysJson = YAxis().asJson

    axysJson should containKeyValue("show", true)
    axysJson should containKeyValue("min", Json.Null)
    axysJson should containKeyValue("max", Json.Null)
    axysJson should containKeyValue("decimals", Json.Null)
    axysJson should containKeyValue("logBase", 1)
    axysJson should containKeyValue("label", "")
  }

  it should "produce json for specific axis" in {
    val axysJson =
      YAxis(
        show = false,
        YAxisUnit.Computation.TFLOPS,
        YAxisScale.LogBase32,
        YAxisValue.Value(-3.1234),
        YAxisValue.Value(33194.48),
        "testLabel",
        Some(3)
      ).asJson

    axysJson should containKeyValue("show", false)
    axysJson should containKeyValue("min", -3.1234)
    axysJson should containKeyValue("max", 33194.48)
    axysJson should containKeyValue("decimals", 3)
    axysJson should containKeyValue("logBase", 32)
    axysJson should containKeyValue("label", "testLabel")
  }

}
