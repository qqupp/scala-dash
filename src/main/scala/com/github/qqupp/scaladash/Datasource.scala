package com.github.qqupp.scaladash

import io.circe.Encoder
import io.circe.literal._

/*


class Datasource:
    def __init__(self, name, type, url, access="proxy", default=False):
            self.name = name
            self.type = type
            self.url = url
            self.access = access
            self.default = default

    def build(self):
        return {
            "name": self.name,
            "type": self.type,
            "url": self.url,
            "access": self.access,
            "isDefault": self.default
        }


 */
final case class Datasource(name: String, datasourceType: DatasourceType, url: String, access: String, default: Boolean)

object Datasource{
  def apply(name: String, datasourceType: DatasourceType): Datasource = Datasource(name, datasourceType, "", "", false)

  implicit val jsonEncoder: Encoder[Datasource] =
    ds =>
        json"""
          {
             "name": ${ds.name},
             "type": ${ds.datasourceType},
             "url":  ${ds.url},
             "access": ${ds.access},
             "isDefault": ${ds.default}
          }
        """

}
