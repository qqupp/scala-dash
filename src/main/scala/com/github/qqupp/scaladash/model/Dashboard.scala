package com.github.qqupp.scaladash.model

import com.github.qqupp.scaladash.model.template.Variable
import com.github.qqupp.scaladash.model.time.Duration._
import com.github.qqupp.scaladash.model.time.{Duration, TimeRange}
import io.circe.Json
import io.circe.literal._

final case class Dashboard(title: String,
                           rows: List[Row],
                           variables: List[Variable],
                           timeRange: TimeRange,
                           timeOptions: List[Duration],
                           refreshIntervals: List[Duration]) {

  def withVariable(variable: Variable): Dashboard =
    copy(variables = variables ++ List(variable))

  def withNavRefreshIntervals(values: List[Duration]): Dashboard =
    copy(refreshIntervals = values)

  def withNavTimeOptions(values: List[Duration]): Dashboard =
    copy(timeOptions = values)

  def withTimeRange(range: TimeRange): Dashboard =
    copy(timeRange = range)

  def withRow(row: Row): Dashboard =
    copy( rows = rows ++ List(row))

  def withRows(addRows: List[Row]): Dashboard =
    addRows.foldLeft(this)((acc, r) => acc.withRow(r))

  def build: Json = {
    val rowsJson = rows.zipWithIndex.map{ case (r, idx) => r.build(idx + 1) }

    val templatingJson = variables

    json"""{
                      "title": $title,
                      "originalTitle": $title,
                      "tags": [],
                      "style": "dark",
                      "timezone": "browser",
                      "editable": true,
                      "hideControls": false,
                      "sharedCrosshair": false,
                      "rows": $rowsJson,
                      "nav": [
                          {
                              "type": "timepicker",
                              "enable": true,
                              "status": "Stable",
                              "time_options": $timeOptions,
                              "refresh_intervals": $refreshIntervals,
                              "now": true,
                              "collapse": false,
                              "notice": false
                          }
                      ],
                      "time": $timeRange,
                      "templating": {
                          "list": $templatingJson
                      },
                      "annotations": {
                          "list": [],
                          "enable": false
                      },
                      "refresh": "10s",
                      "version": 6,
                      "hideAllLegends": false
          }"""
  }

}

object Dashboard {

  def apply(title: String): Dashboard =
    Dashboard(
      title = title,
      rows = List.empty,
      variables = List.empty,
      timeRange = TimeRange.RelativeLast(Minutes(15)),
      timeOptions =  List(Minutes(5), Minutes(15), Hours(1), Hours(6), Hours(12), Hours(24), Days(2), Days(7), Days(30)),
      refreshIntervals = List(Seconds(5), Seconds(10), Seconds(30), Minutes(1), Minutes(5), Minutes(15), Minutes(30), Hours(1), Hours(2), Days(1))
  )

}
