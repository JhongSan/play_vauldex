package services

import javax.inject.Inject
import play.api.cache.AsyncCacheApi
import play.api.libs.ws._
import scala.concurrent.{ExecutionContext, Future}

class ExternalService @Inject()(ws: WSClient, cache: AsyncCacheApi)(implicit ec: ExecutionContext) {
  def fetchExternalData(): Future[String] = {
    val cacheKey = "external.data"
    
    cache.getOrElseUpdate(cacheKey) {
      val request: WSRequest = ws.url("https://pokeapi.co/api/v2/pokemon")
      request.get().map { response =>
        response.body
      }
    }
  }
}