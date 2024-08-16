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

    "render the index page from a new instance of controller" in {
      val controller = new HomeController(stubControllerComponents())
      val home = controller.createUser.apply(FakeRequest(POST, "/userPost"))

      status(home) mustBe OK
      contentAsString(home) must include ("Form values received successfully")
    }

    "render the index page from the application" in {
      val controller = inject[HomeController]
      val home = controller.createUser().apply(FakeRequest(POST, "/userPost"))

      status(home) mustBe BAD_REQUEST
    }

    "render the index page from the router" in {
      val request = FakeRequest(GET, "/profile")
      val home = route(app, request).get

      status(home) mustBe OK
    }

    "render the index page from the application" in {
      val controller = inject[HomeController]
      val home = controller.createProfile().apply(FakeRequest(GET, "/profile"))

      status(home) mustBe BAD_REQUEST
    }
  }
}
