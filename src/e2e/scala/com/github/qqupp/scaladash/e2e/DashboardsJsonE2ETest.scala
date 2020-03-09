package com.github.qqupp.scaladash.e2e

import com.github.qqupp.scaladash.e2e.generators.MetricGen
import com.github.qqupp.scaladash.model.alert.Alert
import com.github.qqupp.scaladash.model.metric.Metric
import com.github.qqupp.scaladash.model.panel._
import com.github.qqupp.scaladash.model.source.Datasource
import com.github.qqupp.scaladash.model.{Dashboard, DashboardEnvelope, Row}
import io.circe.Json
import org.scalacheck.Arbitrary
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import sttp.client._
import sttp.model.StatusCode
import org.scalacheck.magnolia._


class DashboardsJsonE2ETest extends FlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {


  "A dashboard" should "be json compatible with grafana" in {

    val dashboard = Dashboard("DashboardsJsonE2ETest")

    val json = DashboardEnvelope.jsonFor(dashboard)

    val response = postJsonToLocalGrafana(json)

    response.code shouldBe StatusCode.Ok

  }

  implicit lazy val mgen = Arbitrary(MetricGen.allValues)
  "A GraphPanel with Metric" should "be json compatible with grafana" in {
    forAll{ metric: List[Metric]=>
      val dashboard = Dashboard("GraphPanelMetricRNDTest").withRow(Row().withPanel(GraphPanel("TestPanel").withMetrics(metric)))
      val json = DashboardEnvelope.jsonFor(dashboard)

      val response = postJsonToLocalGrafana(json)

      response.code shouldBe StatusCode.Ok
    }
  }


  "A GraphPanel" should "be json compatible with grafana" in {
    final case class TestGraphPanel(title: String,
                                    metrics: List[Metric],
                                    yAxisFormat: YAxisFormat,
                                    filled: FillStyle,
                                    stacked: StackStyle,
                                    minimum: YAxisMinimum,
                                    span: Option[Int],
                                    maximum: String,
                                    lines: Boolean,
                                    bars: Boolean,
                                    points: Boolean
    )


    forAll{ panel: TestGraphPanel =>
      val graphPanel =
        GraphPanel(panel.title)
          .copy(
            metrics = panel.metrics,
            yAxisFormat = panel.yAxisFormat,
            filled = panel.filled,
            stacked = panel.stacked,
            minimum = panel.minimum,
            span = panel.span,
            lines = panel.lines,
            bars = panel.bars,
            points = panel.points
          )
      val dashboard = Dashboard("GraphPanelRNDTest").withRow(Row().withPanel(graphPanel))
      val json = DashboardEnvelope.jsonFor(dashboard)

      val response = postJsonToLocalGrafana(json)

      response.code shouldBe StatusCode.Ok
    }
  }



  private def postJsonToLocalGrafana(json: Json): Identity[Response[Either[String, String]]] = {
    val request =
      basicRequest.post(uri"http://localhost:3000/api/dashboards/db")
        .header("Content-Type", "application/json")
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
        .body(json.toString)

    implicit val backend = HttpURLConnectionBackend()
    request.send()
  }
}
