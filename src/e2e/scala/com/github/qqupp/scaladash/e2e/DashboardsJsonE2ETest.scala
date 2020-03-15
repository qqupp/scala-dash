package com.github.qqupp.scaladash.e2e

import java.io.File

import com.github.qqupp.scaladash.model.alert.Alert
import com.github.qqupp.scaladash.model.query.Query
import com.github.qqupp.scaladash.model.panel._
import com.github.qqupp.scaladash.model.{Dashboard, DashboardEnvelope, Row}
import io.circe.Json
import org.scalacheck.magnolia._
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import sttp.client._
import sttp.model.StatusCode


class DashboardsJsonE2ETest extends FlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {


  "A dashboard" should "be json compatible with grafana" in {

    val dashboard = Dashboard("DashboardsJsonE2ETest")

    val json = DashboardEnvelope.jsonFor(dashboard)

    val response = postJsonToLocalGrafana(json)

    response.code shouldBe StatusCode.Ok

  }

  "A GraphPanel with Query" should "be json compatible with grafana" in {
    forAll{ queries: List[Query]=>
      val dashboard = Dashboard("GraphPanelMetricRNDTest").withRow(Row().withPanel(GraphPanel("TestPanel").withQueries(queries)))
      val json = DashboardEnvelope.jsonFor(dashboard)

      val response = postJsonToLocalGrafana(json)

      response.code shouldBe StatusCode.Ok
    }
  }

  "A GraphPanel" should "be json compatible with grafana" in {
    forAll{ panel: GraphPanel =>
      val dashboard = Dashboard("GraphPanelRNDTest").withRow(Row().withPanel(removeAlertsFrom(panel)))
      val json = DashboardEnvelope.jsonFor(dashboard)

      saveToFile(json.toString())
      val response = postJsonToLocalGrafana(json)

      response.code shouldBe StatusCode.Ok
    }
  }

  private def postJsonToLocalGrafana(json: Json): Identity[Response[Either[String, String]]] = {
    val request =
      basicRequest.post(uri"http://localhost:3006/api/dashboards/db")
        .header("Content-Type", "application/json")
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
        .body(json.toString)

    implicit val backend = HttpURLConnectionBackend()
    request.send()
  }

  import java.io.PrintWriter
  private def saveToFile(s: String) = {
    new PrintWriter(new File("/tmp/dashboard.json")) { write(s); close }
  }

  private def removeAlertsFrom(panel: GraphPanel): Panel = panel.copy(alert = None)

}
