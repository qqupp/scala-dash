package com.github.qqupp.scaladash.utils

import io.circe.Decoder.Result
import io.circe.optics.JsonPath
import io.circe.syntax._
import io.circe.{DecodingFailure, Encoder, Json}
import monocle.Optional
import org.scalatest.matchers.{MatchResult, Matcher}

object JsonTestUtils {

  def containKeyValue[T: Encoder](key: String, value: T): Matcher[Json] = {
    json =>
      val expectedJson = value.asJson
      val decodedDownKey: Result[Json] = json.hcursor.downField(key).as[Json]
      decodedDownKey match {
        case Left(failure) =>
          MatchResult(
            matches = false,
            msg.decodeFail(key, json, failure),
            msg.notDecodeFail
          )
        case Right(downKeyJson) =>
          if (downKeyJson == expectedJson)
            MatchResult(
              matches = true,
              msg.matchTrue,
              msg.notMatchTrue(key)
            )
          else
            MatchResult(
              matches = false,
              msg.matchFalse(downKeyJson, key, expectedJson, json),
              msg.notMatchFalse
            )
      }
  }

  def containValueInPath[T: Encoder](jsonPath: JsonPath, value: T): Matcher[Json] = {
    json =>
      val expectedJson = value.asJson
      val decodedDownPath: Optional[Json, Json] = jsonPath.as[Json]
      val result: Option[Json] = decodedDownPath.getOption(json)
      result match {
        case None =>
          MatchResult(false, msg.decodeFail(jsonPath.toString, json), msg.notDecodeFail)
        case Some(decodeJson) =>
          if (decodeJson == expectedJson)
            MatchResult(true, msg.matchTrue, msg.notMatchTrue(jsonPath.toString))
          else
            MatchResult(false,
              msg.matchFalse(decodeJson, jsonPath.toString, expectedJson, json),
              msg.notMatchFalse)
      }
    }

}

private object msg {

  def decodeFail(path: String, json: Json, faillureMessage: DecodingFailure = DecodingFailure("", List())): String =
    s"""|Fail to decode json when going down to path "$path" in
        | $json
        | decode failed with message $faillureMessage
              """.stripMargin

  def notDecodeFail = "Improve this A message"

  def matchTrue = "Improve this B message"

  def notMatchTrue(s: String) = s"found key $s"

  def matchFalse(foundJson: Json, path: String, expectedJson: Json, mainJson: Json): String =
    s"""Found $foundJson value when going down to path "$path" but expected $expectedJson value
        | $mainJson
      """.stripMargin

  def notMatchFalse = "Improve this D message"
}
