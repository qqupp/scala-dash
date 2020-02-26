package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.okish.DatasourceType.Prometheus
import org.scalatest.{FlatSpec, Matchers}
import io.circe.literal._
import io.circe.syntax._
class DatasourceSpec extends FlatSpec with Matchers {


  behavior of "Datasource"

  it should "Render json" in {
    val datasource = Datasource("aName", Prometheus, "https://a.server/path")
    val expected =
      json"""{
      "name": "aName",
      "type": "prometheus",
      "url": "https://a.server/path",
      "access": "proxy",
      "isDefault": false
    }"""

    datasource.asJson shouldBe expected
  }

}
