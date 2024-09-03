package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import scala.concurrent.{Future, ExecutionContext}
import java.util.UUID._
import play.api.libs.json._
import models.repo._
import models.domain._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, userRepo: UserRepo, contactRepo: ContactRepo)(implicit ec: ExecutionContext) 
    extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def loginPage() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.login())
  }

  def registerPage() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.register())
  }

  def login() = Action.async(parse.json) { implicit request =>
    val json = request.body
    val username = (json \ "username").as[String]
    val password = (json \ "password").as[String]

    userRepo.get(username).flatMap{
      case Some(user) if user.password == password =>
        Future.successful(Ok(Json.obj("status" -> "success", "message" -> "Login successfuly"))
        .withSession("username" -> username, "id" -> user.id.toString))
      case _ => Future.successful(Unauthorized(Json.obj("status" -> "error", "message" -> "Login failed")))
    }
  }

  def register() = Action.async(parse.json) { implicit request =>
    val json = request.body
    val username = (json \ "username").as[String]
    val password = (json \ "password").as[String]
    val newUser = User(randomUUID(), username, password)

    userRepo.get(username).flatMap{
      case Some(user) => Future.successful(BadRequest(Json.obj("status" -> "error", "message" -> "Username is taken")))
      case None => userRepo.add(newUser).map{_ => 
        Ok(Json.obj("status" -> "success", "message" -> "Create user successfuly"))
      }
    }
  }

  def getUserId() = Action { implicit request: Request[AnyContent] =>
    request.session.get("id").match{
      case Some(id) => Ok(Json.obj("userId" -> id))
      case None => Unauthorized(Json.obj("status" -> "error", "message" -> "Not logged in"))
    }
  }

  def contactBook() = Action.async { implicit request =>
    request.session.get("id").map{ userId =>
      val username = request.session.get("username").getOrElse("")
      val id = fromString(userId)

      contactRepo.get(id).map{ contacts =>
        Ok(views.html.contacts(username, contacts))
      }
    }.getOrElse(Future.successful(Redirect(routes.HomeController.loginPage())))
  }

  def logout() = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.loginPage()).withNewSession
  }
}



