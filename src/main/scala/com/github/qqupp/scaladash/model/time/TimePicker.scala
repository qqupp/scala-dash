package com.github.qqupp.scaladash.model.time

import com.github.qqupp.scaladash.model.time.Duration.{Days, Hours, Minutes, Seconds}
import io.circe.Encoder
import io.circe.literal._

final case class TimePicker(timeOptions: List[Duration],
                            refreshIntervals: List[Duration])

object TimePicker {

  val default: TimePicker =
    TimePicker(
      timeOptions =  List(Minutes(5), Minutes(15), Hours(1), Hours(6), Hours(12), Hours(24), Days(2), Days(7), Days(30)),
      refreshIntervals = List(Seconds(5), Seconds(10), Seconds(30), Minutes(1), Minutes(5), Minutes(15), Minutes(30), Hours(1), Hours(2), Days(1))
    )

  implicit val jsonEncoder: Encoder[TimePicker] =
    tp => json"""{
                    "type": "timepicker",
                    "collapse": false,
                    "enable": true,
                    "notice": false,
                    "now": true,
                    "time_options": ${tp.timeOptions},
                    "status": "Stable",
                    "refresh_intervals": ${tp.refreshIntervals}
                  }"""

}
