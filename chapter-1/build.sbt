import Dependencies._

ThisBuild / scalaVersion     := "2.13.13"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "chapter-1",
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick"           % "3.5.0",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.5.0",
      "com.h2database"      % "h2"              % "2.1.214", //"1.4.197",
      "ch.qos.logback"      % "logback-classic" % "1.4.0",
      "org.slf4j" % "slf4j-api" % "1.7.36"
    )
  )

// initialCommands in console := """
//   |import slick.jdbc.H2Profile.api._
//   |import Example._
//   |import scala.concurrent.duration._
//   |import scala.concurrent.Await
//   |import scala.concurrent.ExecutionContext.Implicits.global
//   |val db = Database.forConfig("chapter01")
//   |def exec[T](program: DBIO[T]): T = Await.result(db.run(program), 2 seconds)
//   |exec(messages.schema.create)
//   |exec(messages ++= freshTestData)
// """.trim.stripMargin

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
