package com.github.qqupp.scaladash.model.alert

import io.circe.{Encoder, Json}
import io.circe.syntax._
import io.circe.generic.auto._

sealed trait Evaluator {
  def asJson: Json
}

object Evaluator {

  final case class GreaterThan[T : Encoder](x: T) extends Evaluator {
    override def asJson: Json = EvaluatorJson(List(x), "gt").asJson
  }
  final case class LessThan[T : Encoder](x: T) extends Evaluator {
    override def asJson: Json = EvaluatorJson(List(x), "lt").asJson
  }
  final case class Outside[T: Encoder](low: T, high: T) extends Evaluator {
    override def asJson: Json = EvaluatorJson(List(low, high), "outside_range").asJson
  }
  final case class Within[T: Encoder](low: T, high: T) extends Evaluator {
    override def asJson: Json = EvaluatorJson(List(low, high), "within_range").asJson
  }
  final case object NoValue extends Evaluator {
    override def asJson: Json = EvaluatorJson[Int](List(), "no_value").asJson
  }

  private case class EvaluatorJson[T](params: List[T], `type`: String)

}
