package com.github.qqupp.scaladash.model.panel

import io.circe.{Encoder, Json}

sealed class YAxisFormat(val value: String)

object YAxisFormat {

  case object NoFormat extends YAxisFormat( "none")
  case object Short extends YAxisFormat( "short")
  case object Bytes extends YAxisFormat( "bytes")
  case object Bits extends YAxisFormat( "bits")
  case object BitsPerSecond extends YAxisFormat( "bps")
  case object Seconds extends YAxisFormat(  "s")
  case object Milliseconds extends YAxisFormat( "ms")
  case object Microseconds extends YAxisFormat( "µs")
  case object Nanoseconds extends YAxisFormat( "ns")
  case object Percent extends YAxisFormat(  "percent")
  case object RequestsPerSecond extends YAxisFormat( "reqps")

  implicit val jsonEncoder: Encoder[YAxisFormat] =
      yAxisFormat =>
        Json.fromString(yAxisFormat.value)

}