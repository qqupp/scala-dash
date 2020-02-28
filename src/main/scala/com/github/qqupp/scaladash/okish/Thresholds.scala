package com.github.qqupp.scaladash.okish

import io.circe.{Encoder, Json}

final case class Thresholds(lower: Int, mid: Int, upper: Int)

object Thresholds {

  implicit val jsonEncoder: Encoder[Thresholds] =
    thresholds =>
      Json.fromString(
        s"${thresholds.lower}, ${thresholds.mid}, ${thresholds.upper}"
      )

}
