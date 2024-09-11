package models.repo

import javax.inject._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile
import java.util.UUID
import models.domain._

@Singleton
class KeywordRepo @Inject()(dbcp: DatabaseConfigProvider){
	val dbConfig = dbcp.get[PostgresProfile]
	import dbConfig._
	import profile.api._

	class KeywordTable(tag: Tag) extends Table[Keyword](tag, "KEYWORD"){
		def id = column[UUID]("ID", O.PrimaryKey)
		def name = column[String]("NAME")

		def * = (id, name).mapTo[Keyword]
	}

	val keywords = TableQuery[KeywordTable]

}