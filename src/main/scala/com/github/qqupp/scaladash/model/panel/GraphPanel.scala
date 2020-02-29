package com.github.qqupp.scaladash.model.panel

import com.github.qqupp.scaladash.model.alert.Alert
import com.github.qqupp.scaladash.model.metric.Metric
import com.github.qqupp.scaladash.model.panel.FillStyle.Unfilled
import com.github.qqupp.scaladash.model.panel.StackStyle.Unstacked
import com.github.qqupp.scaladash.model.panel.YAxisFormat.NoFormat
import com.github.qqupp.scaladash.model.panel.YAxisMinimum.Auto
import com.github.qqupp.scaladash.model.source.Datasource
import com.github.qqupp.scaladash.utils.JsonUtils._
import io.circe.Json
import io.circe.literal._

final case class GraphPanel(title: String,
                            metrics: List[Metric],
                            yAxisFormat: YAxisFormat,
                            filled: FillStyle,
                            stacked: StackStyle,
                            minimum: YAxisMinimum,
                            aliasColors: List[AliasColor],
                            span: Option[Int],
                            maximum: String,
                            datasource: Option[Datasource],
                            lines: Boolean,
                            bars: Boolean,
                            points: Boolean,
                            availableRefIds: List[String],
                            seriesOverrides: List[Json],
                            alert: Option[Alert]
                      ) extends Panel {

  def withAlert(alert: Alert): GraphPanel =
    this.copy(alert = Some(alert))

  def withMetric(metric: Metric): GraphPanel = {
    val newMetrics = metrics ++ List(metric)

    val newSeriesOverrides =
      metric.rightYAxisMetricName.fold(seriesOverrides){ name =>
        seriesOverrides ++ List(json"""{
                                         "alias": $name,
                                         "yaxis": 2
                                      }""")
      }

    this.copy(metrics = newMetrics, seriesOverrides = newSeriesOverrides)
  }

  def withMetrics(metrics: List[Metric]): GraphPanel =
    metrics.foldLeft(this)((acc, m) => acc.withMetric(m))

  def build(panelId: Int, span: Int = 12): Json =
    json"""
       {
         "title": $title,
         "error": false,
         "span": ${this.span.getOrElse(span)},
         "editable": true,
         "type": "graph",
         "id": $panelId,
         "datasource": $datasource,
         "renderer": "flot",
         "x-axis": true,
         "y-axis": true,
         "y_formats": ${List(yAxisFormat, yAxisFormat)},
         "grid": {
                 "leftMax": null,
                 "rightMax": null,
                 "leftMin": $minimum,
                 "rightMin": null,
                 "threshold1": null,
                 "threshold2": null,
                 "threshold1Color": "rgba(216, 200, 27, 0.27)",
                 "threshold2Color": "rgba(234, 112, 112, 0.22)"
                 },
         "lines": $lines,
         "fill": $filled,
         "linewidth": 1,
         "points": $points,
         "pointradius": 5,
         "bars": $bars,
         "stack": $stacked,
         "percentage": false,
         "legend": {
            "show": true,
            "values": false,
            "min": false,
            "max": false,
            "current": false,
            "total": false,
            "avg": false
         },
         "nullPointMode": "connected",
         "steppedLine": false,
         "tooltip": {
           "value_type": "cumulative",
           "shared": false
          },
         "targets": ${ (availableRefIds zip metrics).map{ case (id, metric) => metric.build(id) } },
         "aliasColors": ${aliasColors},
         "seriesOverrides": $seriesOverrides,
         "links": []
  }"""
    .addOpt("alert", alert.map(_.build((availableRefIds zip metrics))))

}

object GraphPanel {

  def apply(title: String): GraphPanel =
    GraphPanel(
      title = title,
      metrics = List.empty,
      yAxisFormat = NoFormat,
      filled = Unfilled,
      stacked = Unstacked,
      minimum = Auto,
      aliasColors = List.empty,
      span = None,
      maximum = "",
      datasource = None,
      lines = true,
      bars = false,
      points = false,
      availableRefIds = (65 to 91).map(_.toChar.toString).toList,
      seriesOverrides = List.empty,
      alert = None
    )
}
