package com.github.qqupp.scaladash.generators

import com.github.qqupp.scaladash.model.Row
import com.github.qqupp.scaladash.model.query.Query
import com.github.qqupp.scaladash.model.query.Query.{GenericQuery, PrometheusQuery}
import com.github.qqupp.scaladash.model.panel.{GraphPanel, Panel, SingleStatPanel}
import org.scalacheck.magnolia._
import org.scalacheck.{Arbitrary, Gen}

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

  implicit lazy val arbitraryPanelWithQuery: Arbitrary[Panel] =
    Arbitrary{
      for {
        name <- Gen.alphaNumStr
        kind <- Gen.oneOf("graph", "simple")
        queries <- implicitly[Arbitrary[List[Query]]].arbitrary
      } yield
        kind match {
          case "simple" => SingleStatPanel(name).withQueries(queries)
          case "graph" => GraphPanel(name).withQueries(queries)
        }
    }

  implicit lazy val arbitraryQuery: Arbitrary[Query] =
    Arbitrary{
      Gen.oneOf(
        implicitly[Arbitrary[GenericQuery]].arbitrary,
        implicitly[Arbitrary[PrometheusQuery]].arbitrary
      )
    }

}
