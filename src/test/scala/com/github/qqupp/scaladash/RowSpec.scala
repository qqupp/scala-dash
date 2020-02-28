package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.Metric.GenericMetric
import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.Json
import org.scalacheck.magnolia._
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class RowSpec extends FlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "a Row"


  it should "render json" in {
    val panel1 = Panel("testPanel1").withMetrics(List(metric1, metric2))
    val panel2 = Panel("testPanel2").withMetrics(List(metric3, metric4))

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


  private val metric1 = GenericMetric("targ01", None, false)
  private val metric2 = GenericMetric("targ02", None, false)
  private val metric3 = GenericMetric("targ03", None, false)
  private val metric4 = GenericMetric("targ04", None, false)

}
