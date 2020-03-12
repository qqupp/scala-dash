package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties.SeriesOverride.Overridable
import io.circe.{Encoder, Json, JsonObject}
import io.circe.literal._
import io.circe.syntax._

final case class SeriesOverride(aliasOrRegex: String, overrides: List[Overridable])

object SeriesOverride {

  sealed abstract class Overridable(val name: String, val value: Json)
  final case class OverrideBars(v: BarsMode) extends Overridable("-", v.asJson)
  final case class OverrideLines(v: LinesMode) extends Overridable("-", v.asJson)
  final case class OverridePointMode(v: NullValueMode) extends Overridable("nullPointMode", v.asJson)
  final case class OverrideDashes(v: Boolean) extends Overridable("dashes", v.asJson)
  final case class OverrideDashesLength(v: Int) extends Overridable("dashLength", v.asJson)
  final case class OverrideSpaceLength(v: Int) extends Overridable("spaceLength", v.asJson)
  final case class OverridePoints(v: PointsMode) extends Overridable("-", v.asJson)
  final case class OverrideStackMode(v: StackMode) extends Overridable("-", v.asJson)
  final case class OverrideLegend(v: Boolean) extends Overridable("legend", v.asJson)
  final case class OverrideHideTooltip(v: Boolean) extends Overridable("hideTooltip", v.asJson)
  final case class OverrideColor(v: Color) extends Overridable("color", v.asJson)

  implicit val overridableJsonEncoder: Encoder[Overridable] =
    o =>
      if (o.name == "-")
        o.value
      else
        Json.fromJsonObject(JsonObject(o.name -> o.value))

  implicit val seriesOverrideJsonEncoder: Encoder[SeriesOverride] =
    so =>
      so.overrides.foldLeft(
        Json.fromJsonObject(
          JsonObject("alias" -> Json.fromString(so.aliasOrRegex))
        )
      )( (json, overridable) => json.deepMerge(overridable.asJson))

}

