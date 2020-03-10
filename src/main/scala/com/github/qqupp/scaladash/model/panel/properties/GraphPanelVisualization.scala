package com.github.qqupp.scaladash.model.panel.properties

import io.circe.Encoder
import io.circe.syntax._

final case class GraphPanelVisualization(drawModes: DrawModes,
                                         hooverTooltip: HooverTooltip,
                                         stackModes: StackMode,
                                         nullValuesMode: NullValueMode)

object GraphPanelVisualization {

  val default: GraphPanelVisualization =
    GraphPanelVisualization(
      DrawModes.default,
      HooverTooltip.default,
      StackMode.Unstacked,
      NullValueMode.Connected
    )

  implicit val jsonEncode: Encoder[GraphPanelVisualization] =
    v =>
      v.drawModes.asJson
        .deepMerge(v.stackModes.asJson)
        .deepMerge(v.hooverTooltip.asJson)
        .deepMerge(v.nullValuesMode.asJson)

}