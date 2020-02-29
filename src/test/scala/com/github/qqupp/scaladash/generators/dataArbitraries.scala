package com.github.qqupp.scaladash.generators

import com.github.qqupp.scaladash.Metric.{GenericMetric, PrometheusMetric}
import com.github.qqupp.scaladash.{GraphPanel, Metric, Panel, Row, SingleStatPanel}
import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.magnolia._

object dataArbitraries {


  implicit lazy val arbitraryUpTo10PanelsRow: Arbitrary[Row] =
    Arbitrary {
      for {
        size <- Gen.oneOf(1 to 10)
        panels <- implicitly[Arbitrary[Panel]].arbitrary.map( p =>
          List.fill(size)(p)
        )
      } yield Row().withPanels(panels: _*)
    }

  implicit lazy val arbitraryPanelWithMetrics: Arbitrary[Panel] =
    Arbitrary{
      for {
        name <- Gen.alphaNumStr
        kind <- Gen.oneOf("graph", "simple")
        metrics <- implicitly[Arbitrary[List[Metric]]].arbitrary
      } yield
        kind match {
          case "simple" => SingleStatPanel(name).withMetrics(metrics)
          case "graph" => GraphPanel(name).withMetrics(metrics)
        }
    }

  implicit lazy val arbitraryMetric: Arbitrary[Metric] =
    Arbitrary{
      Gen.oneOf(
        implicitly[Arbitrary[GenericMetric]].arbitrary,
        implicitly[Arbitrary[PrometheusMetric]].arbitrary
      )
    }

}