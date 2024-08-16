package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n._
import models._
import play.api.data._
import play.api.data.Forms._

case class LoginData(username: String, password: String)

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class MyController @Inject()(controllerComponents: MessagesControllerComponents) extends MessagesAbstractController(controllerComponents) {
  val loginForm = Form(mapping(
    "Username" -> text(3, 10),
    "Password" -> text(8)
    )(LoginData.apply)(LoginData.unapply))

  def login = Action { implicit request =>  
    Ok(views.html.login(loginForm))
  }

  def validateLoginGet(username: String, password: String) = Action {
  	Ok(s"$username you have logged in.")
  }

  def validateLoginPost = Action { implicit request =>
  	val postValidate = request.body.asFormUrlEncoded
  	postValidate.map{ postVal =>
  		val username = postVal("username").head
  		val password = postVal("password").head
      if(TaskListInMemoryModel.validateUser(username, password)){
        Redirect(routes.MyController.taskList).withSession("username" -> username)
      } else {
        Redirect(routes.MyController.login).flashing("error" -> "Invalid username/password.")
      }
  	}.getOrElse(Redirect(routes.MyController.login))
  }

  def validateLoginForm = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors)),
      ld => if(TaskListInMemoryModel.validateUser(ld.username, ld.password)){
              Redirect(routes.MyController.taskList).withSession("username" -> ld.username)
            } else {
              Redirect(routes.MyController.login).flashing("error" -> "Invalid username/password.")
            }
    )
  }

  def createUser = Action { implicit request =>
    val postValidate = request.body.asFormUrlEncoded
    postValidate.map{ postVal =>
      val username = postVal("username").head
      val password = postVal("password").head
      if(TaskListInMemoryModel.createUser(username, password)){
        Redirect(routes.MyController.taskList).withSession("username" -> username)
      } else {
        Redirect(routes.MyController.login).flashing("error" -> "User creation failed.")
      }
    }.getOrElse(Redirect(routes.MyController.login))
  }

  def taskList() = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val tasks = TaskListInMemoryModel.getTasks(username)
    Ok(views.html.taskList1(tasks))
    }.getOrElse(Redirect(routes.MyController.login))
  }

  def logout = Action {
    Redirect(routes.MyController.login).withNewSession
  }

  def addTask = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postValidate = request.body.asFormUrlEncoded
      postValidate.map{ postVal =>
        val task = postVal("newTask").head
        TaskListInMemoryModel.addTask(username, task)
        Redirect(routes.MyController.taskList)
      }.getOrElse(Redirect(routes.MyController.taskList))
    }.getOrElse(Redirect(routes.MyController.login))
  }

  def deleteTask = Action { implicit request =>
    val usernameOption = request.session.get("username")
    usernameOption.map { username =>
      val postValidate = request.body.asFormUrlEncoded
      postValidate.map{ postVal =>
        val index = postVal("index").head.toInt
        TaskListInMemoryModel.removeTask(username, index)
        Redirect(routes.MyController.taskList)
      }.getOrElse(Redirect(routes.MyController.taskList))
    }.getOrElse(Redirect(routes.MyController.login))
  }

  def product(prodName: String, prodNum: Int) = Action {
    Ok(s"Product Name: $prodName, product Number: $prodNum")
  }

}
