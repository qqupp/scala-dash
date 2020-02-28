package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.Metric.GenericMetric
import com.github.qqupp.scaladash.okish._
import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.literal._
import io.circe.syntax._
import io.circe.{Encoder, Json}
import org.scalacheck.magnolia._
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import io.circe.optics.JsonPath._

class PanelSpec extends FlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "a Panel"

  it should "add a prometheus metric" in {
    val panel =
      Panel("test_panel")
        .withMetric(Metric.prometheusMetric("tar_get"))

    val expected: Json = json"""[{"refId": "A", "expr": "tar_get"}]"""

    panel.build(1) should containKeyValue("targets", expected)
  }

  it should "render json" in {
    forAll { (metric1: Metric, metric2: Metric, yAxis: YAxisFormat, filled: FillStyle, stacked: StackStyle, minimum: YAxisMinimum) =>

      val girdJson =
        json"""{
        "leftMax": null,
        "rightMax": null,
        "leftMin": $minimum,
        "rightMin": null,
        "threshold1": null,
        "threshold2": null,
        "threshold1Color": "rgba(216, 200, 27, 0.27)",
        "threshold2Color": "rgba(234, 112, 112, 0.22)"
        }"""

      val legendJson =
        json"""{
        "show": true,
        "values": false,
        "min": false,
        "max": false,
        "current": false,
        "total": false,
        "avg": false
      }"""

      val tooltipJson =
        json"""{
          "value_type": "cumulative",
          "shared": false
        }"""

      def seriesOverridesJson[T: Encoder](ts: Option[T]*): Json =
        ts.collect { case Some(t) =>
          json"""{
                  "alias": ${t},
                  "yaxis": 2
                  }"""
        }.asJson


      val panel =
        Panel(title)
          .withMetrics(List(metric1, metric2))
          .copy(yAxisFormat = yAxis)
          .copy(filled = filled)
          .copy(stacked = stacked)
          .copy(minimum = minimum)
          .copy(span = Some(span))

      val jsonPanel = panel.build(panelId)

      jsonPanel should containKeyValue("title", title)
      jsonPanel should containKeyValue("error", false)
      jsonPanel should containKeyValue("span", span)
      jsonPanel should containKeyValue("editable", true)
      jsonPanel should containKeyValue("type", "graph")
      jsonPanel should containKeyValue("id", panelId)
      jsonPanel should containKeyValue("datasource", Json.Null)
      jsonPanel should containKeyValue("renderer", "flot")
      jsonPanel should containKeyValue("x-axis", true)
      jsonPanel should containKeyValue("y-axis", true)
      jsonPanel should containKeyValue("y_formats", List(yAxis, yAxis))
      jsonPanel should containKeyValue("grid", girdJson)
      //jsonPanel should containKeyValue("fill", "filled") to verify
      jsonPanel should containKeyValue("linewidth", 1)
      jsonPanel should containKeyValue("points", false)
      jsonPanel should containKeyValue("pointradius", 5)
      jsonPanel should containKeyValue("bars", false)
      jsonPanel should containKeyValue("stack", stacked)
      jsonPanel should containKeyValue("percentage", false)
      jsonPanel should containKeyValue("legend", legendJson)
      jsonPanel should containKeyValue("nullPointMode", "connected")
      jsonPanel should containKeyValue("steppedLine", false)
      jsonPanel should containKeyValue("tooltip", tooltipJson)
      jsonPanel should containKeyValue("targets", List(metric1.build("A"), metric2.build("B")))
      jsonPanel should containKeyValue("aliasColors", Json.arr()) // to verify list vs obj
      jsonPanel should containKeyValue("seriesOverrides", seriesOverridesJson(metric1.rightYAxisMetricName, metric2.rightYAxisMetricName))
      jsonPanel should containKeyValue("links", Json.arr())
    }
  }

  it should "render with data source" in {
    forAll { datasource: Datasource =>

      val panelJson = Panel(title).copy(datasource = Some(datasource)).build(panelId)

      panelJson should containKeyValue("datasource", datasource)
    }
  }

  it should "render lines" in {
    forAll { lines: Boolean =>
      val jsonPanel = Panel(title).copy(lines = lines).build(panelId)

      jsonPanel should containKeyValue("lines", lines)
    }
  }

  it should "render bars" in {
    forAll { bars: Boolean =>
      val jsonPanel = Panel(title).copy(bars = bars).build(panelId)

      jsonPanel should containKeyValue("bars", bars)
    }
  }

  it should "render points" in {
    forAll { points: Boolean =>
      val jsonPanel = Panel(title).copy(points = points).build(panelId)

      jsonPanel should containKeyValue("points", points)
    }
  }

  it should "render with target refids" in {
    forAll { (metric: Metric, metrics: List[Metric]) =>
      val jsonPanel = Panel(title).withMetrics(metric :: metrics).build(panelId)

      jsonPanel should containKeyValue("targets", (metric :: metrics).zipWithIndex.map { case (m, i) => m.build((i + 65).toChar.toString) })
    }
  }

  it should "render with an alert" in {
      val metric1 = GenericMetric("targ01", None, false)
      val metric2 = GenericMetric("targ02", None, false)

      val expectedCondition1Json =
        json"""
              {
                       "evaluator": {
                         "params": [0],
                         "type": "gt"
                       }
                       ,
                       "operator": {
                         "type": "and"
                       }
                       ,
                       "query": {
                         "datasourceId": 3,
                         "model": {
                         "refId": "A",
                         "target": ${metric1.target}
                       },
                         "params": ["A", "5m", "now"]
                       }
                       ,
                       "reducer": {
                         "params": [],
                         "type": "last"
                       }
                       ,
                       "type": "query"
              }
            """

      val expectedCondition2Json =
        json"""
              {
                        "evaluator": {
                          "params": [3],
                          "type": "lt"
                        }
                        ,
                        "operator": {
                          "type": "or"
                        }
                        ,
                        "query": {
                          "datasourceId": 1,
                          "model": {
                          "refId": "B",
                          "target": ${metric2.target}
                        },
                          "params": ["B", "5m", "now"]
                        }
                        ,
                        "reducer": {
                          "params": [],
                          "type": "last"
                        }
                        ,
                        "type": "query"
              }
            """

      val panel = Panel(title)
        .withMetric(metric1)
        .withMetric(metric2)
        .withAlert(
          Alert("a test alert", 55)
            .withCondition(
              Condition(metric1, EvaluatorType.GreaterThan, 0)
                .copy(datasourceId = 3)
            )
            .withCondition(
              Condition(metric2, EvaluatorType.LessThan, 3)
                .copy(operatorType = OperatorType.Or)
            )
        )

      val panelJson = panel.build(panelId, span)

      panelJson should containValueInPath(root.alert.conditions.index(0), expectedCondition1Json)
      panelJson should containValueInPath(root.alert.conditions.index(1), expectedCondition2Json)
      panelJson should containValueInPath(root.alert.executionErrorState, "alerting")
      panelJson should containValueInPath(root.alert.frequency, "55s")
      panelJson should containValueInPath(root.alert.handler, 1)
      panelJson should containValueInPath(root.alert.name, "a test alert")
      panelJson should containValueInPath(root.alert.noDataState,"no_data")
      panelJson should containValueInPath(root.alert.notifications, Json.arr())

  }

  it should "render with an alert with reducers" in {
    val metric1 = GenericMetric("targ01", None, false)
    val metric2 = GenericMetric("targ02", None, false)

    val expectedReducer1Json =
      json"""{
                "params": [],
                "type": "avg"
             }
            """


    val expectedReducer2Json =
      json"""{
                "params": [],
                "type": "min"
             }
            """

    val panel = Panel(title)
      .withMetric(metric1)
      .withMetric(metric2)
      .withAlert(
        Alert("a test alert", 55)
          .withCondition(
            Condition(metric1, EvaluatorType.GreaterThan, 0)
              .copy(reducer = Reducer.Average)
          )
          .withCondition(
            Condition(metric2, EvaluatorType.LessThan, 3)
              .copy(operatorType = OperatorType.Or)
              .copy(reducer = Reducer.Min)
          )
      )

    val panelJson = panel.build(panelId, span)

    panelJson should containValueInPath(root.alert.conditions.index(0).reducer, expectedReducer1Json)
    panelJson should containValueInPath(root.alert.conditions.index(1).reducer, expectedReducer2Json)

  }

  it should "render alerts with notification by id and uid" in {
    val metric1 = GenericMetric("targ01", None, false)
    val metric2 = GenericMetric("targ02", None, false)

    val expectedJson =
      json"""[{
                "id": 1
             },
             {
                "id": 2
             },
             {
                "uid": "abc"
             }]
            """

    val panel = Panel(title)
      .withMetric(metric1)
      .withMetric(metric2)
      .withAlert(
        Alert("a test alert", 55)
          .withCondition(Condition(metric1, EvaluatorType.GreaterThan, 5))
          .withNotification(Notification(1))
          .withNotification(Notification(2))
          .withNotification(Notification("abc"))
      )

    val panelJson = panel.build(panelId, span)
    panelJson should containValueInPath(root.alert.notifications, expectedJson)

  }

  it should "render alerts with a message" in {
    val metric1 = GenericMetric("targ01", None, false)

    val panel = Panel(title)
      .withMetric(metric1)
      .withAlert(
        Alert("a test alert", 55)
          .copy(message = Some("This is a test message"))
          .withCondition(Condition(metric1, EvaluatorType.GreaterThan, 5))
          .withNotification(Notification("abc"))
      )

    val panelJson = panel.build(panelId, span)
    panelJson should containValueInPath(root.alert.message, "This is a test message")

  }

  it should "render alerts with a specified no data state" in {
    val metric1 = GenericMetric("targ01", None, false)

    val panel = Panel(title)
      .withMetric(metric1)
      .withAlert(
        Alert("a test alert", 55)
          .copy(noDataState = NoDataState.Alerting)
          .withCondition(Condition(metric1, EvaluatorType.GreaterThan, 5))
          .withNotification(Notification("abc"))
      )

    val panelJson = panel.build(panelId, span)
    panelJson should containValueInPath(root.alert.noDataState, "alerting")

  }

  it should "render alerts with a specified execution error state" in {
    val metric1 = GenericMetric("targ01", None, false)

    val panel = Panel(title)
      .withMetric(metric1)
      .withAlert(
        Alert("a test alert", 55)
          .copy(executionErrorState = ExecutionErrorState.KeepState)
          .withCondition(Condition(metric1, EvaluatorType.GreaterThan, 5))
          .withNotification(Notification("abc"))
      )

    val panelJson = panel.build(panelId, span)
    panelJson should containValueInPath(root.alert.executionErrorState, "keep_state")

  }


  val panelId: Int = 10
  val title: String = "Test Panel"
  val span: Int = 22

}
