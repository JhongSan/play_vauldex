package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.collection.mutable.ListBuffer

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
case class Book(title: String, author: String, yearPublished: Int)

case class User(
  username: String,
  password: String,
  age: Int,
  favoriteBookTitles: List[String]
)

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  val books = ListBuffer(
    Book("Harry Potter and The Sorcerer's Stone", "J.K. Rowling", 1997),
    Book("Hunger Games", "Suzanne Collins", 2008),
    Book("Catching Fire", "Suzanne Collins", 2010),
    Book("Mockingjay", "Suzanne Collins", 2010),
    Book("Meditations", "Marcus Aurelius", 2011),
    Book("The Plague", "Albert Camus", 1947),
    Book("White Nights", "Fyodor Dostoevsky", 1848)
  )

  val users = ListBuffer(
    User("jsmith", "p@55w0rd", 21, List("Harry Potter", "Hunger Games", "Catching Fire")),
    User("mkjones", "p@55w0rd", 28, List("Harry Potter", "Six of Crows", "Dune")),
    User("jdoe", "p@55w0rd", 25, List("Hunger Games", "Mockingjay", "White Nights")),
  )

  implicit val bookWrites: Writes[Book] = (
    (__ \ "title").write[String] and 
    (__ \ "author").write[String] and
    (__ \ "yearPublished").write[Int])(b => (b.title, b.author, b.yearPublished))

  implicit val bookReads: Reads[Book] = (
    (__ \ "title").read[String] and 
    (__ \ "author").read[String] and
    (__ \ "yearPublished").read[Int])(Book.apply _)

  implicit val userWrites: Writes[User] = (
    (__ \ "username").write[String] and 
    (__ \ "age").write[Int] and
    (__ \ "favoriteBookTitles").write[List[String]])(u => (u.username, u.age, u.favoriteBookTitles))

  implicit val userReads: Reads[User] = (
    (__ \ "username").read[String] and 
    (__ \ "password").read[String] and
    (__ \ "age").read[Int] and
    (__ \ "favoriteBookTitles").read[List[String]])(User.apply _)

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def action1 = Action {
    val json = Json.toJson(books)
    println(json)
    Ok(json)
  }

  def action2(year: Int) = Action {
    val yr = books.filter(b => b.yearPublished == year)
    val json = Json.toJson(yr)
    println(json)
    Ok(json)
  }

  def action3 = Action(parse.json) { implicit request: Request[JsValue] =>
    val bookResult = request.body.validate[Book]
    bookResult.fold(
      errors => {
        BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors)))
      },
      book => { 
        Ok(Json.toJson(book)).as("application/json")
      }
    )
  }

  def action4() = Action {
    val json = Json.toJson(users)
    println(json)
    Ok(json)
  }

  def action5() = Action(parse.json) { implicit request: Request[JsValue] =>
    val userResult = request.body.validate[User]
    userResult.fold(
      errors => {
        BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(errors)))
      },
      user => { 
        Ok(Json.toJson(user)).as("application/json")
      }
    )
  }
}
