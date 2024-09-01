package models.repo

import javax.inject._
import java.util.UUID
import scala.concurrent.{Future, ExecutionContext}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile
import models.domain._

@Singleton
class UserRepo @Inject()(dbcp: DatabaseConfigProvider)(implicit ec: ExecutionContext){
  val dbConfig = dbcp.get[PostgresProfile]
  import dbConfig._
  import profile.api._

  class Users(tag: Tag) extends Table[User](tag, "USERS"){
    def id = column[UUID]("ID", O.PrimaryKey)
    def username = column[String]("USERNAME")
    def password = column[String]("PASSWORD")

    def * = (id, username, password).mapTo[User]
  }

  val users = TableQuery[Users]

  def validateUser(username: String): Future[Option[User]] =
    db.run(users.filter(_.username === username).result.headOption)

  def createUser(user: User): Future[Int] =
    db.run(users += user)
}
