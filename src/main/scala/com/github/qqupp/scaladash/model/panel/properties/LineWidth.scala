package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json}

sealed abstract class LineWidth(val value: Int)

object LineWidth {

  case object L0 extends LineWidth(0)
  case object L1 extends LineWidth(1)
  case object L2 extends LineWidth(2)
  case object L3 extends LineWidth(3)
  case object L4 extends LineWidth(4)
  case object L5 extends LineWidth(5)
  case object L6 extends LineWidth(6)
  case object L7 extends LineWidth(7)
  case object L8 extends LineWidth(8)
  case object L9 extends LineWidth(9)
  case object L10 extends LineWidth(10)

  implicit val jsonEncoder: Encoder[LineWidth] =
    l => Json.fromInt(l.value)

}
