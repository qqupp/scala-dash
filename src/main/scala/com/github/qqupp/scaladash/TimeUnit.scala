package com.github.qqupp.scaladash

sealed abstract class TimeUnit(val value: Int, val unit: String)

object TimeUnit {

  final case class Seconds(v: Int) extends TimeUnit(v, "s")
  final case class Minutes(v: Int) extends TimeUnit(v, "m")
  final case class Hours(v: Int) extends TimeUnit(v, "h")
  final case class Days(v: Int) extends TimeUnit(v, "d")
  final case class Weeks(v: Int) extends TimeUnit(v, "w")

}
