package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json}

sealed trait YAxis

object YAxis {

  case object Left extends YAxis
  case object Right extends YAxis

  implicit val jsonEncoder: Encoder[YAxis] =
    yAxis =>
      Json.fromInt(
        yAxis match {
         case Left => 1
         case Right => 2
        }
      )

}

