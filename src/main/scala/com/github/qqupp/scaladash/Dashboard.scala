package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.Duration.{Days, Hours, Minutes, Seconds}
import io.circe.Json
import io.circe.literal._
import io.circe.generic.auto._
/*

class Dashboard:
    def __init__(self, title):
        self.title = title
        self.rows = []
        self.time = {
            "from": "now-15m",
            "to": "now"
        }
        self.time_options = ["5m", "15m", "1h", "6h", "12h", "24h", "2d", "7d", "30d"]
        self.refresh_intervals = ["5s", "10s", "30s", "1m", "5m", "15m", "30m", "1h", "2h", "1d"]
        self.variables = []

    def with_row(self, row):
        self.rows.append(row.build(len(self.rows) + 1))
        return self

    def with_rows(self, rows):
        self.rows += rows
        return self

    def with_time_range(self, start, end):
        self.time["from"] = start
        self.time["to"] = end
        return self

    def with_nav_time_options(self, options):
        self.time_options = options
        return self

    def with_nav_refresh_intervals(self, options):
        self.refresh_intervals = options
        return self

    def with_variable(self, variable):
        self.variables += [variable]
        return self

    def build(self):
        return {
            "title": self.title,
            "originalTitle": self.title,
            "tags": [],
            "style": "dark",
            "timezone": "browser",
            "editable": True,
            "hideControls": False,
            "sharedCrosshair": False,
            "rows": self.rows,
            "nav": [
                {
                    "type": "timepicker",
                    "enable": True,
                    "status": "Stable",
                    "time_options": self.time_options,
                    "refresh_intervals": self.refresh_intervals,
                    "now": True,
                    "collapse": False,
                    "notice": False
                }
            ],
            "time": self.time,
            "templating": {
                "list": [variable.build() for variable in self.variables]
            },
            "annotations": {
                "list": [],
                "enable": False
            },
            "refresh": "10s",
            "version": 6,
            "hideAllLegends": False
        }


 */
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
