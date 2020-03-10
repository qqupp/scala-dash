package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties.LinesMode.{Lines, NoLines}
import io.circe.{Json, JsonObject}


sealed abstract class LinesMode(val value: Boolean) { self =>
  def asJson: Json = Json.fromJsonObject{
    self match {
      case NoLines =>
        JsonObject("lines" -> Json.fromBoolean(value))
      case Lines(w, f, s) =>
        JsonObject(
          "lines" -> Json.fromBoolean(value),
          "linewidth" -> Json.fromInt(w),
          "fill" -> Json.fromInt(f.fill.value),
          "fillGradient" -> Json.fromInt(f.fillGradient.value),
          "steppedLine" -> Json.fromBoolean(s)
        )
    }
  }
}

object  LinesMode {

  case object NoLines extends LinesMode(false)
  final case class Lines(width: Int, fill: FillArea, staircase: Boolean) extends LinesMode(true)

}
