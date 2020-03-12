package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json, JsonObject}
import io.circe.syntax._

final case class GraphPanelVisualization(drawModes: DrawModes,
                                         hooverTooltip: HooverTooltip,
                                         stackModes: StackMode,
                                         nullValuesMode: NullValueMode,
                                         seriesOverrides: List[SeriesOverride]
                                        )

object GraphPanelVisualization {

  val default: GraphPanelVisualization =
    GraphPanelVisualization(
      DrawModes.default,
      HooverTooltip.default,
      StackMode.Unstacked,
      NullValueMode.Connected,
      List.empty
    )

  implicit val jsonEncode: Encoder[GraphPanelVisualization] =
    v =>
      Json
        .fromJsonObject(
          JsonObject("seriesOverrides" -> v.seriesOverrides.asJson)
        )
        .deepMerge(v.drawModes.asJson)
        .deepMerge(v.stackModes.asJson)
        .deepMerge(v.hooverTooltip.asJson)
        .deepMerge(JsonObject("nullPointMode" -> v.nullValuesMode.asJson).asJson)

}