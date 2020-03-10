package com.github.qqupp.scaladash.model.panel

import com.github.qqupp.scaladash.model.panel.BarsMode.NoBars
import com.github.qqupp.scaladash.model.panel.FillArea.NoFill
import com.github.qqupp.scaladash.model.panel.LinesMode.Lines
import com.github.qqupp.scaladash.model.panel.PointsMode.NoPoints
import io.circe.Encoder

final case class DrawModes(bars: BarsMode, lines: LinesMode, points: PointsMode)

object DrawModes {

  def apply(): DrawModes = DrawModes(
    bars = NoBars,
    lines = Lines(1, NoFill, false),
    points = NoPoints)

  implicit val jsonEncoder: Encoder[DrawModes] =
    dm =>
      dm.bars.asJson.deepMerge(dm.lines.asJson).deepMerge(dm.points.asJson)

}
