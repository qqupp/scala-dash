package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties.Threshold.{ThresholdColor, ThresholdOp}
import io.circe.Encoder
import io.circe.literal._
import io.circe.syntax._

final case class Threshold(color: ThresholdColor, op: ThresholdOp, fill: Boolean, line: Boolean)

object Threshold {

  sealed trait ThresholdColor
  case object Critical extends ThresholdColor
  case object Warning extends ThresholdColor
  case object Ok extends ThresholdColor
  final case class  Custom(fill: Color, line: Color) extends ThresholdColor

  sealed abstract class ThresholdOp(val op: String, val value: Double)
  final case class Gt(v: Double) extends ThresholdOp("gt", v)
  final case class Lt(v: Double) extends ThresholdOp("lt", v)


  implicit val thresholdColorEncoder: Encoder[ThresholdColor] =
  {
    case Critical => json"""{ "colorMode": "critical" }"""
    case Warning => json"""{ "colorMode": "warning" }"""
    case Ok => json"""{ "colorMode": "ok" }"""
    case Custom(c1, c2) =>
      json"""{
                 "colorMode": "custom",
                 "fillColor": $c1,
                 "lineColor": $c2
              }"""
  }

  implicit val thresholdOpEncoder: Encoder[ThresholdOp] =
    top =>
      json"""{
                "value": ${top.value},
                "op": ${top.op}
             }
          """

  implicit val thresholdEncoder: Encoder[Threshold] =
    t => t.color.asJson.deepMerge(t.op.asJson).deepMerge(
      json"""{
             "fill": ${t.fill},
              "line": ${t.line},
              "yaxis": "left"
            }""")

}
