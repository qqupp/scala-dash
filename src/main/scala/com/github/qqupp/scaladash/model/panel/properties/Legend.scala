package com.github.qqupp.scaladash.model.panel.properties

import io.circe.Encoder
import io.circe.generic.semiauto._
final case class Legend(show: Boolean, values: Boolean, min: Boolean, max: Boolean, current: Boolean, total: Boolean, avg: Boolean)


object Legend {

  val default: Legend = Legend(true, false, false, false, false, false,false)

  implicit val jsonEncoder: Encoder[Legend] = deriveEncoder[Legend]

}
