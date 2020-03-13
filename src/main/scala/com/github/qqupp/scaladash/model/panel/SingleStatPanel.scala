package com.github.qqupp.scaladash.model.panel

import com.github.qqupp.scaladash.model.query.Query
import com.github.qqupp.scaladash.model.panel.properties.Thresholds
import io.circe.Json
import io.circe.literal._

final case class SingleStatPanel(title: String,
                                 prefix: String,
                                 postfix: String,
                                 thresholds: Thresholds,
                                 invertThresholdOrder: Boolean,
                                 queries: List[Query],
                                 availableRefIds: List[String],
                                 span: Option[Int],
                                ) extends Panel {

  def withQuery(query: Query): SingleStatPanel = {
    val newQueries = queries ++ List(query)
    this.copy(queries = newQueries)
  }

  def withQueries(queries: List[Query]): SingleStatPanel =
    queries.foldLeft(this)((acc, m) => acc.withQuery(m))


  def build(panelId: Int, spans: Int): Json = {
    val colors = List("rgba(225, 40, 40, 0.59)", "rgba(245, 150, 40, 0.73)", "rgba(71, 212, 59, 0.4)")
    val colorsJson = if (invertThresholdOrder) colors.reverse else colors
    val targetJson = (availableRefIds zip queries).map { case (id, metric) => metric.build(id) }

    json"""{
             "title": $title,
             "error": false,
             "span": ${span.getOrElse(spans)},
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
      queries = List.empty,
      span = None
    )
}
