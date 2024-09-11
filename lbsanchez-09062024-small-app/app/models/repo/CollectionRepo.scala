package models.repo

import javax.inject._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile
import java.util.UUID
import models.domain._
import models.repo._

@Singleton
class CollectionRepo @Inject()(dbcp: DatabaseConfigProvider, val starRepo: StarRepo){
	val dbConfig = dbcp.get[PostgresProfile]
	import dbConfig._
	import profile.api._

	class CollectionTable(tag: Tag) extends Table[Collection](tag, "COLLECTION"){
		def id = column[UUID]("ID", O.PrimaryKey)
		def starId = column[UUID]("STAR_ID")
		def name = column[String]("NAME")

		def fk = foreignKey("SI_FK", starId, starRepo.stars)(_.id)
		def * = (id, starId, name).mapTo[Collection]
	}

	val keywords = TableQuery[CollectionTable]
}