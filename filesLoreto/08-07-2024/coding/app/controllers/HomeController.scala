package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  val Users = java.util.UUID.randomUUID.toString
  val history = List("Food", "Drinks", "Alcohol")
  val order = "Ring"

  def reg() = Action {
    Ok(views.html.reg(Users))
  }

  def upd(user: String) = Action {
    val user = new Users
    Ok(views.html.upd(user))
  }

  def inlog(email: String, password: String) = Action {
    Ok(views.html.inlog(email,password))
  }

  def placeorder() = Action {
    Ok(views.html.placeorder(Users, order))
  }

  def retrieveorder() = Action {
    Ok(views.html.retrieveorder(history))
  }

  def retrievedetails() = Action {
    Ok(views.html.retrievedetails(order))
  }

}