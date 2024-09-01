package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import java.util.UUID
import java.util.UUID._
import scala.concurrent.{ExecutionContext, Future}
import models.repo._
import models.domain._
import java.lang.ProcessBuilder.Redirect

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, userRepo: UserRepo, todoRepo: TodoListRepo)(implicit ex: ExecutionContext) 
  extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def loginPage() = Action { implicit request:Request[AnyContent] =>
    Ok(views.html.login(None))
  }

  def register() = Action.async(parse.json) { implicit request =>
    val json = request.body
    val username = (json \ "username").as[String]
    val password = (json \ "password").as[String]
    val newUser = User(UUID.randomUUID(), username, password)

    userRepo.validateUser(username).flatMap{
      case Some(_) => Future.successful(BadRequest(Json.obj("status" -> "error", "messge" -> "Username exist.")))
      case None =>
        userRepo.createUser(newUser).map{_ =>
          Ok(Json.obj("status" -> "success", "mesage" -> "User Created"))
        }
    }
  }

  def registerPage() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.register(None))
  }

  def login() = Action.async(parse.json) { implicit request =>
    val json = request.body
    val username = (json \ "username").as[String]
    val password = (json \ "password").as[String]

    userRepo.validateUser(username).flatMap{
      case Some(user) if user.password == password =>
        Future.successful(Ok(Json.obj("status" -> "success", "message" -> "Login Successful"))
        .withSession("username" -> username, "userId" -> user.id.toString))
      case _ => Future.successful(Unauthorized(Json.obj("status" -> "error", "message" -> "Invalid credentials")))
    }
  }

  def getUserId() = Action { implicit request: Request[AnyContent] =>
    request.session.get("userId") match{
      case Some(id) => Ok(Json.obj("userId" -> id))
      case None => Unauthorized(Json.obj("status" -> "error", "message" -> "Not logged in"))
    }  
  }

  def dashboard() = Action.async { implicit request: Request[AnyContent] =>
    request.session.get("userId").map{ userId =>
      val username = request.session.get("username").getOrElse("Guest")
      val userUUID = fromString(userId)
      todoRepo.findByUserId(userUUID).map { items =>
        Ok(views.html.dashboard(username, items))  
      }
    }.getOrElse(Future.successful(Redirect(routes.HomeController.loginPage()).flashing("error" -> "Please login.")))
  }

  def logout() = Action { implicit request: Request[AnyContent] =>
    Redirect(routes.HomeController.loginPage()).withNewSession.flashing("success" -> "Logged out")  
  }
}
