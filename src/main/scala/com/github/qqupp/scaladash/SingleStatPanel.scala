package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.okish.Thresholds
import io.circe.Json
import io.circe.literal._

/*

class SingleStatPanel:
    def __init__(self, title, prefix="", postfix="", thresholds=Thresholds(0,50,200), invert_threshold_order=False):
        self.title = title
        self.prefix = prefix
        self.postfix = postfix
        self.thresholds = thresholds
        self.invert_threshold_order = invert_threshold_order
        self.metrics = []

        self.available_ref_ids = list(map(chr, range(65, 91)))

    def with_metric(self, metric):
        self.metrics.append(metric.build(self.available_ref_ids.pop(0)))
        return self

    def with_metrics(self, metrics):
        for metric in metrics:
            self.with_metric(metric)
        return self

    def build(self, panel_id, span=12):
        colors = ["rgba(225, 40, 40, 0.59)", "rgba(245, 150, 40, 0.73)", "rgba(71, 212, 59, 0.4)"]
        if self.invert_threshold_order:
            colors.reverse()

        return {
            "title": self.title,
            "error": False,
            "span": span,
            "editable": True,
            "type": "singlestat",
            "id": panel_id,
            "links": [],
            "maxDataPoints": 100,
            "interval": None,
            "targets": self.metrics,
            "cacheTimeout": None,
            "format": "none",
            "prefix": self.prefix,
            "postfix": self.postfix,
            "valueName": "current",
            "prefixFontSize": "100%",
            "valueFontSize": "120%",
            "postfixFontSize": "100%",
            "thresholds": self.thresholds.toCsv(),
            "colorBackground": True,
            "colorValue": False,
            "colors": colors,
            "sparkline": {
                "show": True,
                "full": False,
                "lineColor": "rgb(71, 248, 35)",
                "fillColor": "rgba(130, 189, 31, 0.18)"
            }
        }


 */
final case class SingleStatPanel(title: String,
                                 prefix: String,
                                 postfix: String,
                                 thresholds: Thresholds,
                                 invertThresholdOrder: Boolean,
                                 metrics: List[Metric],
                                 availableRefIds: List[String]
                                ) {

  def withMetric(metric: Metric): SingleStatPanel = {
    val newMetrics = metrics ++ List(metric)
    this.copy(metrics = newMetrics)
  }

  def withMetrics(metrics: List[Metric]): SingleStatPanel =
    metrics.foldLeft(this)((acc, m) => acc.withMetric(m))


  def build(panelId: Int, span: Int): Json = {
    val colors = List("rgba(225, 40, 40, 0.59)", "rgba(245, 150, 40, 0.73)", "rgba(71, 212, 59, 0.4)")
    val colorsJson = if (invertThresholdOrder) colors.reverse else colors
    val targetJson = (availableRefIds zip metrics).map { case (id, metric) => metric.build(id) }

    json"""{
             "title": $title,
             "error": false,
             "span": $span,
             "editable": true,
             "type": "singlestat",
             "id": $panelId,
             "links": [],
             "maxDataPoints": 100,
             "interval": null,
             "targets": $targetJson,
             "cacheTimeout": null,
             "format": "none",
             "prefix": $prefix,
             "postfix": $postfix,
             "valueName": "current",
             "prefixFontSize": "100%",
             "valueFontSize": "120%",
             "postfixFontSize": "100%",
             "thresholds": $thresholds,
             "colorBackground": true,
             "colorValue": false,
             "colors": $colorsJson,
             "sparkline": {
               "show": true,
               "full": false,
               "lineColor": "rgb(71, 248, 35)",
               "fillColor": "rgba(130, 189, 31, 0.18)"
               }
            }"""

  }
}

object SingleStatPanel {
  
  def apply(title: String): SingleStatPanel =
    SingleStatPanel(
      title = title,
      prefix = "",
      postfix = "",
      thresholds = Thresholds(0, 50, 200),
      invertThresholdOrder = false,
      availableRefIds = (65 to 91).map(_.toChar.toString).toList,
      metrics = List.empty
    )
}
