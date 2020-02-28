package com.github.qqupp.scaladash.okish

import com.github.qqupp.scaladash.utils.JsonUtils._
import io.circe.Encoder
import io.circe.literal._

final case class Notification(id: Option[Int],
                              uid: Option[String],
                              name: Option[String],
                              `type`: Option[String],
                              default: Option[String],
                              sendReminder: Option[String],
                              settings: Option[String])

object Notification {

  def apply(uid: String): Notification =
    Notification(
      id = None,
      uid = Some(uid),
      name = None,
      `type` = None,
      default = None,
      sendReminder = None,
      settings = None
    )

  def apply(id: Int): Notification =
    Notification(
      id = Some(id),
      uid = None,
      name = None,
      `type` = None,
      default = None,
      sendReminder = None,
      settings = None
    )

  implicit val jsonEncoder: Encoder[Notification] =
    notification =>
      json"""{}"""
        .addOpt("id", notification.id)
        .addOpt("uid", notification.uid)
        .addOpt("name", notification.name)
        .addOpt("type", notification.`type`)
        .addOpt("isDefault", notification.default)
        .addOpt("sendReminder", notification.sendReminder)

}
