package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import java.util.UUID
import java.util.UUID._
import java.time.LocalDateTime
import scala.concurrent.{ExecutionContext, Future}
import models.repo._
import models.domain._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, postRepo: PostRepo)(implicit ec: ExecutionContext) 
  extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def postList() = Action.async { implicit request =>
    postRepo.get().map{ post =>
      Ok(views.html.post(post))
    }
  }

  def addPost() = Action.async(parse.json) { implicit request =>
    val json = request.body
    val message = (json \ "message").as[String]
    val password = (json \ "password").asOpt[String]
    val picture = (json \ "picture").asOpt[String]
    val newPost = Post(randomUUID(), message, LocalDateTime.now(), password, picture)

    postRepo.createPost(newPost).map{ _ =>
      Ok(Json.obj("status" -> "success", "message" -> "Post created"))
    }
  }

  def removePost(id: UUID) = Action.async(parse.json) { implicit request =>
    val password = (request.body \ "password").asOpt[String]

    postRepo.findById(id).flatMap{
      case Some(post) =>
        if(post.password != Some("") && post.password == password){
          postRepo.deletePost(id).map{_ =>
            Ok(Json.obj("status" -> "success", "message" -> "Post deleted."))
          } 
        } else {
            Future.successful(BadRequest(Json.obj("status" -> "error", "message" -> "Wrong password.")))
        }
      case None => Future.successful(NotFound(Json.obj("status" -> "error", "message" -> "Invalid")))
    }
  }
}



