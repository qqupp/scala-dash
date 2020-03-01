package com.github.qqupp.scaladash.model.alert

import io.circe.{Encoder, Json}
import io.circe.literal._

sealed trait Evaluator {
  def asJson: Json
}

object Evaluator {

  final case class GreaterThan[T : Encoder](x: T) extends Evaluator {
    def asJson: Json = toJson(List(x), "gt")
  }
  final case class LessThan[T : Encoder](x: T) extends Evaluator {
    def asJson: Json = toJson(List(x), "lt")
  }
  final case class Outside[T: Encoder](low: T, high: T) extends Evaluator {
    def asJson: Json = toJson(List(low, high), "outside_range")
  }
  final case class Within[T: Encoder](low: T, high: T) extends Evaluator {
    def asJson: Json = toJson(List(low, high), "within_range")
  }
  final case object NoValue extends Evaluator {
    def asJson: Json = toJson[Int](List(), "no_value")
  }

  private def toJson[T : Encoder](params: List[T], tp: String): Json =
    json"""{ "params": $params, "type": ${tp} }"""
}
