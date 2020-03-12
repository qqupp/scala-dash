package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties.SeriesOverride.Overridable
import io.circe.{Encoder, Json, JsonObject}
import io.circe.literal._
import io.circe.syntax._

final case class SeriesOverride(aliasOrRegex: String, overrides: List[Overridable])

object SeriesOverride {

  sealed abstract class Overridable(val name: String, val value: Json)
  final case class OverrideBars(v: Boolean) extends Overridable("bars", v.asJson)
  final case class OverrideLines(v: Boolean) extends Overridable("lines", v.asJson)
  final case class OverrideLinesFill(v: FillStyle) extends Overridable("fill", v.asJson)
  final case class OverrideLinesWidth(v: LineWidth) extends Overridable("linewidth", v.asJson)
  final case class OverridePointMode(v: NullValueMode) extends Overridable("nullPointMode", v.asJson)
  final case class OverrideStaricaseLine(v: Boolean) extends Overridable("steppedLine", v.asJson)
  final case class OverrideDashes(v: Boolean) extends Overridable("dashes", v.asJson)
  final case class OverrideDashesLength(v: Int) extends Overridable("dashLength", v.asJson)
  final case class OverrideSpaceLength(v: Int) extends Overridable("spaceLength", v.asJson)


  implicit val overridableJsonEncoder: Encoder[Overridable] =
    o => Json.fromJsonObject(JsonObject(o.name -> o.value))

  implicit val seriesOverrideJsonEncoder: Encoder[SeriesOverride] =
    so =>
      so.overrides.foldLeft(
        Json.fromJsonObject(
          JsonObject("alias" -> Json.fromString(so.aliasOrRegex))
        )
      )( (json, overridable) => json.deepMerge(overridable.asJson))

}

