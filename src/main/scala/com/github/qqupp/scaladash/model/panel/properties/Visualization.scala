package com.github.qqupp.scaladash.model.panel.properties

final case class Visualization(drawModes: DrawModes, hooverTooltip: HooverTooltip, stackStyle: StackStyle)

object Visualization {

  val default: Visualization =
    Visualization(
      DrawModes.default,
      HooverTooltip.default,
      StackStyle.Unstacked
    )

}