package com.github.qqupp.scaladash.model.time

import io.circe.literal._
import io.circe.syntax._
import org.scalatest.{FlatSpec, Matchers}

class TimePickerSpec extends FlatSpec with Matchers {

  "timepicker" should "produce json" in {
      val expected =
        json"""{
                 "collapse": false,
                 "enable": true,
                 "notice": false,
                 "now": true,
                 "refresh_intervals": [
                 "5s",
                 "10s",
                 "30s",
                 "1m",
                 "5m",
                 "15m",
                 "30m",
                 "1h",
                 "2h",
                 "1d"
                 ],
                 "status": "Stable",
                 "time_options": [
                 "5m",
                 "15m",
                 "1h",
                 "6h",
                 "12h",
                 "24h",
                 "2d",
                 "7d",
                 "30d"
                 ],
                 "type": "timepicker"
               }"""

    val tp = TimePicker.default

    tp.asJson shouldBe expected
  }


}
