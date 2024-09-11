package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import scala.concurrent.{Future, ExecutionContext}
import play.api.libs.json._
import java.util.UUID._
import services._
import models.repo._
import models.domain._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

@Singleton
class HomeController @Inject()(cc: ControllerComponents, webService: WebService, starRepo: StarRepo)(implicit ec: ExecutionContext) 
extends AbstractController(cc) {

  def searchArticle() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def articles() = Action.async(parse.json) { implicit request =>
    val json = request.body
    val keyword = (json \ "keyword").as[String]
    Future.successful(Ok(Json.obj("status" -> "success")).withSession("keyword" -> keyword))
  }

  def starKeyword() = Action.async(parse.json) { implicit request =>
    val json = request.body
    val keyword = (json \ "starKeyword").as[String]
    val newKeyword = Star(randomUUID(), keyword)

    starRepo.find(keyword).flatMap{
      case Some(key) => Future.successful(BadRequest(Json.obj("status" -> "error")))
      case None => starRepo.add(newKeyword).map{_ =>
        Ok(Json.obj("status" -> "success"))
      }
    }
  }

  def articleDetails() = Action.async { implicit request =>
    val keyword = request.session.get("keyword").match{
      case Some(key) => key.toString
      case None => ""
    }
    webService.fetchArticle(keyword).map{ json =>
      val author = (json \ "articles").as[Seq[JsValue]].map{ author =>
        (author \ "author").asOpt[String]
      }
      val title = (json \ "articles").as[Seq[JsValue]].map{ title =>
        (title \ "title").asOpt[String]
      }
      val description = (json \ "articles").as[Seq[JsValue]].map{ desc =>
        (desc \ "description").asOpt[String]
      }
      val url = (json \ "articles").as[Seq[JsValue]].map{ url =>
        (url \ "url").asOpt[String]
      }
      val urlImage = (json \ "articles").as[Seq[JsValue]].map{ urlImage =>
        (urlImage \ "urlToImage").asOpt[String]
      }
      val publishedAt = (json \ "articles").as[Seq[JsValue]].map{ pubAt =>
        (pubAt \ "publishedAt").asOpt[String]
      }
      val content = (json \ "articles").as[Seq[JsValue]].map{ content =>
        (content \ "content").asOpt[String]
      }

      

      Ok(views.html.articles(keyword, author, title, description, url, urlImage, publishedAt, content))
    }
  }

  def starredList() = Action.async { implicit request =>
    starRepo.get().map{ star =>
      println(star)
      Ok(views.html.starred(star))
    }
  }
}
