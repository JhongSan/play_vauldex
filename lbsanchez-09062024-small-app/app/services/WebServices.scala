package services

import javax.inject._
import play.api.libs.ws._
import play.api.cache.AsyncCacheApi
import play.api.libs.json._
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class WebService @Inject()(ws: WSClient, cache: AsyncCacheApi)(implicit ec: ExecutionContext) {

  def fetchArticle(keyword: String): Future[JsValue] = {
  	val cacheKey = s"everything?=$keyword&apiKey=b64a53150b824b9db5bd871015509bc0"
  	cache.getOrElseUpdate(cacheKey){
  	  	ws.url(s"https://newsapi.org/v2/everything?q=$keyword&apiKey=238b50b99f4e479b834355c775cd05f0").get().map{ response =>
  	  		response.json
  	  	}
  	}
  }
}
