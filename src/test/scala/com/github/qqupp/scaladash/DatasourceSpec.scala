package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.okish.Datasource
import com.github.qqupp.scaladash.okish.Datasource.Graphite
import io.circe.literal._
import io.circe.syntax._
import org.scalatest.{FlatSpec, Matchers}
class DatasourceSpec extends FlatSpec with Matchers {

  behavior of "Datasource"

  it should "Render json with defaults" in {
    val datasource = Datasource.prometheus("aName", "https://a.server/path")
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

  it should "Render json" in {
    val datasource: Datasource = Graphite("anotherName", "https://another.server/", "abc", true)
    val expected =
      json"""{
            "name": "anotherName",
            "type": "graphite",
            "url": "https://another.server/",
            "access": "abc",
            "isDefault": true
        }"""

    datasource.asJson shouldBe expected
  }

}
