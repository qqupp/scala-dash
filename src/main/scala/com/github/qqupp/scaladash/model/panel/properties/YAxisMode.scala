package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json}

sealed trait YAxisMode

object YAxisMode {

  case object Left extends YAxisMode
  case object Right extends YAxisMode

  implicit val jsonEncoder: Encoder[YAxisMode] =
    yAxis =>
      Json.fromInt(
        yAxis match {
         case Left => 1
         case Right => 2
        }
      )

}

