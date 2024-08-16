package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n._


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(controllerComponents: MessagesControllerComponents) extends MessagesAbstractController(controllerComponents) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  // def index() = Action { implicit request: Request[AnyContent] =>
  //   Ok(views.html.index())
  // }

  val userForm = Form(
    mapping(
      "id" -> uuid,
      "email"  -> text,
      "username" -> text(100),
      "password" -> text(8, 250)
    )(User.apply _)(User.unapply _)
  )

  def login = Action { implicit request =>  
    Ok(views.html.user(userForm))
  }

  def validateLoginGet(username: String, password: String) = Action {
    Ok(s"$username you have logged in.")
  }

  def validateLoginForm = Action { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.user(formWithErrors)),
      ld => /*if(TaskListInMemoryModel.validateUser(ld.username, ld.password)){
              Redirect(routes.MyController.taskList).withSession("username" -> ld.username)
            } else {
              Redirect(routes.MyController.login).flashing("error" -> "Invalid username/password.")
            }*/Redirect(routes.HomeController.validateLoginGet(ld.username,ld.password))
    )
  }

  // def createUser = Action { implicit request =>
  //   val postValidate = request.body.asFormUrlEncoded
  //   postValidate.map{ postVal =>
  //     val username = postVal("username").head
  //     val password = postVal("password").head
  //     if(TaskListInMemoryModel.createUser(username, password)){
  //       Redirect(routes.MyController.taskList).withSession("username" -> username)
  //     } else {
  //       Redirect(routes.MyController.user).flashing("error" -> "User creation failed.")
  //     }
  //   }.getOrElse(Redirect(routes.MyController.user))
  // }

  // val profileForm = Form(
  //   mapping(
  //     "id" -> text,
  //     "firstName"  -> text,
  //     "middleName" -> optional(max(250)),
  //     "lastName" -> text(max(250))
  //   )(User.apply)(User.unapply)
  // )

  // def createUser = Action { implicit request =>
  //   userForm.bindFormRequest().
  //     fold(
  //       formWithErrors => {
  //         BadRequest(views.html.user(formWithErrors))
  //       },
  //       userData => {
  //         val newUser = models.User(userData.id, userData.email, userData.username, userData.password)
  //         vad id = models.User.create(newUser)
  //         Ok
  //       }
  //     )
  //   }
  // }

  // def createProfile = Action { implicit request =>
  //   profileForm.bindFormRequest().
  //     fold(
  //       formWithErrors => {
  //         BadRequest(views.html.user(formWithErrors))
  //       },
  //       userData => {
  //         val newUser = models.User(userData.id, userData.email, userData.username, userData.password)
  //         vad id = models.User.create(newUser)
  //         Ok
  //       }
  //     )
  //   }
  // }
}
