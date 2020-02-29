package com.github.qqupp.scaladash

import io.circe.{Encoder, Json}

sealed trait YAxisFormat

object YAxisFormat {

  case object NoFormat extends YAxisFormat
  case object Short extends YAxisFormat
  case object Bytes extends YAxisFormat
  case object Bits extends YAxisFormat
  case object BitsPerSecond extends YAxisFormat
  case object Seconds extends YAxisFormat
  case object Milliseconds extends YAxisFormat
  case object Microseconds extends YAxisFormat
  case object Nanoseconds extends YAxisFormat
  case object Percent extends YAxisFormat

  implicit val jsonEncoder: Encoder[YAxisFormat] =
      yAxisFormat =>
        Json.fromString(
          yAxisFormat match {
            case NoFormat => "none"
            case Short => "short"
            case Bytes => "bytes"
            case Bits => "bits"
            case BitsPerSecond => "bps"
            case Seconds =>  "s"
            case Milliseconds => "ms"
            case Microseconds => "µs"
            case Nanoseconds => "ns"
            case Percent =>  "percent"
          }
        )

}