package com.github.qqupp.scaladash.okish

final case class Thresholds(lower: Int, mid: Int, upper: Int) {

  def toCsv = s"$lower, $mid, $upper"

}
