name := """lbsanchez-08262024"""
organization := "com.jhongsan"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.14"

libraryDependencies += guice
libraryDependencies ++= Seq(
	"org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test,
	"com.typesafe.play" %% "play-json" % "2.8.1",
	//"com.typesafe.play" %% "play-slick" % "5.0.0",
	"org.playframework" %% "play-slick"            % "6.1.0",
	"com.typesafe.slick" %% "slick-codegen" % "3.5.0",
	"org.postgresql" % "postgresql" % "42.5.0",
	//"com.typesafe.slick" %% "slick-hikarip" % "3.3.2",
	"org.mindrot" % "jbcrypt" % "0.4"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.jhongsan.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.jhongsan.binders._"
