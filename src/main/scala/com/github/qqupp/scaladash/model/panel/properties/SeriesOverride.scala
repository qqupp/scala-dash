package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json, JsonObject}

sealed abstract class SeriesOverride()

object SeriesOverride {

  case object Test extends SeriesOverride

  implicit val jsonEncoder: Encoder[SeriesOverride] =
    so => Json.fromBoolean(true)

}
