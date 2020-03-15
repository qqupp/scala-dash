package com.github.qqupp.scaladash.model

import com.github.qqupp.scaladash.model.template.Variable
import com.github.qqupp.scaladash.model.time.Duration._
import com.github.qqupp.scaladash.model.time.{Duration, TimePicker, TimeRange}
import io.circe.{Json, JsonObject}
import io.circe.literal._
import io.circe.syntax._

final case class Dashboard(title: String,
                           rows: List[Row],
                           variables: List[Variable],
                           timePicker: TimePicker,
                           time: TimeRange,

                           tags: List[String]) {

  def withVariable(variable: Variable): Dashboard =
    copy(variables = variables ++ List(variable))

  def withNavRefreshIntervals(values: List[Duration]): Dashboard =
    copy(timePicker = timePicker.copy(refreshIntervals = values))

  def withNavTimeOptions(values: List[Duration]): Dashboard =
    copy(timePicker = timePicker.copy(timeOptions = values))

  def withTime(time: TimeRange): Dashboard =
    copy(time = time)

  def withRow(row: Row): Dashboard =
    copy( rows = rows ++ List(row))

  def withRows(addRows: List[Row]): Dashboard =
    addRows.foldLeft(this)((acc, r) => acc.withRow(r))

  def build: Json = {
    val rowsJson = rows.zipWithIndex.map{ case (r, idx) => r.build(idx + 1) }

    Json.fromJsonObject(
      JsonObject(
        "title" -> title.asJson,
        "tags" -> tags.asJson,
        "style" ->  "dark".asJson,
        "timezone" -> "browser".asJson,
        "editable" -> Json.True,
        "hideControls" -> Json.False,
        "graphTooltip" -> Json.fromInt(1),
        "panels" -> Json.arr(),
        "time" -> time.asJson,
        "timepicker" -> timePicker.asJson,
        "templating" -> json"""{
                                 "list": $variables
                               }""",
        "annotations" -> json"""{
                                  "list": []
                                }""",
        "refresh" -> "10s".asJson,
        "schemaVersion" -> Json.fromInt(17),
        "links" -> Json.arr()
      )
    )
  }

}

object Dashboard {

  def apply(title: String): Dashboard =
    Dashboard(
      title = title,
      rows = List.empty,
      variables = List.empty,
      time = TimeRange.RelativeLast(Minutes(15)),
      timePicker = TimePicker.default,
      tags = List()
  )

}
