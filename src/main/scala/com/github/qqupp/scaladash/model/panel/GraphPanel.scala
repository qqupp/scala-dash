package com.github.qqupp.scaladash.model.panel

import com.github.qqupp.scaladash.model.alert.Alert
import com.github.qqupp.scaladash.model.metric.Metric
import com.github.qqupp.scaladash.model.panel.properties.YAxisUnit.Misc.NoFormat
import com.github.qqupp.scaladash.model.panel.properties.AxisValue.Auto
import com.github.qqupp.scaladash.model.panel.properties._
import com.github.qqupp.scaladash.model.source.Datasource
import com.github.qqupp.scaladash.utils.JsonUtils._
import io.circe.Json
import io.circe.literal._
import io.circe.syntax._

final case class GraphPanel(title: String,
                            metrics: List[Metric],
                            visualization: GraphPanelVisualization,
                            axes: Axes,
                            minimum: AxisValue,
                            span: Option[Int],
                            legend: Legend,
                            maximum: String,
                            datasource: Option[Datasource],
                            alert: Option[Alert]
                      ) extends Panel {

  def withDrawModes(drawModes: DrawModes): GraphPanel = {
    val newVisualization = this.visualization.copy(drawModes = drawModes)
    copy(visualization = newVisualization)
  }

  private val aliasColors: List[AliasColor] = List()
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
         "legend": $legend,
         "targets": $targetsJ ,
         "aliasColors": ${aliasColors},
         "seriesOverrides": $seriesOverrides,
         "links": []
  }""".deepMerge(visualization.asJson)
      .deepMerge(axes.asJson)
      .addOpt("alert", alert.map(_.build((availableRefIds zip metrics))))

  }
}

object GraphPanel {

  def apply(title: String): GraphPanel =
    GraphPanel(
      title = title,
      metrics = List.empty,
      visualization = GraphPanelVisualization.default,
      legend = Legend.default,
      axes = Axes.default,
      minimum = Auto,
      span = None,
      maximum = "",
      datasource = None,
      alert = None
    )
}
