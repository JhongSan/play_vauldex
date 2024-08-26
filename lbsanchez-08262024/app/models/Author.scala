package models

import slick.jdbc.PostgresProfile.api._
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._

final case class Author(
    id:  UUID,
    firstName: String,
	lastName: String)

final class Authors(tag: Tag) extends Table[Author](tag, "message") {
    def id      = column[UUID]("id", O.PrimaryKey)
    def firstName  = column[String]("firstName")
    def lastName = column[String]("lastName")

    def * = (id, firstName, lastName).mapTo[Author]
  }

// val author = TableQuery[Authors]

// val db = Database.forConfig("lbsanchez")