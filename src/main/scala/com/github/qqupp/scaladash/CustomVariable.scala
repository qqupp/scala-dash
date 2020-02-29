package com.github.qqupp.scaladash

import io.circe.{Encoder, Json}
import io.circe.literal._
import io.circe.generic.auto._


final case class CustomVariable(name: String, label: String, defaultValue: String, otherValues: List[String])

object CustomVariable {

  private case class OptionJson(text: String, value: String, selected: Boolean)

  implicit val jsonEncoder: Encoder[CustomVariable] =
    cv => {
      val options: List[OptionJson] =
        OptionJson(cv.defaultValue, cv.defaultValue, true) ::
        cv.otherValues.map( s => OptionJson(s, s, false))

      val query: String = (cv.defaultValue :: cv.otherValues).mkString(",")

      json"""{
               "allValue": null,
               "current": {
                 "tags": [],
                 "text": ${cv.defaultValue},
                 "value": ${cv.defaultValue}
               },
               "hide": 0,
               "includeAll": false,
               "label": ${cv.label},
               "multi": false,
               "name": ${cv.name},
               "options": $options,
               "query": $query,
               "skipUrlSync": false,
               "type": "custom"
              }"""
  }

}


