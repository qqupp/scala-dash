package com.github.qqupp.scaladash.e2e.generators

import com.github.qqupp.scaladash.model.metric.Metric
import com.github.qqupp.scaladash.model.metric.Metric.{GenericMetric, PrometheusMetric}
import org.scalacheck.magnolia._
import org.scalacheck.{Arbitrary, Gen}

object MetricGen {

  lazy val allValues: Gen[Metric] =
      Gen.oneOf(
        implicitly[Arbitrary[GenericMetric]].arbitrary,
        implicitly[Arbitrary[PrometheusMetric]].arbitrary
      )

}
