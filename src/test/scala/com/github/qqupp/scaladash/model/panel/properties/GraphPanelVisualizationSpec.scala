package com.github.qqupp.scaladash.model.panel.properties

import org.scalatest.{FlatSpec, Matchers}
import com.github.qqupp.scaladash.generators.dataArbitraries._
import com.github.qqupp.scaladash.model.panel.properties.FillArea.Fill
import com.github.qqupp.scaladash.model.panel.properties.FillGradient.{Medium, Slow}
import com.github.qqupp.scaladash.model.panel.properties.FillStyle.HalfFilled
import com.github.qqupp.scaladash.model.panel.properties.LinesMode.Lines
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
    val customLines = Lines(3, Fill(HalfFilled, Medium), staircase = true)
    val visualization =
      GraphPanelVisualization.default
        .copy(drawModes = DrawModes.default.copy(lines = customLines))

    visualization.asJson should containKeyValue("lines", true)
    visualization.asJson should containKeyValue("linewidth", 3)
    visualization.asJson should containKeyValue("steppedLine", true)
    visualization.asJson should containKeyValue("fill", 5)
    visualization.asJson should containKeyValue("fillGradient", 5)
  }
}
