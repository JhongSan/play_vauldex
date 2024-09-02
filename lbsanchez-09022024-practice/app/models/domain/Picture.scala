package models.domain

import java.util.UUID
import java.time.LocalDateTime
import play.api.libs.json._

case class Picture(id: UUID, filename: String, uploadTime: LocalDateTime)

object Picture {
  implicit val pictureFormat: OFormat[Picture] = Json.format[Picture]
}
