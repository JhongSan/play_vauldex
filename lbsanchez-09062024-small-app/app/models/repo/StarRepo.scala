package models.repo

import javax.inject._
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile
import java.util.UUID
import scala.concurrent.Future
import models.domain._
import models.repo._

@Singleton
class StarRepo @Inject()(dbcp: DatabaseConfigProvider, val keywordRepo: KeywordRepo){
	val dbConfig = dbcp.get[PostgresProfile]
	import dbConfig._
	import profile.api._

	class StarTable(tag: Tag) extends Table[Star](tag, "STAR"){
		def id = column[UUID]("ID", O.PrimaryKey)
		def keyword = column[String]("KEYWORD")

		def * = (id, keyword).mapTo[Star]
	}

	val stars = TableQuery[StarTable]

	def get(): Future[Seq[Star]] =
		db.run(stars.result)

	def add(star: Star): Future[Int] =
		db.run(stars += star)

	def find(keyword: String): Future[Option[Star]] =
		db.run(stars.filter(_.keyword === keyword).result.headOption)
}