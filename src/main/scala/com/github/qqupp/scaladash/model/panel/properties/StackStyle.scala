package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json}

sealed abstract class StackStyle(val value: Boolean)

object StackStyle {

  case object Stacked extends StackStyle(true)
  case object Unstacked extends StackStyle(false)

  implicit val jsonEncoder: Encoder[StackStyle] =
    stackStyle =>
      Json.fromBoolean(stackStyle.value)

}
