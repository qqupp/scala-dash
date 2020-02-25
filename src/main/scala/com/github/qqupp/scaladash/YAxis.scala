package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.YAxisFormat.{Bits, BitsPerSecond, Bytes, Microseconds, Milliseconds, Nanoseconds, NoFormat, Percent, Seconds, Short}
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

