val scala3Version = "3.4.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "lbsanchez-08162024",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.apache.pekko" %% "pekko-actor" % "1.0.2",
      "org.apache.pekko" %% "pekko-testkit" % "1.0.2" % Test,
      "org.scalatest" %% "scalatest" % "3.2.16" % Test,
      "org.apache.pekko" %% "pekko-persistence-typed" % "1.0.2",
      "org.apache.pekko" %% "pekko-persistence-testkit" % "1.0.2" % Test
    )
  )
