package models.domain

import java.util.UUID
import play.api.libs.json._

final case class TodoList(
    id: UUID,
    userId: UUID,
    task: String,
    completed: Boolean
)

object TodoList:
    implicit val todolistReads: Reads[TodoList] = Json.reads[TodoList]
    implicit val todoListWrites: Writes[TodoList] = Json.writes[TodoList]