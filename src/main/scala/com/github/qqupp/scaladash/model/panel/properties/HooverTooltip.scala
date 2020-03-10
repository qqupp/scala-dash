package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties.HooverTooltip.{TooltipMode, TooltipSortOrder}

final case class HooverTooltip(mode: TooltipMode, sortOrder: TooltipSortOrder)

object HooverTooltip {

  val default: HooverTooltip = HooverTooltip(AllSeries, Decreasing)

  sealed trait TooltipMode
  case object Single extends TooltipMode
  case object AllSeries extends TooltipMode

  sealed trait TooltipSortOrder
  case object None extends TooltipSortOrder
  case object Increasing extends TooltipSortOrder
  case object Decreasing extends TooltipSortOrder

}
