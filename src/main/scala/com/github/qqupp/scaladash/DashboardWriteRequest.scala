package com.github.qqupp.scaladash

import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._

final case class DashboardEnvelope(dashboard: Json, overwrite: Boolean)

object DashboardEnvelope {

  def jsonFor(dashboard: Dashboard, overwrite: Boolean = true): Json =
    DashboardEnvelope(dashboard.build, overwrite).asJson

}
