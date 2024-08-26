package models.repo

import models.domain.{User, Profile}
import models.repo.UserProfileTables
import scala.concurrent.{Future, ExecutionContext}
import slick.jdbc.JdbcProfile
import java.util.UUID
import javax.inject.Inject
import play.api.db.slick._

@Singleton
class UserRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
    (implicit ec: ExecutionContext) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  val userProfileTables = new UserProfileTables(profile)
  import userProfileTables._

  def addUser(user: User): Future[Int] = db.run(users += user)

  def addProfile(profile: Profile): Future[Int] = db.run(profiles += profile)

  def updateUser(id: UUID, newUsername: String, newEmail: String): Future[Int] = {
    db.run(users.filter(_.id === id).map(u => (u.username, u.email)).update((newUsername, newEmail)))
  }

  def updateProfile(id: UUID, livingCountry: String, firstLanguage: String, secondLanguage: Option[String]): Future[Int] = {
    db.run(profiles.filter(_.userId === id)
    .map(p => (p.livingCountry, p.firstLanguage, p.secondLanguage))
    .update((livingCountry, firstLanguage, secondLanguage)))
  }

  def getUserWithProfile(id: UUID): Future[Option[(User, Profile)]] = {
    val query = for {
      user <- users if user.id === id
      profile <- profiles if profile.userId === id
    } yield (user, profile)

    db.run(query.result.headOption)
  }
}
