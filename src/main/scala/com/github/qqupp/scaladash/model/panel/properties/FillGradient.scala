package com.github.qqupp.scaladash.model.panel.properties

sealed abstract class FillGradient(val value: Int)

object FillGradient {

  case object Fast extends FillGradient(10)
  case object Medium extends FillGradient(5)
  case object Slow extends FillGradient(1)
  case object Solid extends FillGradient(0)

}
