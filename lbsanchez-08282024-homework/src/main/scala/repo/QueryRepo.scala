package repo

import slick.jdbc.H2Profile.api._
import scala.concurrent.ExecutionContext
import java.sql.Date
import domain._

case class Result21(username: String, courseName: String)
case class Result22(username: String, courseName: Option[String])
case class Result24(courseName: String, enrolled: Int)
case class Result25(username: String, courseName: String, score: Score)
case class Result26(username: String, courseName: String)

def num21(implicit ec: ExecutionContext): DBIO[Seq[Result21]] = {
    (for {
        (user, course) <- users.join(userCourses).on(_.id === _.userId).join(courses).on(_._2.courseId === _.id)
    } yield (user._1.username, course.name)).result
        .map(_.map { case (username, courseName) => Result21(username, courseName) })
}

def num22(implicit ec: ExecutionContext): DBIO[Seq[Result22]] = {
    (for {
        (user, course) <- users.joinLeft(userCourses.join(courses).on(_.courseId === _.id)) on (_.id === _._1.userId)
    } yield (user.username, course.map(_._2.name))).result
        .map {_.map {
            case (username, courseName) => Result22(username, courseName)
            }
        }
}

def num23(implicit ec: ExecutionContext): DBIO[Seq[String]] = {
  userCourseScores.filter(_.score === (A))
    .join(users).on(_.userId === _.id)
    .map(_._2.username).result
}

def num24(implicit ec: ExecutionContext): DBIO[Seq[Result24]] = {
    userCourses
        .join(courses).on(_.courseId === _.id)
        .groupBy { case (_, course) => course.name }
        .map { case (courseName, group) => (courseName, group.length) }.result
            .map {_.map { case (courseName, enrolled) => Result24(courseName, enrolled)
                }
            }
}

def num25(implicit ec: ExecutionContext): DBIO[Seq[Result25]] = {
  userCourseScores
    .join(users).on(_.userId === _.id)
    .join(courses).on(_._1.courseId === _.id)
    .map { case ((userCourseScore, user), course) => 
        (user.username, course.name, userCourseScore._1.score) }
    .sortBy(_._3.desc).result.map{_.map { 
        case (username, courseName, score) => Result25(username, courseName, score)}}
}

def num26(implicit ec: ExecutionContext): DBIO[Seq[Result26]] = {
    userCourses
        .joinLeft(userCourseScores).on { case (userCourse, userCourseScore) =>
            userCourse.userId === userCourseScore.userId && userCourse.courseId === userCourseScore.courseId }
        .filter(_._2.isEmpty)
        .join(users).on(_._1.userId === _.id)
        .join(courses).on(_._1._1.courseId === _.id)
        .map { case ((_, user), course) => (user.username, course.name) }.result
            .map{_.map{ case(username, courseName) => Result26(username, courseName)}}
}
