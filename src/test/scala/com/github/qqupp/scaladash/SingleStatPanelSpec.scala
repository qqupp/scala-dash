package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.model.metric.Metric.GenericMetric
import com.github.qqupp.scaladash.model.panel.SingleStatPanel
import com.github.qqupp.scaladash.model.panel.properties.Thresholds
import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.Json
import io.circe.literal._
import org.scalatest.{FlatSpec, Matchers}


class SingleStatPanelSpec extends FlatSpec with Matchers {

  behavior of "a SingleStatPanel"

  it should "render json" in {
    val prefix = "some prefix"
    val postfix = "some postfix"
    val thresholdLower = 111
    val thresholdMid = 222
    val thresholdUpper = 333

    val expectedColorsJson =
      json"""[
              "rgba(225, 40, 40, 0.59)",
              "rgba(245, 150, 40, 0.73)",
              "rgba(71, 212, 59, 0.4)"
             ]"""

    val expectedSparklineJson =
      json"""{
              "show": true,
              "full": false,
              "lineColor": "rgb(71, 248, 35)",
              "fillColor": "rgba(130, 189, 31, 0.18)"
             }"""

    val panel =
      SingleStatPanel(title)
        .copy(prefix = prefix)
        .copy(postfix = postfix)
        .copy(thresholds =Thresholds(thresholdLower, thresholdMid, thresholdUpper))
        .copy(invertThresholdOrder=false)
        .withMetric(metric1)
        .withMetric(metric2)

    val panelJson = panel.build(panelId, span)

    panelJson should containKeyValue("title", title)
    panelJson should containKeyValue("error", false)
    panelJson should containKeyValue("span", span)
    panelJson should containKeyValue("editable", true)
    panelJson should containKeyValue("type", "singlestat")
    panelJson should containKeyValue("id", panelId)
    panelJson should containKeyValue("links", Json.arr())
    panelJson should containKeyValue("maxDataPoints", 100)
    panelJson should containKeyValue("interval", Json.Null)
    panelJson should containKeyValue("targets", List(metric1.build("A"), metric2.build("B")))
    panelJson should containKeyValue("cacheTimeout", Json.Null)
    panelJson should containKeyValue("format", "none")
    panelJson should containKeyValue("prefix", prefix)
    panelJson should containKeyValue("postfix", postfix)
    panelJson should containKeyValue("valueName", "current")
    panelJson should containKeyValue("prefixFontSize", "100%")
    panelJson should containKeyValue("valueFontSize", "120%")
    panelJson should containKeyValue("postfixFontSize", "100%")
    panelJson should containKeyValue("thresholds", s"$thresholdLower, $thresholdMid, $thresholdUpper")
    panelJson should containKeyValue("colorBackground", true)
    panelJson should containKeyValue("colorValue", false)
    panelJson should containKeyValue("colors", expectedColorsJson)
    panelJson should containKeyValue("sparkline", expectedSparklineJson)

  }

  it should "render with inverted threshold" in {
    val panel = SingleStatPanel(title).copy(invertThresholdOrder = true)

    val expectedJson =
      json""" [
               "rgba(71, 212, 59, 0.4)",
               "rgba(245, 150, 40, 0.73)",
               "rgba(225, 40, 40, 0.59)"
              ]"""

    panel.build(panelId, span) should containKeyValue("colors", expectedJson)

  }

  private val metric1 = GenericMetric("targ01", None, false)
  private val metric2 = GenericMetric("targ02", None, false)

  private val panelId: Int = 10
  private val title: String = "Test Panel"
  private val span: Int = 22
}
