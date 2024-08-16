package services

import javax.inject._
import play.api.libs.ws._
import play.api.cache.AsyncCacheApi
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PokeService @Inject()(ws: WSClient, cache: AsyncCacheApi)(implicit ec: ExecutionContext) {

  private val pokeApiBaseUrl = "https://pokeapi.co/api/v2/pokemon"

  def fetchPokemon(idOrName: String): Future[JsValue] = {
    val cacheKey = s"pokemon:$idOrName"
    cache.getOrElseUpdate(cacheKey) {
      ws.url(s"$pokeApiBaseUrl/$idOrName").get().map { response =>
        response.json
      }
    }
  }
}
