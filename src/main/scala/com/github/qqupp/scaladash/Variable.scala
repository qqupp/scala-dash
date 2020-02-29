package com.github.qqupp.scaladash

import com.github.qqupp.scaladash.okish.VariableRefresh.OnDashboardLoad
import com.github.qqupp.scaladash.okish.VariableSort.Disabled
import com.github.qqupp.scaladash.okish.{VariableRefresh, VariableSort}
import io.circe.{Encoder, Json}
import io.circe.literal._
import io.circe.generic.auto._


sealed trait Variable

object Variable {

  final case class QueryVariable(name: String,
                                 label: String,
                                 datasource: String,
                                 query: String,
                                 regex: String ="",
                                 includeAll: Boolean = false,
                                 multi: Boolean = false,
                                 sort: VariableSort = Disabled,
                                 refresh: VariableRefresh = OnDashboardLoad
                                ) extends Variable

  final case class CustomVariable(name: String,
                                  label: String,
                                  defaultValue: String,
                                  otherValues: List[String]) extends Variable


  private case class OptionJson(text: String, value: String, selected: Boolean)

  implicit val variableJsonEncoder: Encoder[Variable] = {
      case x: CustomVariable => customVariableJsonEncoder(x)
      case x: QueryVariable => queryVariableJsonEncoder(x)
  }

  implicit val customVariableJsonEncoder: Encoder[CustomVariable] =
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

  implicit val queryVariableJsonEncoder: Encoder[QueryVariable] =
    qv => {
      json"""{
        "allValue": null,
        "datasource": ${qv.datasource},
        "definition": ${qv.query},
        "hide": 0,
        "includeAll": ${qv.includeAll},
        "label": ${qv.label},
        "multi": ${qv.multi},
        "name": ${qv.name},
        "query": ${qv.query},
        "refresh": ${qv.refresh},
        "regex": ${qv.regex},
        "skipUrlSync": false,
        "sort": ${qv.sort},
        "tagValuesQuery": "",
        "tags": [],
        "tagsQuery": "",
        "type": "query",
        "useTags": false
      }"""
    }


}


