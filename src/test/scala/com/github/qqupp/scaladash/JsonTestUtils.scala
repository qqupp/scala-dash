package com.github.qqupp.scaladash

import io.circe.Decoder.Result
import io.circe.syntax._
import io.circe.{Encoder, Json}
import org.scalatest.matchers.{MatchResult, Matcher}

object JsonTestUtils {

  def containKeyValue[T: Encoder](key: String, value: T): Matcher[Json] = {
    json =>
      val expectedJson = value.asJson
      val decodedDownKey: Result[Json] = json.hcursor.downField(key).as[Json]
      decodedDownKey match {
        case Left(failure) =>
          MatchResult(false, s"""containKeyValue: fail to decode json: $json when going down to key: "$key"""", "Improve this D")
        case Right(downKeyJson) =>
          if (downKeyJson == expectedJson)
            MatchResult(true, "Improve this A", "Improve this B")
          else
            MatchResult(false, s"$downKeyJson was not equal to $expectedJson", "Improve this C")
      }
  }

}
