package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class ActionController @Inject()(val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {
  def action1 = Action {
    Ok("Hello World")
  }

  def action2 = Action {
    Ok(<html>HTML</html>).as("text/html")
  }

  def action3 = Action {
    Ok(<html>HTML</html>).as("text/html")
  }

  def action4 = Action {
    Ok(views.html.action4("Hello World")).as("text/html")
  }

  def action5 = Action { implicit request: Request[AnyContent] =>
    val remote = request.remoteAddress
    Ok(remote)
  }

  def action6 = Action(parse.json) { request =>
    request.body.asOpt[JsValue].map{json =>
      Ok(json)
    }.getOrElse(Ok("No Json Body Found"))
  }

  case class User(id: Int, name: String)
  def findUserById(id: Int): Future[Option[User]] = {
    val users = List(
      User(1, "John"),
      User(2, "William"),
      User(3, "James"),
      User(4, "Michael"),
      User(5, "Robert"),
      User(6, "David"),
      User(7, "Thomas"),
      User(8, "Charles"),
      User(9, "George"),
      User(10, "Joseph"))
    Future.successful(users.find(_.id == id))
}

  def action7 = Action.async(parse.formUrlEncoded) { request =>
    val maybeId = request.body.get("user").flatMap(_.headOption.flatMap(value => scala.util.Try(value.toInt).toOption))
    maybeId match{
      case Some(id) =>
        findUserById(id).map{
          case Some(user) => Ok(user.toString).withSession("userId" -> id.toString)
          case None => NotFound
        }
      case None => Future.successful(BadRequest("Invalid user ID"))
    }
  }

}
