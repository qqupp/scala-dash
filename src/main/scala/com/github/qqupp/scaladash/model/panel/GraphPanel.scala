package com.github.qqupp.scaladash.model.panel

import com.github.qqupp.scaladash.model.alert.Alert
import com.github.qqupp.scaladash.model.metric.Metric
import com.github.qqupp.scaladash.model.panel.FillStyle.Unfilled
import com.github.qqupp.scaladash.model.panel.StackStyle.Unstacked
import com.github.qqupp.scaladash.model.panel.YAxisFormat.Misc.NoFormat
import com.github.qqupp.scaladash.model.panel.YAxisMinimum.Auto
import com.github.qqupp.scaladash.model.source.Datasource
import com.github.qqupp.scaladash.utils.JsonUtils._
import io.circe.Json
import io.circe.literal._
import io.circe.syntax._

final case class GraphPanel(title: String,
                            metrics: List[Metric],
                            drawModes: DrawModes,
                            yAxisFormat: YAxisFormat,
                            filled: FillStyle,
                            stacked: StackStyle,
                            minimum: YAxisMinimum,
                            aliasColors: List[AliasColor],
                            span: Option[Int],
                            maximum: String,
                            datasource: Option[Datasource],
                            alert: Option[Alert]
                      ) extends Panel {

  private val availableRefIds = (65 to 91).map(_.toChar.toString).toList
  private val seriesOverrides: List[Json] = List()


  def withAlert(alert: Alert): GraphPanel =
    this.copy(alert = Some(alert))

  def withMetric(metric: Metric): GraphPanel = {
    val newMetrics = metrics ++ List(metric)

    this.copy(metrics = newMetrics)
  }

  def withMetrics(metrics: List[Metric]): GraphPanel =
    metrics.foldLeft(this)((acc, m) => acc.withMetric(m))

  def build(panelId: Int, span: Int = 12): Json = {
    val targetsJ: Json = (availableRefIds zip metrics).map{ case (id, metric) => metric.build(id) }.asJson

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
         "tooltip": {
           "value_type": "cumulative",
           "shared": false
          },
         "targets": $targetsJ ,
         "aliasColors": ${aliasColors},
         "seriesOverrides": $seriesOverrides,
         "links": []
  }""".deepMerge(drawModes.asJson)
      .addOpt("alert", alert.map(_.build((availableRefIds zip metrics))))

  }
}

object GraphPanel {

  def apply(title: String): GraphPanel =
    GraphPanel(
      title = title,
      metrics = List.empty,
      drawModes = DrawModes(),
      yAxisFormat = NoFormat,
      filled = Unfilled,
      stacked = Unstacked,
      minimum = Auto,
      aliasColors = List.empty,
      span = None,
      maximum = "",
      datasource = None,
      alert = None
    )
}
