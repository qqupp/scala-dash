package com.github.qqupp.scaladash.model.panel.properties

import org.scalatest.{FlatSpec, Matchers}
import io.circe.syntax._
import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.Json

class YAxisSpec extends FlatSpec with Matchers {

  behavior of "a YAxis"

  it should "produce json" in {

    val axysJson = YAxis.default.asJson

    axysJson should containKeyValue("show", true)
    axysJson should containKeyValue("min", Json.Null)
    axysJson should containKeyValue("max", Json.Null)
    axysJson should containKeyValue("decimals", Json.Null)
    axysJson should containKeyValue("logBase", 1)
    axysJson should containKeyValue("label", "")
  }

}
