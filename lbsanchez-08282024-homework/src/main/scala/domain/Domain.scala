package domain

import slick.jdbc.H2Profile.api._
import scala.concurrent.ExecutionContext
import java.sql.Date

sealed trait Score

case object A extends Score
case object B extends Score
case object C extends Score
case object D extends Score

object Score:
    def fromChar(c: Char): Score =
        c match {
            case 'A' => A
            case 'B' => B
            case 'C' => C
            case _ => D
        }

    def toChar(score: Score): Char =
        score match {
            case A => 'A'
            case B => 'B'
            case C => 'C'
            case D => 'D'
        }

implicit val scoreColumnType: BaseColumnType[Score] =
    MappedColumnType.base[Score, Char](
        score => Score.toChar(score),
        char => Score.fromChar(char)
    )

final case class User(
    id: Int,
    username: String,
    email: String
)

final case class Course(
    id: Int,
    name: String,
    description: Option[String]
)

final case class UserCourse(
    userId: Int,
    courseId: Int,
    enrolledOn: Date
)

final case class UserCourseScore(
    userId: Int,
    courseId: Int,
    score: Score
)

final class Users(tag: Tag) extends Table[User](tag, "USERS") {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    def username = column[String]("USERNAME", O.Unique)
    def email = column[String]("EMAIL", O.Unique)

    def * = (id, username, email).mapTo[User]
}
val users = TableQuery[Users]

final class Courses(tag: Tag) extends Table[Course](tag, "COURSES") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  def description = column[Option[String]]("DESCRIPTION")

  def * = (id, name, description).mapTo[Course]
}
val courses = TableQuery[Courses]

final class UserCourses(tag: Tag) extends Table[UserCourse](tag, "USER_COURSES") {
  def userId = column[Int]("USER_ID")
  def courseId = column[Int]("COURSE_ID")
  def enrolledOn = column[Date]("ENROLLED_ON")

  def pk = primaryKey("PK_USER_COURSE", (userId, courseId))
  def * = (userId, courseId, enrolledOn).mapTo[UserCourse]
}
val userCourses = TableQuery[UserCourses]

final class UserCourseScores(tag: Tag) extends Table[UserCourseScore](tag, "USER_COURSE_SCORES") {
  def userId = column[Int]("USER_ID")
  def courseId = column[Int]("COURSE_ID")
  def score = column[Score]("SCORE")

  def pk = primaryKey("pk_user_course_scores", (userId, courseId))
  def * = (userId, courseId, score).mapTo[UserCourseScore]
}
val userCourseScores = TableQuery[UserCourseScores]