package com.github.qqupp.scaladash.model.time

import io.circe.Encoder
import io.circe.generic.auto._
import io.circe.syntax._

sealed trait TimeRange

object TimeRange {

  final case class RelativeLast(value: Duration) extends TimeRange
  final case class RelativeFromTo(from: Duration, to: Duration) extends TimeRange

  implicit val jsonEncoder: Encoder[TimeRange] = {
    case RelativeLast(v) =>
      TimeRangeJson(s"now-${v.show}", "now").asJson
    case RelativeFromTo(f, t) =>
      TimeRangeJson(s"now-${f.show}", s"now-${t.show}").asJson
  }

  private case class TimeRangeJson(from: String, to: String)
}
