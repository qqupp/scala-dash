package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.model.{Dashboard, DashboardEnvelope}
import org.scalatest.{FlatSpec, Matchers}
import com.github.qqupp.scaladash.utils.JsonTestUtils._

class DashboardEnvelopeSpec extends FlatSpec with Matchers {

  "Dashboard envelope" should "wrap a dashboard and convert it to json in a api firendly format" in {
      val json =
        DashboardEnvelope.jsonFor(
          Dashboard("test title")
        )

    json should containKeyValue("dashboard", Dashboard("test title").build)
    json should containKeyValue("overwrite", true)

  }
}
