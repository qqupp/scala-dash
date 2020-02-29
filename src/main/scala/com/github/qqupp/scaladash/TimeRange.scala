package com.github.qqupp.scaladash

import io.circe.Encoder
import io.circe.syntax._
import io.circe.generic.auto._

sealed trait TimeRange

object TimeRange {

  final case class RelativeLast(value: Int, unit: TimeUnit) extends TimeRange
  final case class RelativeFromTo(fromValue: Int, fromUnit: TimeUnit, toValue: Int, toUnit: TimeUnit) extends TimeRange

  implicit val jsonEncoder: Encoder[TimeRange] = {
    case RelativeLast(v, u) =>
      TimeRangeJson(s"now-${v}${u.value}", "now").asJson
    case RelativeFromTo(fv, fu, tv, tu) =>
      TimeRangeJson(s"now-${fv}${fu.value}", s"now-${tv}${tu.value}").asJson
  }

  private case class TimeRangeJson(from: String, to: String)
}
