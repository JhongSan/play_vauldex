package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import scala.concurrent.{ Future, ExecutionContext }
import app.auth.AuthorizedAction

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */


@Singleton
class HomeController @Inject()(authorizedAction: AuthorizedAction, val controllerComponents: ControllerComponents)(implicit val ec: ExecutionContext) extends BaseController {
  def createSession = Action.async { implicit request =>
    Future.successful(Ok.withSession("email" -> "user@gmail.com"))
  }
  def home = AuthorizedAction.async { implicit request =>
    Future.successful(Ok("You are logged in"))
  }
}
