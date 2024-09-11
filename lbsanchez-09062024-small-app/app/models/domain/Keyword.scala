package models. domain

import play.api.libs.json._
import java.util.UUID

case class Keyword(
	id: UUID,
	name: String
)

object Keyword:
	implicit val keywordReads: Reads[Keyword] = Json.reads[Keyword]
	implicit val keywordWrites: Writes[Keyword] = Json.writes[Keyword]