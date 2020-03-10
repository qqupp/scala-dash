package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json, JsonObject}
import io.circe.syntax._

sealed abstract class NullValueMode(val nullPointMode: String)

object NullValueMode {

  case object NullAsNull extends NullValueMode("null")
  case object NullAsZero extends NullValueMode("null as zero")
  case object Connected extends NullValueMode("connected")

 implicit val jsonEncoder: Encoder[NullValueMode] =
   nvm => JsonObject("nullPointMode" -> Json.fromString(nvm.nullPointMode)).asJson

}
