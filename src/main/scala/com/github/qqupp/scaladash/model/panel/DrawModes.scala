package com.github.qqupp.scaladash.model.panel

import com.github.qqupp.scaladash.model.panel.DrawModes.{BarsMode, LinesMode, PointsMode}
import io.circe.{Encoder, Json, JsonObject}

final case class DrawModes(bars: BarsMode, lines: LinesMode, points: PointsMode)

object DrawModes {

  def apply(): DrawModes = DrawModes(
    bars = NoBars,
    lines = Lines(1, NoFill, false),
    points = NoPoints)

  sealed abstract class BarsMode(val value: Boolean) { self =>
    def json: Json = Json.fromJsonObject(
      JsonObject("bars" -> Json.fromBoolean(value))
    )
  }
  case object Bars extends BarsMode(true)
  case object NoBars extends BarsMode(false)


  sealed abstract class LinesMode(val value: Boolean) { self =>
    def json: Json = Json.fromJsonObject{
      self match {
        case NoLines =>
          JsonObject("lines" -> Json.fromBoolean(value))
        case Lines(w, f, s) =>
          JsonObject(
            "lines" -> Json.fromBoolean(value),
            "linewidth" -> Json.fromInt(w),
            "fill" -> Json.fromInt(f.fill.value),
            "fillGradient" -> Json.fromInt(f.fillGradient.value),
            "steppedLine" -> Json.fromBoolean(s)
          )
      }
    }
  }
  case object NoLines extends LinesMode(false)
  final case class Lines(width: Int, fill: FillArea, staircase: Boolean) extends LinesMode(true)

  sealed abstract class FillArea(val fill: FillStyle, val fillGradient: FillGradientStyle)
  case object NoFill extends FillArea(FillStyle.Unfilled, FillGradientStyle.Slow)
  final case class Fill(override val fill: FillStyle, override val fillGradient: FillGradientStyle) extends FillArea(fill, fillGradient)

  sealed abstract class PointsMode(val value: Boolean){ self =>
    def json: Json = Json.fromJsonObject(
      JsonObject("points" -> Json.fromBoolean(value))
    )
  }
  case object NoPoints extends PointsMode(false)
  final case class Points(radius: Double) extends PointsMode(true)

  implicit val jsonEncoder: Encoder[DrawModes] =
    dm =>
      dm.bars.json.deepMerge(dm.lines.json).deepMerge(dm.points.json)

}
