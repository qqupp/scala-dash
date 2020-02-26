package com.github.qqupp.scaladash

import io.circe.{Encoder, Json}

final case class AliasColor()

object AliasColor {

  implicit val jsonEncoder: Encoder[AliasColor] = _ => Json.Null

}

