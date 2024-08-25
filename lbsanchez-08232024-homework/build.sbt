val scala3Version = "3.5.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "lbsanchez-practice",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "com.typesafe.slick" %% "slick"           % "3.5.0",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.5.0",
      "com.h2database"      % "h2"              % "2.1.214", //"1.4.197",
      "ch.qos.logback"      % "logback-classic" % "1.4.0",
      "org.slf4j" % "slf4j-api" % "1.7.36"
      )
  )
