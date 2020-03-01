package com.github.qqupp.scaladash.model.source

import io.circe.Encoder
import io.circe.literal._

sealed abstract class Datasource(val datasourceType: String,
                                 val datasourceName: String,
                                 val datasourceUrl: String,
                                 val datasourceAccess: String,
                                 val isDefault: Boolean)

object Datasource{

  final case class Prometheus(name: String, url: String, access: String, default: Boolean) extends
    Datasource("prometheus", name, url, access, default)

  final case class Graphite(name: String, url: String, access: String, default: Boolean) extends
    Datasource("graphite", name, url, access, default)

  def prometheus(name: String, url: String): Datasource =
    Prometheus(
      name = name,
      url = url,
      access = "proxy",
      default = false
    )

  def graphite(name: String, url: String): Datasource =
    Graphite(
      name = name,
      url = url,
      access = "proxy",
      default = false
    )

  implicit val jsonEncoder: Encoder[Datasource] =
    ds =>
        json"""
          {
             "name": ${ds.datasourceName},
             "type": ${ds.datasourceType},
             "url":  ${ds.datasourceUrl},
             "access": ${ds.datasourceAccess},
             "isDefault": ${ds.isDefault}
          }
        """

}
