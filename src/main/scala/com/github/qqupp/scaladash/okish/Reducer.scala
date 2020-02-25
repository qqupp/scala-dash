package com.github.qqupp.scaladash.okish

import io.circe.{Encoder, Json}

sealed trait Reducer

object Reducer {

  case object Average extends Reducer
  case object Count extends Reducer
  case object Last extends Reducer
  case object Median extends Reducer
  case object Max extends Reducer
  case object Min extends Reducer
  case object Sum extends Reducer

  implicit val jsonEncoder: Encoder[Reducer] =
    reducer =>
      Json.fromString(
        reducer match {
          case Average => "avg"
          case Count => "count"
          case Last => "last"
          case Median => "median"
          case Max => "max"
          case Min => "min"
          case Sum => "sum"
        }
      )
}
