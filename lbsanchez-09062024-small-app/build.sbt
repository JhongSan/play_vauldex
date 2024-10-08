name := """lbsanchez-09062024-small-app"""
organization := "com.lbsanchez"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.1"

libraryDependencies ++= Seq(
	guice,
	ws,
	caffeine,
	"org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test,
	"org.playframework" %% "play-slick" % "6.1.0",
	"org.playframework" %% "play-slick-evolutions" % "6.1.0",
	"org.postgresql" % "postgresql" % "42.5.0"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.lbsanchez.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.lbsanchez.binders._"
