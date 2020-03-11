package com.github.qqupp.scaladash.model

import com.github.qqupp.scaladash.model.panel.Panel
import io.circe.Json
import io.circe.literal._

final case class Row(height: String, title: Option[String], showTitle: Boolean, collapse: Boolean, panels: List[Panel]) {

  def withPanel(panel: Panel): Row =
    copy(panels = panels ++ List(panel))

  def withPanels(palesList: Panel *): Row =
    palesList.foldLeft(this)((acc, m) => acc.withPanel(m))

  def build(rowId: Int): Json = {
      val titleJson = title.getOrElse(s"Row $rowId")
      val panelsJson =
        panels
          .zipWithIndex
          .map{ case (panel, idx) =>
            panel.build((rowId * 10) + (idx + 1), 12 / panels.length)
          }

    json"""{
        "title": $titleJson,
        "height": $height,
        "editable": true,
        "collapse": $collapse,
        "showTitle": $showTitle,
        "panels": $panelsJson
      }"""
  }

}

object Row {

  def apply(panels: Panel*): Row =
    Row(
      height = "250px",
      title = None,
      showTitle = false,
      collapse = false,
      panels = panels.toList
    )

}
