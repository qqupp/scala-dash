package com.github.qqupp.scaladash.model.alert

import io.circe.{Encoder, Json}
import io.circe.literal._

sealed abstract class Evaluator(val params: List[Double], val tp: String)

object Evaluator {

  final case class GreaterThan(x: Double) extends Evaluator(List(x), "gt")

  final case class LessThan(x: Double) extends Evaluator(List(x), "lt")

  final case class Outside(low: Double, high: Double) extends Evaluator(List(low, high), "outside_range")

  final case class Within(low: Double, high: Double) extends Evaluator(List(low, high), "within_range")

  final case object NoValue extends Evaluator(List(), "no_value")


  implicit val jsonEncoder: Encoder[Evaluator] =
    e =>
      json"""{ "params": ${e.params}, "type": ${e.tp} }"""

}
