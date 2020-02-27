package com.github.qqupp.scaladash.generators

import com.github.qqupp.scaladash.Metric
import org.scalacheck.{Arbitrary, Gen}

trait MetricGen {

 implicit val metricArbitrary: Arbitrary[Metric] = Arbitrary(metricGen)

 val metricGen: Gen[Metric] =
   for {
     target <- Gen.alphaNumStr
     rightYAxisMetricName <- Gen.option(Gen.alphaNumStr)
     hide <- Gen.oneOf(false, true)
   } yield
     Metric(target,  rightYAxisMetricName, hide)

}
