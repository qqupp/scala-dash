package com.github.qqupp.scaladash

import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import org.scalacheck.magnolia._

class ConditionSpec extends FlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "Condition"

  it should "render json" in {
    forAll { condition: Condition =>

    }
  }
}
