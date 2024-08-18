package controllers

import org.apache.pekko
import pekko.actor._
import pekko.stream.Materializer
import javax.inject._
import play.api.mvc._
import play.api.libs.streams.ActorFlow
import scala.concurrent.{Future, ExecutionContext}

@Singleton
class ChatController @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer, ec: ExecutionContext) extends AbstractController(cc) {

  def chatSocket: WebSocket = WebSocket.acceptOrResult[String, String] { request =>
    Future.successful(Right(ActorFlow.actorRef(out => ChatActor.props(out))))
  }

  def indexHtml() = Action {
    Ok.sendFile(new java.io.File("public/html/index.html"))
  }
}

object ChatActor {
  def props(out: ActorRef): Props = Props(new ChatActor(out))
}

class ChatActor(out: ActorRef) extends Actor {
  var nickname: Option[String] = None

  override def preStart(): Unit = {
    out ! "Welcome. Enter a nickname.."
  }

  def receive: Receive = {
    case msg: String =>
      nickname match {
        case Some(name) if msg.nonEmpty =>
          val broadcastMessage = s"$name: $msg"
          context.system.eventStream.publish(broadcastMessage)
        // case Some(_) =>
        //   out ! "Nickname cannot be empty. Enter a nickname.."
        case None if msg.nonEmpty =>
          nickname = Some(msg)
          out ! s"Welcome to the chat, $msg!"
          context.system.eventStream.subscribe(self, classOf[String])
        case None =>
          out ! "Nickname cannot be empty. Enter a nickname.."
      }
    case broadcastMessage: String =>
      out ! broadcastMessage
  }

  override def postStop(): Unit = {
    context.system.eventStream.unsubscribe(self)
  }
}
