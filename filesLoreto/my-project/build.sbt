name := "my-project"

version := "0.1"

scalaVersion := "3.3.1"

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "3.2.19" ,
	"com.softwaremill.sttp.client4" %% "core" % "4.0.0-M6",
	"com.lihaoyi" %% "ujson" % "2.0.0")