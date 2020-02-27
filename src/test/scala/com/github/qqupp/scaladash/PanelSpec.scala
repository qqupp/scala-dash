package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.okish.{FillStyle, StackStyle, YAxisFormat, YAxisMinimum}
import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.Json
import io.circe.literal._
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import org.scalacheck.Prop._
import org.scalacheck.magnolia._

class PanelSpec extends FlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "a Panel"

  it should "add a prometheus metric" in {
    val panel =
      Panel("test_panel")
        .withMetric(Metric.prometheusMetric("tar_get"))

    val expected: Json = json"""[{"refId": "A", "expr": "tar_get"}]"""

   panel.build(1) should containKeyValue("targets", expected)
  }

  it should "render json" in {
    forAll { (metric1: Metric, metric2: Metric, yAxis: YAxisFormat, filled: FillStyle, stacked: StackStyle, minimum: YAxisMinimum) =>

      val panelId: Int = 10
      val title: String = "Test Panel"

      val expected = json"""{
        "title": $title,
        "error": false,
        "span": 20,
        "editable": true,
        "type": "graph",
        "id": $panelId,
        "datasource": null,
        "renderer": "flot",
        "x-axis": true,
        "y-axis": true,
        "y_formats": [
          $yAxis,
          $yAxis
        ],
        "grid": {
          "leftMax": null,
          "rightMax": null,
          "leftMin": $minimum,
          "rightMin": null,
          "threshold1": null,
          "threshold2": null,
          "threshold1Color": "rgba(216, 200, 27, 0.27)",
          "threshold2Color": "rgba(234, 112, 112, 0.22)"
        },
        "lines": true,
        "fill": "filled",
        "linewidth": 1,
        "points": false,
        "pointradius": 5,
        "bars": false,
        "stack": $stacked,
        "percentage": false,
        "legend": {
          "show": true,
          "values": false,
          "min": false,
          "max": false,
          "current": false,
          "total": false,
          "avg": false
        },
        "nullPointMode": "connected",
        "steppedLine": false,
        "tooltip": {
          "value_type": "cumulative",
          "shared": false
        },
        "targets": [${metric1.build("A")}, ${metric2.build("B")}],
        "aliasColors": {},
        "seriesOverrides": [{
          "alias": ${metric1.rightYAxisMetricName},
          "yaxis": 2
        }, {
          "alias": ${metric2.rightYAxisMetricName},
          "yaxis": 2
        }],
        "links": []
      }"""

      val panel =
        Panel(title)
          .withMetrics(List(metric1, metric2))
          .copy(yAxisFormat = yAxis)
          .copy(filled = filled)
          .copy(stacked = stacked)
          .copy(minimum = minimum)

      panel.build(panelId) shouldBe expected
    }
  }
}
