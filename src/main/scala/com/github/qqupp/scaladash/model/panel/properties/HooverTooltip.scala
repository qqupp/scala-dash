package com.github.qqupp.scaladash.model.panel.properties

import com.github.qqupp.scaladash.model.panel.properties.HooverTooltip.{TooltipMode, TooltipSortOrder}
import io.circe.Encoder
import io.circe.literal._

final case class HooverTooltip(mode: TooltipMode, sortOrder: TooltipSortOrder)

object HooverTooltip {

  val default: HooverTooltip = HooverTooltip(AllSeries, Decreasing)

  sealed abstract class TooltipMode(val shared: Boolean)
  case object Single extends TooltipMode(false)
  case object AllSeries extends TooltipMode(true)

  sealed abstract class TooltipSortOrder(val sort: Int)
  case object None extends TooltipSortOrder(0)
  case object Increasing extends TooltipSortOrder(1)
  case object Decreasing extends TooltipSortOrder(2)

  implicit val jsonEncoder: Encoder[HooverTooltip] =
    ht =>
      json"""{
               "tooltip": {
                       "shared": ${ht.mode.shared},
                       "sort": ${ht.sortOrder.sort}
                        }
             }
          """

}
