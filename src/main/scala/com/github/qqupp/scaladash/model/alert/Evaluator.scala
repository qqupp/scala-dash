package com.github.qqupp.scaladash.model.alert

import io.circe.Encoder
import io.circe.literal._

sealed abstract class Evaluator(val params: List[Int], val `type`: String)

object Evaluator {

  final case class GreaterThan(x: Int) extends Evaluator(List(x), "gt")
  final case class LessThan(x: Int) extends Evaluator(List(x), "lt")
  final case class Outside(low: Int, high: Int) extends Evaluator(List(low, high), "outside_range")
  final case class Within(low: Int, high: Int) extends Evaluator(List(low, high), "within_range")
  final case object NoValue extends Evaluator(List(), "no_value")

  implicit val jsonEncoder: Encoder[Evaluator] =
    evaluator =>
      json"""{
              "params": ${evaluator.params},
              "type": ${evaluator.`type`}
            }"""
}
