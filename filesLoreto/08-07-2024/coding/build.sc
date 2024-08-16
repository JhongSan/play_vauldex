import mill._
import $ivy.`com.lihaoyi::mill-contrib-playlib:`,  mill.playlib._

object sbtRun extends PlayModule with SingleModule {

  def scalaVersion = "[A[A[B"
  def playVersion = "sbt run"
  def twirlVersion = "2.0.1"

  object test extends PlayTests
}
