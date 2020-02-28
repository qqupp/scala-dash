package com.github.qqupp.scaladash

import io.circe.Json

abstract class Panel {

  def withMetric(metric: Metric): Panel

  def withMetrics(metrics: List[Metric]): Panel

  def build(panelId: Int, span: Int): Json

}