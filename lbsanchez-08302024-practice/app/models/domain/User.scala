package models.domain

import java.util.UUID
import play.api.libs.json._

final case class User(
    id: UUID,
    username: String,
    password: String
)

object User:
    implicit val userReads: Reads[User] = Json.reads[User]
    implicit val userWrites: Writes[User] = Json.writes[User]