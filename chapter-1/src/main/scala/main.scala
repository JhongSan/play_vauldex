// Import the Slick interface for H2:
import slick.jdbc.H2Profile.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Example extends App {

  // Case class representing a row in our table:
  final case class Message(
    sender:  String,
    content: String,
    id:      Long = 0L)

  case class TextOnly(id: Long = 0L, sender: String)

  // Helper method for creating test data:
  def freshTestData = Seq(
    Message("Dave", "Hello, HAL. Do you read me, HAL?"),
    Message("HAL",  "Affirmative, Dave. I read you."),
    Message("Dave", "Open the pod bay doors, HAL."),
    Message("HAL",  "I'm sorry, Dave. I'm afraid I can't do that.")
  )

  // Schema for the "message" table:
  final class MessageTable(tag: Tag) extends Table[Message](tag, "message") {
    def id      = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def sender  = column[String]("sender")
    def content = column[String]("content")

    def * = (sender, content, id).mapTo[Message]
  }

  // Base query for querying the messages table:
  lazy val messages = TableQuery[MessageTable]

  // An example query that selects a subset of messages:
  val halSays = messages.filter(_.sender === "HAL")
  // val specificCol = messages.map(_.content)
  // val specificCol = messages.map(_.content).filter{content:Rep[String] => content like "%afraid%"}
  // val specificCol = messages.map(mess => (mess.id, mess.sender))
  val specificCol = messages.map(mess => (mess.id, mess.sender).mapTo[TextOnly])
  val containsWord = for {
    m <- messages
    if m.content like "%bay%"
  } yield m

  // Create an in-memory H2 database;
  val db = Database.forConfig("chapter1")

  // Helper method for running a query in this example file:
  def exec[T](program: DBIO[T]): T = Await.result(db.run(program), 2.seconds)

  // Create the "messages" table:
  println("Creating database table")
  exec(messages.schema.create)

  println(messages.schema.createStatements.mkString)

  // Create and insert the test data:
  println("\nInserting test data")
  exec(messages ++= freshTestData)

  // Run the test query and print the results:
  println("\nSelecting all messages:")
  exec( messages.result ) foreach { println }

  println("\nSelecting only messages from HAL:")
  exec( halSays.result ) foreach { println }

  println("\nAdd Dave")
  println(exec(messages += Message("Dave","What if I say 'Pretty please'?")))

  println("\nSelecting only messages from Dave:")
  exec(messages.filter(_.sender === "Dave").result) foreach {println}

  println("\nSelecting a column:")
  exec(specificCol.result) foreach {println}

  println("\nContains the word:")
  println(exec(containsWord.exists.result))

  // val action: DBIO[Unit] = messages.schema.create
  // val future: Future[Unit] = db.run(action)
  // val result = Await.result(future, 2.seconds)

  // val insert: DBIO[Option[Int]] = messages ++= freshTestData
  // val insertAction: Future[Option[Int]] = db.run(insert)
  // insertAction.foreach(println)

  // val rowCount = Await.result(insertAction, 2.seconds)
  // println(rowCount)

  // val messagesAction = messages.result
  // val messagesFuture = db.run(messagesAction)
  // messagesFuture.foreach(println)

  // val messagesResults = Await.result(messagesFuture, 2.seconds)
  // println(messagesResults)

  // val sql = messages.result.statements.mkString
  // println(sql)

  // val halSays2 = for {
  //   message <- messages if message.sender === "HAL"
  // } yield message
  // db.run(halSays2.result).foreach(println)

  // val newMessage = db.run(messages += Message("Dave","What if I say 'Pretty please'?"))
  // val messageDave = db.run(messages.filter(_.sender === "Dave").result)
  // messageDave.foreach(println)

  println("\n" + Query(1).result.statements.mkString)
}
