package com.github.qqupp.scaladash.model.panel

import com.github.qqupp.scaladash.model.metric.Metric
import io.circe.Json

abstract class Panel {

  def withMetric(metric: Metric): Panel

  def withMetrics(metrics: List[Metric]): Panel

  def build(panelId: Int, span: Int): Json

}