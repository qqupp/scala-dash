package com.github.qqupp.scaladash

case class Thresholds(lower: Int, mid: Int, upper: Int) {

  def toCsv = s"$lower, $mid, $upper"

}
