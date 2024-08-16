package models

import play.api.libs.json._

case class Pokemon(id: Int, name: String, height: Int, weight: Int, base_experience: Int)

object Pokemon {
    implicit val typeFormat: Format[String] = Format(
    Reads.StringReads.map(_.toLowerCase),
    Writes.StringWrites
  )
  
    implicit val pokemonFormat: Format[Pokemon] = Json.format[Pokemon]
}
