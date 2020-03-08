package com.github.qqupp.scaladash.e2e

import com.github.qqupp.scaladash.model.{Dashboard, DashboardEnvelope}
import org.scalatest.{FlatSpec, Matchers}
import sttp.client._
import sttp.model.StatusCode

class DashboardsJsonE2ETest extends FlatSpec with Matchers {

  "A dashboard" should "be json compatible with grafana" in {

    val dashboard = Dashboard("DashboardsJsonE2ETest")

    val json = DashboardEnvelope.jsonFor(dashboard)

    val request =
      basicRequest.post(uri"http://localhost:3000/api/dashboards/db")
        .header("Content-Type", "application/json")
        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
      .body(json.toString)


    implicit val backend = HttpURLConnectionBackend()
    val response = request.send()

    response.code shouldBe StatusCode.Ok

  }

}
