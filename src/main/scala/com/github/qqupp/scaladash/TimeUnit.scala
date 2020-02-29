package com.github.qqupp.scaladash

sealed abstract class TimeUnit(val value: String)

object TimeUnit {

  case object Seconds extends TimeUnit( "s" )
  case object Minutes extends TimeUnit( "m" )
  case object Hours extends TimeUnit( "h" )
  case object Days extends TimeUnit( "d" )
  case object Weeks extends TimeUnit( "w" )

}
