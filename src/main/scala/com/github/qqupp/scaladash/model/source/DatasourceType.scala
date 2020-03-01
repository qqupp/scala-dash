package com.github.qqupp.scaladash.model.source

import io.circe.{Encoder, Json}

sealed trait DatasourceType

object DatasourceType {

  case object Prometheus extends DatasourceType

  implicit val jsonEncoder: Encoder[DatasourceType] = {
    case Prometheus => Json.fromString("prometheus")
  }
}
