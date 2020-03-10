package com.github.qqupp.scaladash.model.panel

sealed abstract class FillStyle(val value: Int)

object FillStyle {

  case object Filled extends FillStyle(10)
  case object HalfFilled extends FillStyle(5)
  case object Unfilled extends FillStyle(0)

}
