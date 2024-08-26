package models

import slick.jdbc.PostgresProfile.api._
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._

final case class Book(
    isbn:  String,
    title: String,
    publicationYear: Int,
    authorId: UUID,
	price: Double)

final class Books(tag: Tag) extends Table[Book](tag, "message") {
    def isbn      = column[String]("isbn", O.PrimaryKey)
    def title  = column[String]("title")
    def publicationYear  = column[Int]("publicationYear")
    def authorId = column[UUID]("authorId")
    def price = column[Double]("price")

    def * = (isbn, title, publicationYear, authorId, price).mapTo[Book]
  }

// val book = TableQuery[Books]

// val db = Database.forConfig("lbsanchez")