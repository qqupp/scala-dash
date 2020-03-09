package com.github.qqupp.scaladash.utils

import diffson.jsonpatch.JsonPatch

object JsonPatcher {
  import diffson._
  import diffson.circe._
  import diffson.lcs._
  import io.circe._

  private implicit val lcs: Patience[Json] = new Patience[Json]

  private val differ: Diff[Json, JsonPatch[Json]] = diffson.jsonpatch.lcsdiff.remembering.JsonDiffDiff[Json]

  def createPatch(from: Json, to: Json): JsonPatch[Json] = diff(from, to)(differ)
}
