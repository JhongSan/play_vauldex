name := """lbsanchez-practice"""
organization := "com.jhongsan"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.14"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test

libraryDependencies ++= Seq(
  guice, // Dependency Injection
  ehcache, // Cache
  ws // Web Services
)

enablePlugins(PlayScala)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.jhongsan.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.jhongsan.binders._"
