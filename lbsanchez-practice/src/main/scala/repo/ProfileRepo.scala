package  repo

import slick.jdbc.H2Profile.api._
import domain._
import java.util.UUID

object ProfileRepo {
    val profiles = TableQuery[Profiles]

    val createSchema = profiles.schema.dropIfExists andThen profiles.schema.create

    def add(prof: Profile) = profiles += prof

    def findById(profId: UUID) = profiles.filter(_.accountId === profId).result.headOption

    def filterByGender(gen: Char) = profiles.filter(_.gender === gen).result

    def get = profiles.result
}
