package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.model.alert.Evaluator.{GreaterThan, LessThan}
import com.github.qqupp.scaladash.model.alert._
import com.github.qqupp.scaladash.model.query.Query
import com.github.qqupp.scaladash.model.query.Query.GenericQuery
import com.github.qqupp.scaladash.model.panel._
import com.github.qqupp.scaladash.model.panel.properties.{DrawModes, StackMode, YAxisUnit, AxisValue}
import com.github.qqupp.scaladash.model.source.Datasource
import com.github.qqupp.scaladash.utils.JsonTestUtils._
import io.circe.literal._
import io.circe.optics.JsonPath._
import io.circe.syntax._
import io.circe.{Encoder, Json}
import org.scalacheck.magnolia._
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class GraphPanelSpec extends FlatSpec with Matchers with ScalaCheckDrivenPropertyChecks {

  behavior of "a Panel"

  it should "add a prometheus query" in {
    val panel =
      GraphPanel("test_panel")
        .withQuery(Query.prometheusQuery("tar_get"))

    val expected: Json = json"""[{"refId": "A", "expr": "tar_get"}]"""

    panel.build(1) should containKeyValue("targets", expected)
  }

  it should "render json" in {
    forAll { (q1: Query, q2: Query) =>

      val girdJson =
        json"""{
        "leftMax": null,
        "rightMax": null,
        "leftMin": null,
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
        "avg": false,
        "hideEmpty": false,
        "hideZero": false
      }"""


      val panel =
        GraphPanel(title)
          .withQueries(List(q1, q2))
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
      jsonPanel should containKeyValue("grid", girdJson)
      jsonPanel should containKeyValue("linewidth", 1)
      jsonPanel should containKeyValue("points", false)
      jsonPanel should containKeyValue("bars", false)
      jsonPanel should containKeyValue("percentage", false)
      jsonPanel should containKeyValue("legend", legendJson)
      jsonPanel should containKeyValue("nullPointMode", "connected")
      jsonPanel should containKeyValue("steppedLine", false)
      jsonPanel should containKeyValue("targets", List(q1.build("A"), q2.build("B")))
      jsonPanel should containKeyValue("links", Json.arr())
    }
  }

  it should "render stack values" in {
    forAll { stackStyle: StackMode =>
      val panel =
        GraphPanel(title)

      val paneWithStackStyle = panel.copy(visualization = panel.visualization.copy(stackModes = stackStyle))

      paneWithStackStyle.build(1) should containKeyValue("stack", stackStyle.value)
    }
  }

  it should "render with data source" in {
    forAll { datasource: Datasource =>

      val panelJson = GraphPanel(title).copy(datasource = Some(datasource)).build(panelId)

      panelJson should containKeyValue("datasource", datasource.datasourceName)
    }
  }


  it should "render bars, lines, and points" in {
    forAll { drawModes: DrawModes =>
      val jsonPanel = GraphPanel(title).withDrawModes(drawModes).build(panelId)

      jsonPanel should containKeyValue("bars", drawModes.bars.value)
      jsonPanel should containKeyValue("lines", drawModes.lines.value)
      jsonPanel should containKeyValue("points", drawModes.points.value)
    }
  }

  it should "render with target refids" in {
    forAll { (q: Query, qf: List[Query]) =>
      val jsonPanel = GraphPanel(title).withQueries(q :: qf).build(panelId)

      jsonPanel should containKeyValue("targets", (q :: qf).zipWithIndex.map { case (m, i) => m.build((i + 65).toChar.toString) })
    }
  }

  it should "render with an alert" in {
      val q1 = GenericQuery("targ01", false)
      val q2 = GenericQuery("targ02", false)

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
                         "target": ${q1.target}
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
                          "params": [2.54321],
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
                          "target": ${q2.target}
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

      val panel = GraphPanel(title)
        .withQuery(q1)
        .withQuery(q2)
        .withAlert(
          Alert("a test alert", "", 55)
            .withCondition(
              Condition(q1, GreaterThan(0))
                .copy(datasourceId = 3)
            )
            .orCondition(
              Condition(q2, LessThan(2.54321))
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
    val query1 = GenericQuery("targ01", false)
    val query2 = GenericQuery("targ02", false)

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

    val panel = GraphPanel(title)
      .withQuery(query1)
      .withQuery(query2)
      .withAlert(
        Alert("a test alert", "", 55)
          .withCondition(
            Condition(query1, GreaterThan(0))
              .copy(reducer = Reducer.Average)
          )
          .withCondition(
            Condition(query2, LessThan(3))
              .copy(operatorType = OperatorType.Or)
              .copy(reducer = Reducer.Min)
          )
      )

    val panelJson = panel.build(panelId, span)

    panelJson should containValueInPath(root.alert.conditions.index(0).reducer, expectedReducer1Json)
    panelJson should containValueInPath(root.alert.conditions.index(1).reducer, expectedReducer2Json)

  }

  it should "render alerts with notification by id and uid" in {
    val query1 = GenericQuery("targ01", false)
    val query2 = GenericQuery("targ02", false)

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

    val panel = GraphPanel(title)
      .withQuery(query1)
      .withQuery(query2)
      .withAlert(
        Alert("a test alert", "", 55)
          .withCondition(Condition(query1, GreaterThan(5)))
          .withNotification(Notification(1))
          .withNotification(Notification(2))
          .withNotification(Notification("abc"))
      )

    val panelJson = panel.build(panelId, span)
    panelJson should containValueInPath(root.alert.notifications, expectedJson)

  }

  it should "render alerts with a message" in {
    val query1 = GenericQuery("targ01", false)

    val panel = GraphPanel(title)
      .withQuery(query1)
      .withAlert(
        Alert("a test alert","This is a test message",  55)
          .withCondition(Condition(query1, GreaterThan(5)))
          .withNotification(Notification("abc"))
      )

    val panelJson = panel.build(panelId, span)
    panelJson should containValueInPath(root.alert.message, "This is a test message")

  }

  it should "render alerts with a specified no data state" in {
    val query1 = GenericQuery("targ01", false)

    val panel = GraphPanel(title)
      .withQuery(query1)
      .withAlert(
        Alert("a test alert", "", 55)
          .copy(noDataState = NoDataState.Alerting)
          .withCondition(Condition(query1, GreaterThan(5)))
          .withNotification(Notification("abc"))
      )

    val panelJson = panel.build(panelId, span)
    panelJson should containValueInPath(root.alert.noDataState, "alerting")

  }

  it should "render alerts with a specified execution error state" in {
    val query = GenericQuery("targ01", false)

    val panel = GraphPanel(title)
      .withQuery(query)
      .withAlert(
        Alert("a test alert", "", 55)
          .copy(executionErrorState = ExecutionErrorState.KeepState)
          .withCondition(Condition(query, GreaterThan(5)))
          .withNotification(Notification("abc"))
      )

    val panelJson = panel.build(panelId, span)
    panelJson should containValueInPath(root.alert.executionErrorState, "keep_state")

  }


  val panelId: Int = 10
  val title: String = "Test Panel"
  val span: Int = 22

}
