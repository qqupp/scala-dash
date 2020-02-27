package com.github.qqupp.scaladash.generators

import com.github.qqupp.scaladash._
import org.scalacheck.{Arbitrary, Gen}

trait PanelGen {

  implicit val panelArbitrary: Arbitrary[Panel] = Arbitrary(panelGen)

  def panelGen(implicit metric: Arbitrary[Metric]): Gen[Panel] =
    for {
      name <- Gen.alphaNumStr
    } yield
      Panel(name)

}
