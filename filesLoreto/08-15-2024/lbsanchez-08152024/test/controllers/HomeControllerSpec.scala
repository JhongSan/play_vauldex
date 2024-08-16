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

    "do enter valid name" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.pokedex().apply(FakeRequest(GET, "/pokedex"))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
    }

    "reject invalid name" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.pokedex().apply(FakeRequest(GET, "/pokedex"))

      status(home) mustBe BAD_REQUEST
      contentType(home) mustBe Some("application/json")
    }
    
  }
}
