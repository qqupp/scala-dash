package com.github.qqupp.scaladash.model.time

import io.circe.{Encoder, Json}

sealed abstract class Duration(val value: Int, val unit: String) extends Product with Serializable {
  def show: String = s"${value}${unit}"
}

object Duration {

  final case class Seconds(v: Int) extends Duration(v, "s")
  final case class Minutes(v: Int) extends Duration(v, "m")
  final case class Hours(v: Int) extends Duration(v, "h")
  final case class Days(v: Int) extends Duration(v, "d")
  final case class Weeks(v: Int) extends Duration(v, "w")

  implicit val jsonEncoder: Encoder[Duration] = d => Json.fromString(d.show)

}
