package com.github.qqupp.scaladash.model.panel

import io.circe.{Json, JsonObject}

sealed abstract class BarsMode(val value: Boolean) { self =>
  def asJson: Json = Json.fromJsonObject(
    JsonObject("bars" -> Json.fromBoolean(value))
  )
}
object BarsMode {

  case object Bars extends BarsMode(true)
  case object NoBars extends BarsMode(false)

}
