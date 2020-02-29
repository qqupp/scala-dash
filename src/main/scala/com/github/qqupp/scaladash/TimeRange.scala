package com.github.qqupp.scaladash

import io.circe.Encoder
import io.circe.syntax._
import io.circe.generic.auto._

sealed trait TimeRange

object TimeRange {

  final case class RelativeLast(value: TimeUnit) extends TimeRange
  final case class RelativeFromTo(from: TimeUnit, to: TimeUnit) extends TimeRange

  implicit val jsonEncoder: Encoder[TimeRange] = {
    case RelativeLast(v) =>
      TimeRangeJson(s"now-${v.value}${v.unit}", "now").asJson
    case RelativeFromTo(f, t) =>
      TimeRangeJson(s"now-${f.value}${f.unit}", s"now-${t.value}${t.unit}").asJson
  }

  private case class TimeRangeJson(from: String, to: String)
}
