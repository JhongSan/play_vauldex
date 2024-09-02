package models.repo

import javax.inject._
import java.util.UUID
import java.time.LocalDateTime
import scala.concurrent.{Future, ExecutionContext}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile
import models.domain._

@Singleton
class PostRepo @Inject()(dbcp: DatabaseConfigProvider)(implicit ec: ExecutionContext){
	val dbConfig = dbcp.get[PostgresProfile]
	import dbConfig._
	import profile.api._

	class Posts(tag: Tag) extends Table[Post](tag, "POST"){
		def id = column[UUID]("ID", O.PrimaryKey)
		def message = column[String]("MESSAGE")
		def postTime = column[LocalDateTime]("POST_TIME")
		def password = column[Option[String]]("PASSWORD")
		def picture = column[Option[String]]("PICTURE")

		def * = (id, message, postTime, password, picture).mapTo[Post]
	}

	val posts = TableQuery[Posts]

	def createPost(post: Post): Future[Int] =
		db.run(posts += post)

	def findById(id: UUID): Future[Option[Post]] =
		db.run(posts.filter(_.id === id).result.headOption)

	def get(): Future[Seq[Post]] =
		db.run(posts.result)

	def deletePost(id: UUID): Future[Int] =
		db.run(posts.filter(_.id === id).delete)
}




