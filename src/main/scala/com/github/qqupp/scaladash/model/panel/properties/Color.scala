package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json}

sealed abstract class Color(val value: String) { self =>
  def asJson: Json = Json.fromString(self.value)
}

object Color {

  final case class UnsafeCustom(r: Int, g: Int, b: Int) extends Color(s"rgb($r, $g, $b)")

  case object Green0 extends Color("rgb(115, 191, 105)")
  case object Green1 extends Color("rgb(55, 135, 45)")
  case object Green2 extends Color("rgb(86, 166, 75)")
  case object Green3 extends Color("rgb(150, 217, 141)")
  case object Green4 extends Color("rgb(200, 242, 194)")
  case object Yellow0 extends Color("rgb(250, 222, 42)")
  case object Yellow1 extends Color("rgb(224, 180, 0)")
  case object Yellow2 extends Color("rgb(242, 204, 12)")
  case object Yellow3 extends Color("rgb(255, 238, 82)")
  case object Yellow4 extends Color("rgb(255, 248, 153)")
  case object Red0 extends Color("rgb(242, 73, 92)")
  case object Red1 extends Color("rgb(196, 22, 42)")
  case object Red2 extends Color("rgb(224, 47, 68)")
  case object Red3 extends Color("rgb(255, 115, 131)")
  case object Red4 extends Color("rgb(255, 166, 176)")
  case object Blue0 extends Color("rgb(87, 148, 242)")
  case object Blue1 extends Color("rgb(31, 96, 196)")
  case object Blue2 extends Color("rgb(50, 116, 217)")
  case object Blue3 extends Color("rgb(138, 184, 255)")
  case object Blue4 extends Color("rgb(192, 216, 255)")
  case object Orange0 extends Color("rgb(255, 152, 48)")
  case object Orange1 extends Color("rgb(250, 100, 0)")
  case object Orange2 extends Color("rgb(255, 120, 10)")
  case object Orange3 extends Color("rgb(255, 179, 87)")
  case object Orange4 extends Color("rgb(255, 203, 125)")
  case object Purple0 extends Color("rgb(184, 119, 217)")
  case object Purple1 extends Color("rgb(143, 59, 184)")
  case object Purple2 extends Color("rgb(163, 82, 204)")
  case object Purple3 extends Color("rgb(202, 149, 229)")
  case object Purple4 extends Color("rgb(222, 182, 242)")

  implicit val jsonEncoder: Encoder[Color] =
    c => c.asJson

}
