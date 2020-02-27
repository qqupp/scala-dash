package com.github.qqupp.scaladash.generators

import com.github.qqupp.scaladash.Metric
import com.github.qqupp.scaladash.Metric.{GenericMetric, PrometheusMetric}
import org.scalacheck.{Arbitrary, Gen}

trait MetricGen {

 implicit val metricArbitrary: Arbitrary[Metric] = Arbitrary(Gen.oneOf(genericMetricGen, prometheusMetricGen))

  val genericMetricGen: Gen[Metric] =
    for {
      target <- Gen.alphaNumStr
      rightYAxisMetricName <- Gen.option(Gen.alphaNumStr)
      hide <- Gen.oneOf(false, true)
    } yield
      GenericMetric(target,  rightYAxisMetricName, hide)

  val prometheusMetricGen: Gen[Metric] =
    for {
      target <- Gen.alphaNumStr
      rightYAxisMetricName <- Gen.option(Gen.alphaNumStr)
      hide <- Gen.oneOf(false, true)
    } yield
      PrometheusMetric(target,  rightYAxisMetricName, None, None, None, None, None, hide)

}
