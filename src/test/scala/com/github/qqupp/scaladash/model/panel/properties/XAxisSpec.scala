package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties.AxisValue.{Auto, Value}
import com.github.qqupp.scaladash.model.panel.properties.XAxis.HistogramXAxis
import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.Json
import org.scalatest.{FlatSpec, Matchers}

class XAxisSpec extends FlatSpec with Matchers {

  behavior of "a XAxis"

  it should "produce json" in {
    val axisJson = XAxis.default.asJson

    axisJson should containKeyValue("show", true)
    axisJson should containKeyValue("mode", "time")
    axisJson should containKeyValue("name", Json.Null)
    axisJson should containKeyValue("values", Json.arr())
    axisJson should containKeyValue("buckets", Json.Null)
  }

  it should "produce json for specific axis" in {
    val axis = HistogramXAxis(display = false, numberOfbuckets = Value(10), xMin = Value(7), xMax = Value(100))
    val axisJson =axis.asJson

    axisJson should containKeyValue("show", false)
    axisJson should containKeyValue("mode", "histogram")
    axisJson should containKeyValue("name", Json.Null)
    axisJson should containKeyValue("values", Json.arr())
    axisJson should containKeyValue("buckets", 10)
    axisJson should containKeyValue("min", 7)
    axisJson should containKeyValue("max", 100)

    axis.copy(numberOfbuckets = Auto).asJson should containKeyValue("buckets", Json.Null)

  }

}
