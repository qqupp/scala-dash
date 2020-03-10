package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json}

sealed abstract class YAxisScale(val logBase: Int)

object YAxisScale {

  case object Linear extends YAxisScale(1)
  case object LogBase2 extends YAxisScale(2)
  case object LogBase10 extends YAxisScale(10)
  case object LogBase32 extends YAxisScale(32)
  case object LogBase1024 extends YAxisScale(1024)

  implicit val jsonEncoder: Encoder[YAxisScale] =
    s => Json.fromInt(s.logBase)
}
