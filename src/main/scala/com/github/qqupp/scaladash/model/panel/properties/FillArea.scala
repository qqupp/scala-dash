package com.github.qqupp.scaladash.model.panel.properties

sealed abstract class FillArea(val fill: FillStyle, val fillGradient: FillGradient)

case object FillArea {

  case object NoFill extends FillArea(FillStyle.Unfilled, FillGradient.Slow)
  final case class Fill(override val fill: FillStyle, override val fillGradient: FillGradient) extends FillArea(fill, fillGradient)

}
