package com.github.qqupp.scaladash.model.panel

import com.github.qqupp.scaladash.model.panel.DrawModes.{BarsMode, LinesMode, PointsMode}

final case class DrawModes(bars: BarsMode, lines: LinesMode, points: PointsMode)

object DrawModes {

  def apply(): DrawModes = DrawModes(
    bars = NoBars,
    lines = Lines(1, NoFill, false),
    points = NoPoints)

  sealed trait BarsMode
  case object Bars extends BarsMode
  case object NoBars extends BarsMode

  sealed trait LinesMode
  case object NoLines extends LinesMode
  final case class Lines(width: Int, fill: FillLine, staircase: Boolean) extends LinesMode

  sealed abstract class FillLine(val fill: Int, val fillGradient: Int)
  case object NoFill extends FillLine(0, 0)
  final case class Fill(override val fill: Int, override val fillGradient: Int) extends FillLine(fill, fillGradient)

  sealed trait PointsMode
  case object NoPoints extends PointsMode
  final case class Points(radius: Double) extends PointsMode

}
