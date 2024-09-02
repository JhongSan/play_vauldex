package models.domain

import java.util.UUID
import java.time.LocalDateTime
import play.api.libs.json._

case class Post(
	id: UUID,
	message: String,
	postTime: LocalDateTime,
	password: Option[String],
	picture: Option[String]
)

object Post:
	implicit val postReads: Reads[Post] = Json.reads[Post]
	implicit val postWrites: Writes[Post] = Json.writes[Post]