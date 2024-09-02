package controllers

import models.domain._
import play.api._
import play.api.mvc._
import java.util.UUID
import java.time.LocalDateTime
import java.nio.file.{Files, Paths}
import javax.inject._
import scala.concurrent.ExecutionContext
import play.api.libs.Files.TemporaryFile


/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val uploadDir = "public/uploads/"

  def index = Action { implicit request: Request[AnyContent] =>
    val files = new java.io.File(uploadDir).listFiles.filter(_.isFile).map { file =>
      Picture(UUID.randomUUID(), file.getName, LocalDateTime.now())
    }.toSeq

    Ok(views.html.index(files))
  }

  def upload = Action(parse.multipartFormData) { implicit request: Request[MultipartFormData[TemporaryFile]] =>
    request.body.file("picture").map { picture =>
      val filename = Paths.get(picture.filename).getFileName.toString
      val filePath = Paths.get(uploadDir, filename).toString

      picture.ref.copyTo(Paths.get(filePath), replace = true)

      Redirect(routes.HomeController.index()).flashing("success" -> "File uploaded")
    }.getOrElse {
      Redirect(routes.HomeController.index()).flashing("error" -> "Missing file")
    }
  }
}
