package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties.SeriesOverride.Overridable
import io.circe.{Encoder, Json, JsonObject}
import io.circe.literal._
import io.circe.syntax._

final case class SeriesOverride(aliasOrRegex: String, overrides: List[Overridable])

object SeriesOverride {

  sealed abstract class Overridable(val name: String, val value: Json)
  final case class Bars(v: Boolean) extends Overridable("bars", v.asJson)
  final case class Lines(v: Boolean) extends Overridable("lines", v.asJson)
  final case class LinesFill(v: FillStyle) extends Overridable("fill", v.asJson)


  implicit val overridableJsonEncoder: Encoder[Overridable] =
    o => Json.fromJsonObject(JsonObject(o.name -> o.value))

  implicit val jsonEncoder: Encoder[SeriesOverride] =
    so =>
      so.overrides.foldLeft(
      Json.fromJsonObject(
        JsonObject("alias" -> Json.fromString(so.aliasOrRegex)))
      )( (json, overridable) => json.deepMerge(overridable.asJson))
}

