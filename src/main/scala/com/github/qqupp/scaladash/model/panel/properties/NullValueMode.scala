package com.github.qqupp.scaladash.model.panel.properties

sealed trait NullValueMode

object NullValueMode {

  case object NullAsNull extends NullValueMode
  case object NullAsZero extends NullValueMode
  case object Connected extends NullValueMode

}
