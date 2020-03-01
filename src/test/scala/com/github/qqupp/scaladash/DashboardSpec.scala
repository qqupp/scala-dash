package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.generators.dataArbitraries._
import com.github.qqupp.scaladash.model.template.Variable.{CustomVariable, QueryVariable}
import com.github.qqupp.scaladash.model.{Dashboard, Row}
import com.github.qqupp.scaladash.model.template.{VariableRefresh, VariableSort}
import com.github.qqupp.scaladash.model.time.Duration._
import com.github.qqupp.scaladash.model.time.TimeRange
import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.Json
import io.circe.literal._
import io.circe.optics.JsonPath._
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class DashboardSpec extends FlatSpec  with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "a Dashboard"

  it should "render json" in {
    forAll { (row1: Row, row2: Row) =>
      val dashboard =
        Dashboard(title)
          .withRow(row1)
          .withRow(row2)

      val dashboardJson = dashboard.build

      dashboardJson should containKeyValue("title", title)
      dashboardJson should containKeyValue("originalTitle", title)
      dashboardJson should containKeyValue("tags", Json.arr())
      dashboardJson should containKeyValue("style", "dark")
      dashboardJson should containKeyValue("timezone", "browser")
      dashboardJson should containKeyValue("editable", true)
      dashboardJson should containKeyValue("hideControls", false)
      dashboardJson should containKeyValue("sharedCrosshair", false)
      dashboardJson should containKeyValue("rows", List(row1.build(1), row2.build(2)))
      dashboardJson should containValueInPath(root.nav.index(0), navJson)
      dashboardJson should containKeyValue("refresh", "10s")
      dashboardJson should containKeyValue("version", 6)
      dashboardJson should containKeyValue("hideAllLegends", false)
      dashboardJson should containKeyValue("time",
        json"""{
                 "from": "now-15m",
                 "to": "now"
               }""")
      dashboardJson should containKeyValue("annotations",
        json"""{
                 "list": [],
                 "enable" : false
               }""")
      dashboardJson should containKeyValue("templating",
        json"""{
                 "list": []
               }""")

    }
  }

  it should "render with custom time range" in {
    val dashboard =
      Dashboard(title)
       .withTimeRange(TimeRange.RelativeFromTo(Days(2), Hours(1)))

    val dashboardJson = dashboard.build

    val expectedTimeRangeJson =
      json"""{
        "from": "now-2d",
        "to": "now-1h"
      }"""

    dashboardJson should containKeyValue("time", expectedTimeRangeJson)

  }


  it should "render with custom navigation time" in {
    val dashboard =
      Dashboard(title)
        .withNavTimeOptions(List(Hours(3), Hours(12), Days(7)))

    val dashboardJson = dashboard.build

    val expectedNavTime =
      json"""["3h", "12h", "7d"]"""

    dashboardJson should containValueInPath(root.nav.index(0).time_options, expectedNavTime)

  }

  it should "render with custom refresh intervals" in {
    val dashboard =
      Dashboard(title)
        .withNavRefreshIntervals(List(Minutes(1), Hours(1), Days(1)))

    val dashboardJson = dashboard.build

    val expectedNavIntervals =
      json"""["1m", "1h", "1d"]"""

    dashboardJson should containValueInPath(root.nav.index(0).refresh_intervals, expectedNavIntervals)

  }

  it should "render with custom variable" in {
    val dashboard =
      Dashboard(title)
        .withVariable(CustomVariable("a-var", "A Var", "default-value", List("value2", "value3")))

    val dashboardJson = dashboard.build

    val expectedJson =
      json"""[
               {
                 "allValue": null,
                 "current": {
                   "tags": [],
                   "text": "default-value",
                   "value": "default-value"
                 },
                 "hide": 0,
                 "includeAll": false,
                 "label": "A Var",
                 "multi": false,
                 "name": "a-var",
                 "options": [
                 {
                   "selected": true,
                   "text": "default-value",
                   "value": "default-value"
                 },
                 {
                   "selected": false,
                   "text": "value2",
                   "value": "value2"
                 },
                 {
                   "selected": false,
                   "text": "value3",
                   "value": "value3"
                 }
                 ],
                 "query": "default-value,value2,value3",
                 "skipUrlSync": false,
                 "type": "custom"
               }
            ]"""

    dashboardJson should containValueInPath(root.templating.list, expectedJson)

  }

  it should "render with custom query variable" in {
    val dashboard =
      Dashboard(title)
        .withVariable(
          QueryVariable(
            name = "a-var",
            label = "A Var",
            datasource ="aDataSource",
            query = "stats.app.value.*",
            sort = VariableSort.NumericalDesc,
            refresh = VariableRefresh.OnTimeRangeChange,
            regex = "(?!boo).*",
            includeAll = true,
            multi = true
          ))

    val dashboardJson = dashboard.build

    val expectedJson =
    json"""[
             {
               "allValue": null,
               "datasource": "aDataSource",
               "definition": "stats.app.value.*",
               "hide": 0,
               "includeAll": true,
               "label": "A Var",
               "multi": true,
               "name": "a-var",
               "query": "stats.app.value.*",
               "refresh": 2,
               "regex": "(?!boo).*",
               "skipUrlSync": false,
               "sort": 4,
               "tagValuesQuery": "",
               "tags": [],
               "tagsQuery": "",
               "type": "query",
               "useTags": false
             }
           ]"""

    dashboardJson should containValueInPath(root.templating.list, expectedJson)

  }

  private val title = "Test Dashboard"

  private val navJson: Json =
    json"""
        {
          "type": "timepicker",
          "enable": true,
          "status": "Stable",
          "time_options": [
          "5m",
          "15m",
          "1h",
          "6h",
          "12h",
          "24h",
          "2d",
          "7d",
          "30d"
          ],
          "refresh_intervals": [
          "5s",
          "10s",
          "30s",
          "1m",
          "5m",
          "15m",
          "30m",
          "1h",
          "2h",
          "1d"
          ],
          "now": true,
          "collapse": false,
          "notice": false
        }"""

}
