package com.github.qqupp.scaladash.model.panel.properties

import org.scalatest.{FlatSpec, Matchers}
import com.github.qqupp.scaladash.generators.dataArbitraries._
import com.github.qqupp.scaladash.model.panel.properties.BarsMode.Bars
import com.github.qqupp.scaladash.model.panel.properties.FillArea.Fill
import com.github.qqupp.scaladash.model.panel.properties.FillGradient.{Medium, Slow}
import com.github.qqupp.scaladash.model.panel.properties.FillStyle.HalfFilled
import com.github.qqupp.scaladash.model.panel.properties.HooverTooltip.{AllSeries, Decreasing}
import com.github.qqupp.scaladash.model.panel.properties.LineWidth.L3
import com.github.qqupp.scaladash.model.panel.properties.LinesMode.Lines
import com.github.qqupp.scaladash.model.panel.properties.PointsMode.Points
import com.github.qqupp.scaladash.model.panel.properties.StackMode.{Cumulative, StackedPercent}
import com.github.qqupp.scaladash.model.template.Variable.{CustomVariable, QueryVariable}
import com.github.qqupp.scaladash.model.{Dashboard, Row}
import com.github.qqupp.scaladash.model.template.{VariableRefresh, VariableSort}
import com.github.qqupp.scaladash.model.time.Duration._
import com.github.qqupp.scaladash.model.time.TimeRange
import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.Json
import io.circe.literal._
import io.circe.optics.JsonPath._
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import io.circe.syntax._


class GraphPanelVisualizationSpec extends FlatSpec with Matchers {

  behavior of "a GraphPanelVisualization"

  it should "produce json with Lines" in {
    val customLines = Lines(L3, Fill(HalfFilled, Medium), staircase = true)
    val visualization =
      GraphPanelVisualization.default
        .copy(drawModes = DrawModes.default.copy(lines = customLines))

    visualization.asJson should containKeyValue("lines", true)
    visualization.asJson should containKeyValue("linewidth", 3)
    visualization.asJson should containKeyValue("steppedLine", true)
    visualization.asJson should containKeyValue("fill", 5)
    visualization.asJson should containKeyValue("fillGradient", 5)
  }


  it should "produce json with Bars" in {
    val customBars = Bars
    val visualization =
      GraphPanelVisualization.default
        .copy(drawModes = DrawModes.default.copy(bars = customBars))

    visualization.asJson should containKeyValue("bars", true)
  }

  it should "produce json with Points" in {
    val customPoints = Points(7)
    val visualization =
      GraphPanelVisualization.default
        .copy(drawModes = DrawModes.default.copy(points = customPoints))

    visualization.asJson should containKeyValue("points", true)
    visualization.asJson should containKeyValue("pointradius", 7)
  }

  it should "produce json with HooverTooltip" in {
    val customToolTip = HooverTooltip(mode = AllSeries, sortOrder = Decreasing)
    val visualization =
      GraphPanelVisualization.default
        .copy(hooverTooltip = customToolTip)

    visualization.asJson should containValueInPath(root.tooltip.shared, true)
    visualization.asJson should containValueInPath(root.tooltip.sort, 2)
  }

  it should "produce json with Stacked" in {
    val customStack = StackedPercent(Cumulative)
    val visualization =
      GraphPanelVisualization.default
        .copy(stackModes = customStack)

    visualization.asJson should containValueInPath(root.stack, true)
    visualization.asJson should containValueInPath(root.percentage, true)
    visualization.asJson should containValueInPath(root.tooltip.value_type, "cumulative")
  }

  it should "produce json with NullValuesMode" in {
    val customNullValue = NullValueMode.Connected
    val visualization =
      GraphPanelVisualization.default
        .copy(nullValuesMode = customNullValue)

    visualization.asJson should containKeyValue("nullPointMode", "connected")
  }

}
