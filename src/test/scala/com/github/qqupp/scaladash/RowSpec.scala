package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.Metric.GenericMetric
import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.Json
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class RowSpec extends FlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "a Row"

  it should "render json" in {

    val aRow =
        Row()
          .copy(collapse = true)
          .withPanels(panel1, panel2)

    val rowJson: Json = aRow.build(1)

    rowJson should containKeyValue("title", "Row 1")
    rowJson should containKeyValue("height", "250px")
    rowJson should containKeyValue("editable", true)
    rowJson should containKeyValue("collapse", true)
    rowJson should containKeyValue("showTitle", false)
    rowJson should containKeyValue("panels", List(panel1.build(11,6), panel2.build(12, 6)))
  }

  it should "render showing a title" in {
    val aRow =
      Row()
        .copy(showTitle = true)
        .copy(height = "100px")
        .withPanels(panel1, panel2)

    val rowJson: Json = aRow.build(1)

    rowJson should containKeyValue("title", "Row 1")
    rowJson should containKeyValue("height", "100px")
    rowJson should containKeyValue("editable", true)
    rowJson should containKeyValue("collapse", false)
    rowJson should containKeyValue("showTitle", true)
    rowJson should containKeyValue("panels", List(panel1.build(11,6), panel2.build(12, 6)))
  }

  it should "respect specific panel span" in {
    forAll { (span1: Int, span2: Int) =>
      val aRow =
        Row()
          .withPanel(
            panel1.copy(span = Some(span1))
          )
          .withPanel(
            panel2.copy(span = Some(span2))
          )

      val rowJson: Json = aRow.build(1)

      rowJson should containKeyValue("panels",
        List(panel1.build(11, span1), panel2.build(12, span2)))
    }
  }

  it should "render a title" in {
    val aRow =
      Row()
      .copy(title = Some("a test title"))
      .withPanel(panel1)

    val rowJson: Json = aRow.build(1)
    rowJson should containKeyValue("title", "a test title")
  }

  private val metric1 = GenericMetric("targ01", None, false)
  private val metric2 = GenericMetric("targ02", None, false)
  private val metric3 = GenericMetric("targ03", None, false)
  private val metric4 = GenericMetric("targ04", None, false)

  private val panel1 = SingleStatPanel("testPanel1").withMetrics(List(metric1, metric2))
  private val panel2 = GraphPanel("testPanel2").withMetrics(List(metric3, metric4))

}
