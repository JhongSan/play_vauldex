package models.domain

import play.api.libs.json._
import java.util.UUID

case class Star(
	id: UUID,
	keyword: String
);

object Star:
	implicit val starReads: Reads[Star] = Json.reads[Star]
	implicit val starWrites: Writes[Star] = Json.writes[Star]