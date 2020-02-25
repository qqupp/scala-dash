package com.github.qqupp.scaladash.okish

import io.circe.{Encoder, Json}

sealed trait DatasourceType

object DatasourceType {

  case object Type1 extends DatasourceType

  implicit val jsonEncoder: Encoder[DatasourceType] = {
    case Type1 => Json.fromString("sldkfjsldkfjlskdjf")
  }
}
