package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._
import services._
import models.domain._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */

@Singleton
class HomeController @Inject()(cc: ControllerComponents, pokeService: PokeService)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  def searchPokemon() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.searchPokemon());
  }

  def pokemon() = Action.async(parse.json) { implicit request =>
    val json = request.body
    val idOrName = (json \ "idOrName").as[String]
    Future.successful(Ok(Json.obj("status" -> "success")).withSession("idOrName" -> idOrName))
  }

  def pokemonDetails() = Action.async { implicit request =>
    val poke = request.session.get("idOrName").match{ 
      case Some(id) => id.toString
    }
    pokeService.fetchPokemon(poke).map{ json =>
      val id = (json \ "id").as[Int]
      val name = (json \ "name").as[String]
      val sprites = (json \ "sprites" \ "front_default").as[String]
      val height = (json \ "height").as[Int]
      val weight = (json \ "weight").as[Int]
      val ability = (json \ "abilities").as[Seq[JsValue]].map{abi =>
        (abi \ "ability" \ "name").as[String]
      }
      val move = (json \ "moves").as[Seq[JsValue]].map{m =>
        (m \ "move" \ "name").as[String]
      }
      val types = (json \ "types").as[Seq[JsValue]].map{t =>
        (t \ "type" \ "name").as[String]
      }

      val pokemon = Pokemon(id, name, weight, height, sprites, ability, move, types)
      Ok(views.html.index(pokemon))
    }.recover {
      case _ => NotFound(views.html.errorPage("Pokemon not found"))
    }
  }
}
