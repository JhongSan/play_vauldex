package models.domain

import play.api.libs.json._

case class Pokemon(
  id: Int,
  name: String,
  weight: Int,
  height: Int,
  sprites: String,
  ability: Seq[String],
  move: Seq[String],
  types: Seq[String]
)

object Pokemon:
	implicit val pokemonReads: Reads[Pokemon] = Json.reads[Pokemon]
	implicit val pokemonWrites: Writes[Pokemon] = Json.writes[Pokemon]