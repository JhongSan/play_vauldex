package models.domain

import java.util.UUID
import play.api.libs.json._

case class Contact(
	id: UUID,
	userId: UUID,
	firstName: Option[String],
	middleName: Option[String],
	lastName: Option[String],
	phoneNumber: Option[String],
	email: Option[String]
)

object Contact:
	implicit val contactReads: Reads[Contact] = Json.reads[Contact]
	implicit val contactWrites: Writes[Contact] = Json.writes[Contact]
