package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties
import com.github.qqupp.scaladash.model.panel.properties.BarsMode.NoBars
import com.github.qqupp.scaladash.model.panel.properties.FillArea.NoFill
import com.github.qqupp.scaladash.model.panel.properties.LinesMode.Lines
import com.github.qqupp.scaladash.model.panel.properties.PointsMode.NoPoints
import io.circe.Encoder

final case class DrawModes(bars: BarsMode, lines: LinesMode, points: PointsMode)

object DrawModes {

  val default: DrawModes = properties.DrawModes(
    bars = NoBars,
    lines = Lines(1, NoFill, staircase = false),
    points = NoPoints
  )

  implicit val jsonEncoder: Encoder[DrawModes] =
    dm =>
      dm.bars.asJson
        .deepMerge(dm.lines.asJson)
        .deepMerge(dm.points.asJson)

}
