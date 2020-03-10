package com.github.qqupp.scaladash.model.panel

sealed abstract class FillArea(val fill: FillStyle, val fillGradient: FillGradientStyle)

case object FillArea {

  case object NoFill extends FillArea(FillStyle.Unfilled, FillGradientStyle.Slow)

  final case class Fill(override val fill: FillStyle, override val fillGradient: FillGradientStyle) extends FillArea(fill, fillGradient)

}
