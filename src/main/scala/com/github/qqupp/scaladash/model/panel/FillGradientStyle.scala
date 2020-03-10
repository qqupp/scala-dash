package com.github.qqupp.scaladash.model.panel

sealed abstract class FillGradientStyle(val value: Int)

object FillGradientStyle {

  case object Fast extends FillGradientStyle(10)
  case object Medium extends FillGradientStyle(5)
  case object Slow extends FillGradientStyle(1)
  case object Solid extends FillGradientStyle(0)

}
