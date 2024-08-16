package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "HomeController GET" should {

    "Number 1" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.action1().apply(FakeRequest(GET, "/action1"))

      status(home) mustBe OK
    }

    "Number 2" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.action2().apply(FakeRequest(GET, "/action2"))

      status(home) mustBe OK
    }

    "Number 3" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.action3().apply(FakeRequest(GET, "/action3"))

      status(home) mustBe OK
    }

    "Number 4" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.action4().apply(FakeRequest(GET, "/action4"))

      status(home) mustBe OK
    }

    "Number 5" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.action5().apply(FakeRequest(GET, "/action5"))

      status(home) mustBe OK
    }
  }
}
