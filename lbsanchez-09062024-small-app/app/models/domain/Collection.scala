package models.domain

import play.api.libs.json._
import java.util.UUID

case class Collection(
	id: UUID,
	starId: UUID,
	name: String
);

object Collection:
	implicit val collectionReads: Reads[Collection] = Json.reads[Collection]
	implicit val collectionWrites: Writes[Collection] = Json.writes[Collection]