package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties.AxisValue.Auto
import com.github.qqupp.scaladash.model.panel.properties.XAxis.{HistogramXAxis, SeriesXAxis, TimeXAxis}
import io.circe.{Json, JsonObject}
import io.circe.syntax._

sealed abstract class XAxis(val show: Boolean, val mode: String) { self =>
  def asJson: Json = {

   val baseAttr =
     List(
       "name" -> Json.Null,
       "values" -> List.empty[Int].asJson,
       "buckets" -> Json.Null,
       "mode" -> Json.fromString(self.mode),
       "show" -> Json.fromBoolean(self.show)
    )

   val attr =
    self match {
      case TimeXAxis(_) =>
        List()
      case SeriesXAxis(_, seriesKind) =>
        List("values" -> List(seriesKind.value).asJson)
      case HistogramXAxis(display, buckets, min, max) =>
        List(
          "buckets" -> buckets.asJson,
          "min" -> min.asJson,
          "max" -> max.asJson
        )

    }
    JsonObject(baseAttr: _*).deepMerge(JsonObject(attr: _*)).asJson
  }
}

object XAxis {

  val default: XAxis = TimeXAxis(display = true)

  final case class TimeXAxis(display: Boolean) extends XAxis(display, "time")
  final case class SeriesXAxis(display: Boolean, seriesKind: SeriesKind) extends XAxis(display, "series")
  final case class HistogramXAxis(display: Boolean, numberOfbuckets: AxisValue = Auto, xMin: AxisValue = Auto, xMax: AxisValue = Auto) extends XAxis(display, "histogram")



  sealed abstract class SeriesKind(val value: String)
  case object Avg extends SeriesKind("avg")
  case object Min extends SeriesKind("min")
  case object Max extends SeriesKind("max")
  case object Total extends SeriesKind("total")
  case object Countt extends SeriesKind("count")
  case object Current extends SeriesKind("current")
  case object Histogram extends SeriesKind("count")
}

