package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.okish.FillStyle.Unfilled
import com.github.qqupp.scaladash.okish.StackStyle.Unstacked
import com.github.qqupp.scaladash.okish.YAxisFormat.NoFormat
import com.github.qqupp.scaladash.okish.YAxisMinimum.Auto
import com.github.qqupp.scaladash.okish.{FillStyle, StackStyle, YAxisFormat, YAxisMinimum}
import io.circe.{Json, JsonObject}
import io.circe.literal._
import com.github.qqupp.scaladash.utils.JsonUtils._

/*

class Panel:
    def __init__(self, title, y_axis_format=YAxisFormat.NoFormat, filled=FillStyle.Unfilled,
                 stacked=StackStyle.Unstacked, minimum=YAxisMinimum.Auto, alias_colors=None,
                 span=None, maximum=None, datasource=None, lines=True, bars=False, points=False):
        self.y_axis_format = y_axis_format
        self.title = title
        self.metrics = []
        self.alert = None
        self.filled = filled
        self.stacked = stacked
        self.minimum = minimum
        self.maximum = maximum
        self.series_overrides = []
        self.alias_colors = alias_colors
        self.span = span
        self.datasource = datasource
        self.lines = lines
        self.bars = bars
        self.points = points

        self.available_ref_ids = list(map(chr, range(65, 91)))

    def with_metric(self, metric):
        self.metrics.append(metric.build(self.available_ref_ids.pop(0)))
        if hasattr(metric, "right_y_axis_metric_name") and metric.right_y_axis_metric_name is not None:
            self.series_overrides.append({
                "alias": metric.right_y_axis_metric_name,
                "yaxis": 2
            })
        return self

    def with_metrics(self, metrics):
        for metric in metrics:
            self.with_metric(metric)
        return self

    def with_alert(self, alert):
        self.alert = alert
        return self

    def build(self, panel_id, span=12):
        panel = {
            "title": self.title,
            "error": False,
            "span": self.span or span,
            "editable": True,
            "type": "graph",
            "id": panel_id,
            "datasource": self.datasource,
            "renderer": "flot",
            "x-axis": True,
            "y-axis": True,
            "y_formats": [
                self.y_axis_format,
                self.y_axis_format
            ],
            "grid": {
                "leftMax": self.maximum,
                "rightMax": None,
                "leftMin": self.minimum,
                "rightMin": None,
                "threshold1": None,
                "threshold2": None,
                "threshold1Color": "rgba(216, 200, 27, 0.27)",
                "threshold2Color": "rgba(234, 112, 112, 0.22)"
            },
            "lines": self.lines,
            "fill": self.filled,
            "linewidth": 1,
            "points": self.points,
            "pointradius": 5,
            "bars": self.bars,
            "stack": self.stacked,
            "percentage": False,
            "legend": {
                "show": True,
                "values": False,
                "min": False,
                "max": False,
                "current": False,
                "total": False,
                "avg": False
            },
            "nullPointMode": "connected",
            "steppedLine": False,
            "tooltip": {
                "value_type": "cumulative",
                "shared": False
            },
            "targets": self.metrics,
            "aliasColors": ({} if self.alias_colors is None else self.alias_colors),
            "seriesOverrides": self.series_overrides,
            "links": []
        }
        if self.alert:
            panel["alert"] = self.alert.build(self.metrics)
        return panel


 */
final case class Panel(title: String,
                       metrics: List[PrometheusMetric],
                       metricsJson: List[Json],
                       yAxisFormat: YAxisFormat,
                       filled: FillStyle,
                       stacked: StackStyle,
                       minimum: YAxisMinimum,
                       aliasColors: List[AliasColor],
                       span: Option[Int],
                       maximum: WTF,
                       datasource: Option[Datasource],
                       lines: Boolean,
                       bars: Boolean,
                       points: Boolean,
                       availableRefIds: List[Char],
                       seriesOverrides: List[Json],
                       alert: Option[Alert]
                      ) {

  def withMetric(metric: PrometheusMetric): Panel = {
    val (h :: t) = availableRefIds
    val newMetrics = metrics ++ List(metric)
    val newMetricsJson = metricsJson ++ List(metric.build(h.toString))

    val newSeriesOverrides =
      metric.rightYAxisMetricName.fold(seriesOverrides){ name =>
        seriesOverrides ++ List(json"""{
                                         "alias": $name,
                                         "yaxis": 2
                                      }""")
      }

    this.copy(metrics = newMetrics, metricsJson = newMetricsJson, seriesOverrides = newSeriesOverrides)
  }

  def withMetrics(metrics: List[PrometheusMetric]): Panel =
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
                 "leftMax": $maximum,
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
         "targets": $metricsJson,
         "aliasColors": ${aliasColors},
         "seriesOverrides": $seriesOverrides,
         "links": []
  }"""

}

object Panel {

  def apply(title: String): Panel =
    Panel(
      title = title,
      metrics = List.empty,
      metricsJson = List.empty,
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
      availableRefIds = (65 to 91).map(_.toChar).toList,
      seriesOverrides = List.empty,
      alert = None
    )
}
