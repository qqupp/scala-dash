package com.github.qqupp.scaladash.model.panel

import com.github.qqupp.scaladash.model.query.Query
import io.circe.Json

abstract class Panel {

  def withQuery(query: Query): Panel

  def withQueries(queries: List[Query]): Panel

  def build(panelId: Int, span: Int): Json

}