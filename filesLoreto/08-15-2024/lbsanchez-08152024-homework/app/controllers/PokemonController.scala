package controllers

import javax.inject._
import play.api.mvc._
import services.PokeService
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._

@Singleton
class PokeController @Inject()(cc: ControllerComponents, pokeService: PokeService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def showForm: Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.searchForm())
  }

  def searchPokemon: Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    val idOrName = request.body.asFormUrlEncoded.flatMap(_.get("query").flatMap(_.headOption)).getOrElse("")
    pokeService.fetchPokemon(idOrName).map { json =>
      Ok(views.html.index(json))
    }.recover {
      case _ => NotFound(views.html.errorPage("Pokemon not found"))
    }
  }
}
