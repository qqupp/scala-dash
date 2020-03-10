package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, JsonObject}
import io.circe.syntax._

final case class Visualization(drawModes: DrawModes,
                               hooverTooltip: HooverTooltip,
                               stackModes: StackMode,
                               nullValuesMode: NullValueMode)

object Visualization {

  val default: Visualization =
    Visualization(
      DrawModes.default,
      HooverTooltip.default,
      StackMode.Unstacked,
      NullValueMode.NullAsNull
    )

  implicit val jsonEncode: Encoder[Visualization] =
    v =>
      v.drawModes.asJson
        .deepMerge(v.stackModes.asJson)
}