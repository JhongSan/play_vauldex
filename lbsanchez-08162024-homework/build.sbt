name := """lbsanchez-08162024-homework"""
organization := "com.jhongsan"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.14"

// libraryDependencies += guice
// libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
// libraryDependencies += "com.typesafe.play" %% "play-akka-http-server" % 2.8.18

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test,
  "com.typesafe.play" %% "play-akka-http-server" % "2.8.18",
  "org.scala-lang.modules" %% "scala-xml" % "2.2.0"
)

dependencyOverrides += "org.scala-lang.modules" %% "scala-xml" % "2.2.0"

dependencyOverrides += "org.playframework.twirl" %% "twirl-api" % "2.0.7" // Latest compatible version




// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.jhongsan.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.jhongsan.binders._"
