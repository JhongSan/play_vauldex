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

@Singleton
class HomeController @Inject()(cc: ControllerComponents, externalService: ExternalService, 
  cache: AsyncCacheApi, ws: WSClient)(implicit ec: ExecutionContext) extends AbstractController(cc) {
  
  //////----CACHE----///////
  /*--Get String--
  cache.set("key","arf")
  def index() = Action.async {
   cache.get[String]("key").map{x=>
    Ok(x.get)
   }
  }
  */
  /* --Get Action--
  def action = Ok("Cat")
  cache.set("key",action)
  def index() = Action.async {
   cache.get[Result]("key").map{x=>
    println(x)
    x.getOrElse(Ok("bad shit"))
   }
  }
  */
  /* --Get OrElse Action
  def action = Ok("Cat")
  cache.set("key",action)
  //cache.remove("key")
  def index() = Action.async {
   cache.getOrElseUpdate[Result]("key"){
    Future(Ok("Bad Meow"))
   }
  }
  */
  /* NOTES
  To remove an item from the cache use the remove method:
    val removeResult: Future[Done] = cache.remove("item.key")
  To remove all items from the cache use the removeAll method:
    val removeAllResult: Future[Done] = cache.removeAll()
  */
  // def index() = cached("homePage") {
  //   Action {
  //     Ok("Hello world")
  //   }
  // }
  //////----END OF CACHE----///////
  //////----WEB SERVICES----///////
  /* -- GET--
  val request = ws.url("http://localhost:8000/books").get().map(_.body)
  def index() = Action.async{
    request.map{
      x=>
        Ok(Json.parse(x))
    }
  }
  */
  ///*--POST--
  val jsonData = """{"title":"Martoms Potter and The Sorcerer's Stone","author":"J.K. Rowling","yearPublished":18}"""
  val request = ws.url("http://localhost:8000/books")
    .post(Json.parse(jsonData))
    .map{
      response =>
        Ok(response.body)
    }
  def index() = Action.async{
    request
  }
  //*/
}