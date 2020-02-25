package com.github.qqupp.scaladash

import io.circe.{Encoder, Json}

sealed trait StackStyle
object StackStyle {

  case object Stacked extends StackStyle
  case object Unstacked extends StackStyle

  implicit val jsonEncoder: Encoder[StackStyle] = {
    case Stacked => Json.fromBoolean(true)
    case Unstacked => Json.fromBoolean(false)
  }
}
