package app.auth                                                                                                                                            
import play.api.mvc._                                                                                                                                       
import play.api.libs.json.Json      
import play.api.mvc.Results._
import scala.concurrent.{ExecutionContext, Future}

object AuthorizedAction {                                                                                                                                   
  def apply(parse: BodyParser[AnyContent])(implicit session: Session): ActionBuilder[Request, AnyContent] =                             
    new ActionBuilder[Request, AnyContent] {                                                                                                                
      override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result])(implicit executionContext: ExecutionContext): Future[Result] = {                                                                                                                                                          
        session.get("email") match {                                                                                                                        
          case Some(_) => block(request)                                                                                                                    
          case None => Future.successful(Unauthorized(Json.obj("error" -> "Unauthorized")))                                                                 
        }                                                                                                                                                   
      }                                                                                                                                                     
    }                                                                                                                                                       
}      