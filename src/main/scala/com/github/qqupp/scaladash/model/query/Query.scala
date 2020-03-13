package com.github.qqupp.scaladash.model.query

import com.github.qqupp.scaladash.utils.JsonUtils._
import io.circe.Json
import io.circe.literal._

sealed trait Query extends Product with Serializable {
  def build(refId: String): Json
}

object  Query {

  final case class PrometheusQuery(expr: String,
                                   legendFormat: Option[String],
                                   format: Option[PrometheusQueryFormat],
                                   instant: Option[Boolean],
                                   interval: Option[Int],
                                   intervalFactor: Option[Int],
                                   hide: Boolean) extends Query {

    def build(refId: String): Json = {

      val queryJson =
        json"""{
                 "refId": $refId,
                 "expr": $expr
               }"""


      queryJson
        .addOpt("legendFormat", legendFormat)
        .addOpt("format", format)
        .addOpt("instant", instant)
        .addOpt("interval", interval)
        .addOpt("intervalFactor", intervalFactor)
        .addOpt("hide", if (hide) Some(true) else None)
    }

  }


  final case class GenericQuery(target: String, hide: Boolean) extends Query {

    def build(refId: String): Json = {

      val queryJson =
        json"""{
                 "refId": $refId,
                 "target": $target
               }"""

      queryJson
        .addOpt("hide", if (hide) Some(true) else None)

    }

  }

  def prometheusQuery(expression: String): Query = PrometheusQuery(expression, None, None, None, None, None, false)

  def genericQuery(target: String): Query = GenericQuery(target, false)

}