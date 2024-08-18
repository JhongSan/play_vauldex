package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import org.apache.pekko.actor._
import actors.HelloActor._
import actors.HelloActor
import scala.concurrent.duration._
import org.apache.pekko.pattern._
import org.apache.pekko.util.Timeout
import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
 
@Singleton
class HomeController @Inject()(system: ActorSystem, val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext) extends BaseController {
 
  val helloActor = system.actorOf(HelloActor.props, "hello-actor")
  implicit val timeout: Timeout = 5.seconds
 
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def sayHello(name: String) = Action.async {
    (helloActor ? SayHello(name)).mapTo[String].map { message => Ok(message) }
  }
}
