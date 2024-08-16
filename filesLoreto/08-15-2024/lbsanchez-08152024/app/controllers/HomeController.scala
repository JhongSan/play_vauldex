package controllers

import javax.inject._
import play.api.mvc._
import services.ExternalService
import scala.concurrent.{ExecutionContext,Future}
import play.api.cache._
import play.api.cache.AsyncCacheApi
import scala.concurrent.duration._
import play.api.cache.Cached
import play.api.libs.ws.WSClient
import play.api.libs.ws.JsonBodyReadables._
import play.api.libs.ws.JsonBodyWritables._
import play.api.libs.json._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, externalService: ExternalService,
  cache: AsyncCacheApi, ws: WSClient)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  // def pokedex() = Action.async{
  //   request
  // }

  // def pokedex(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
  //   externalService.fetchExternalData().map { data =>
  //     Ok(views.html.index(data))
  //   }
  // }

  val exter = externalService.fetchExternalData().map { data => data
    }

  def pokemon() = Action.async {
    exter.map {
      da => Ok(views.html.index(da))
    }
  }

  def pokemonList() = {
    Action.async {
      ws.url("https://pokeapi.co/api/v2/pokemon").get().map { 
        response => Ok(response.body) }
    }
  }

  def validPokemon() = Action.async {
    exter.map {
      da => Ok(da.toString)
    }
  }
}
