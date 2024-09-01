package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
import java.util.UUID
import java.util.UUID._
import play.api.libs.json._
import models.domain._
import models.repo._
import java.nio.file.Path

@Singleton
class TodoController @Inject()(cc: ControllerComponents, todoRepo: TodoListRepo)(implicit ec: ExecutionContext) extends AbstractController(cc) {
    def createTodo() = Action.async(parse.json) { implicit request =>
        val json = request.body
        val userId = fromString((json \ "userId").as[String])
        val task = (json \ "task").as[String]
        val completed = (json \ "completed").as[Boolean]
        val newTodo = TodoList(randomUUID(), userId, task, completed)

        todoRepo.create(newTodo).map { _ =>
            Ok(Json.obj("status" -> "success", "message" -> "Todo Created"))
        }
    }

    def deleteTodo(id: UUID) = Action.async { implicit request =>
        todoRepo.delete(id).map { _ =>
            Ok(Json.obj("status" -> "success", "message" -> "Todo Deleted"))
        }    
    }

    def updateTodo() = Action.async(parse.json) { implicit request =>
        val json = request.body
        val id = fromString((json \ "id").as[String])
        val userId = fromString((json \ "userId").as[String])
        val task = (json \ "task").as[String]
        val completed = (json \ "completed").as[Boolean]
        val newTodo = TodoList(id, userId, task, completed)

        todoRepo.update(newTodo).map { _ =>
            Ok(Json.obj("status" -> "success", "message" -> "Todo Updated"))    
        }
    }

    def listTodos(userId: UUID) = Action.async { implicit request =>
        todoRepo.findByUserId(userId).map { todo =>
            Ok(Json.toJson(todo))
        }    
    }
}
