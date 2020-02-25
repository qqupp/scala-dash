package com.github.qqupp.scaladash.okish

import io.circe.{Encoder, Json}

sealed trait StackStyle

object StackStyle {

  case object Stacked extends StackStyle
  case object Unstacked extends StackStyle

  implicit val jsonEncoder: Encoder[StackStyle] =
    stackStyle =>
      Json.fromBoolean(
        stackStyle match {
          case Stacked => true
          case Unstacked => false
        }
      )

}
