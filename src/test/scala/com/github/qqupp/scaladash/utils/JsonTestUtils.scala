package com.github.qqupp.scaladash.utils

import io.circe.Decoder.Result
import io.circe.{Encoder, Json}
import org.scalatest.matchers.{MatchResult, Matcher}
import io.circe.syntax._

object JsonTestUtils {

  def containKeyValue[T: Encoder](key: String, value: T): Matcher[Json] = {
    json =>
      val expectedJson = value.asJson
      val decodedDownKey: Result[Json] = json.hcursor.downField(key).as[Json]
      decodedDownKey match {
        case Left(failure) =>
          MatchResult(
            matches = false,
            s"""|containKeyValue: fail to decode json when going down to key "$key":
                | $json
                | decode failed with message $failure
              """.stripMargin,
            "Improve this A message"
          )
        case Right(downKeyJson) =>
          if (downKeyJson == expectedJson)
            MatchResult(
              matches = true,
              "Improve this B message",
              "Improve this C message"
            )
          else
            MatchResult(
              matches = false,
              s"""found $downKeyJson value when going donw to key "$key" but expected $expectedJson value
                 | $json
               """.stripMargin,
              "Improve this D message"
            )
      }
  }

}
