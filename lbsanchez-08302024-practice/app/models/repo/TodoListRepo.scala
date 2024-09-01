package models.repo

import javax.inject._
import java.util.UUID
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile
import scala.concurrent.{ExecutionContext, Future}
import models.domain._
import models.repo._

@Singleton
class TodoListRepo @Inject()(dbcp: DatabaseConfigProvider, val userRepo: UserRepo)(implicit ec: ExecutionContext){ 
    val dbConfig = dbcp.get[PostgresProfile]
    import dbConfig._
    import profile.api._

    class TodoLists(tag: Tag) extends Table[TodoList](tag, "TODO_LIST") {
        def id = column[UUID]("ID", O.PrimaryKey)
        def userId = column[UUID]("USER_ID")
        def task = column[String]("TASK")
        def completed = column[Boolean]("COMPLETED")

        def fk = foreignKey("UI_FK", userId, userRepo.users)(_.id)
        def * = (id, userId, task, completed).mapTo[TodoList]
    }

    val todolists = TableQuery[TodoLists]

    def findByUserId(userId: UUID): Future[Seq[TodoList]] =
        db.run(todolists.filter(_.userId === userId).result)

    def create(task: TodoList): Future[Int] =
        db.run(todolists += task)
    
    def update(task: TodoList): Future[Int] =
        db.run(todolists.filter(_.id === task.id).update(task))

    def delete(id: UUID): Future[Int] =
        db.run(todolists.filter(_.id === id).delete)
}