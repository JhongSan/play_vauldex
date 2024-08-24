package domain

import slick.jdbc.H2Profile.api._
import java.util.UUID

final case class Account(
    id: UUID,
    email: String
)
final case class Profile(
    accountId: UUID,
    firstName: String,
    lastName: String,
    gender: Char
)

final class Accounts(tag: Tag) extends Table[Account](tag, "message") {
    def id = column[UUID]("id", O.PrimaryKey)
    def email = column[String]("email")

    def * = (id, email).mapTo[Account]
}
final class Profiles(tag: Tag) extends Table[Profile](tag, "message") {
    def accountId = column[UUID]("accountId", O.PrimaryKey)
    def firstName = column[String]("firstName")
    def lastName = column[String]("lastName")
    def gender = column[Char]("gender", O.SqlType("Char(1)"))

    def * = (accountId, firstName, lastName, gender).mapTo[Profile]
}
