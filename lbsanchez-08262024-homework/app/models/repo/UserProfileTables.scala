package models.repo

import models.domain.{User, Profile}
import slick.jdbc.JdbcProfile
import java.util.UUID
import java.time.LocalDate

class UserProfileTables(val profile: JdbcProfile) {
  import profile.api._

  class UserTable(tag: Tag) extends Table[User](tag, "USERS") {
    def id: Rep[UUID] = column[UUID]("ID", O.PrimaryKey)
    def username: Rep[String] = column[String]("USERNAME", O.Unique)
    def password: Rep[String] = column[String]("PASSWORD")
    def email: Rep[String] = column[String]("EMAIL", O.Unique)

    def * = (id, username, password, email) <> ((User.apply _).tupled, User.unapply)
  }

  val users = TableQuery[UserTable]

  class ProfileTable(tag: Tag) extends Table[Profile](tag, "PROFILES") {
    def userId: Rep[UUID] = column[UUID]("USER_ID", O.PrimaryKey)
    def firstName: Rep[String] = column[String]("FIRST_NAME")
    def middleName: Rep[Option[String]] = column[Option[String]]("MIDDLE_NAME")
    def lastName: Rep[String] = column[String]("LAST_NAME")
    def birthDate: Rep[LocalDate] = column[LocalDate]("BIRTH_DATE")
    def livingCountry: Rep[String] = column[String]("LIVING_COUNTRY")
    def firstLanguage: Rep[String] = column[String]("FIRST_LANGUAGE")
    def secondLanguage: Rep[Option[String]] = column[Option[String]]("SECOND_LANGUAGE")

    def * = (userId, firstName, middleName, lastName, birthDate, livingCountry, firstLanguage, secondLanguage) <> ((Profile.apply _).tupled, Profile.unapply)

    def userFK = foreignKey("USER_FK", userId, users)(_.id)
  }

  val profiles = TableQuery[ProfileTable]
}
