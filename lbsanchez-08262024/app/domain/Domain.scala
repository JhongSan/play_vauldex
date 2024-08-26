package domain

import slick.jdbc.PostgresProfile.api._
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import models._

object Domain {
	val authors = TableQuery[Authors]
	val books = TableQuery[Books]

	def insertSingleAuthor(auth: Author) =
		authors returning authors += auth

	def insertMultipleBooks(multiBooks: Seq[Book]) =
		books returning books ++= multiBooks

	def getBooksAndReverseTitle(id: UUID) = {
		val getTitle = books.filter(_.authorId === id).map(_.title).result.headOption
		getTitle.map(title => title.map(_.reverse))
	}

	def getBooksByPublicationYear(pubYear: Option[Int]) = 
		books.filterOpt(pubYear)(_.publicationYear === _)

	def updateTitlePubYearPrice(bookIsbn: String) =
		books.filter(_.isbn === bookIsbn).map(b => (b.title, b.publicationYear, b.price))
		.update(("title", 2000, 2000.00))

	def findBookByIsbn(bookIsbn: String) =
		books.filter(_.isbn === bookIsbn)

	def deleteBooksUsingId(id: UUID) =
		books.filter(_.authorId === id).delete andThen books.size.result

	def sortRecords =
		books.sortBy(_.publicationYear).take(10)
}

