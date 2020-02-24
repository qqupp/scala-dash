package com.github.qqupp.scaladash.utils

import io.circe.{Encoder, Json, JsonObject}
import io.circe.syntax._


object JsonUtils {

  implicit class JsonOps(json: Json) {
    def add[T : Encoder](key: String, value: T): Json =
      json.deepMerge(JsonObject((key, value.asJson)).asJson)

    def addOpt[T : Encoder](key: String, value: Option[T]): Json =
      value
        .fold(json)(t => json.deepMerge(JsonObject((key, t.asJson)).asJson))
  }

}
