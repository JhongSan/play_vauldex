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

  "E-commerce app" should {

    // "render the index page from a new instance of controller" in {
    //   val controller = new HomeController(stubControllerComponents())
    //   val home = controller.index().apply(FakeRequest(GET, "/"))

    //   status(home) mustBe OK
    //   contentType(home) mustBe Some("text/html")
    //   contentAsString(home) must include ("Welcome to Play")
    // }

    // "render the index page from the application" in {
    //   val controller = inject[HomeController]
    //   val home = controller.index().apply(FakeRequest(GET, "/"))

    //   status(home) mustBe OK
    //   contentType(home) mustBe Some("text/html")
    //   contentAsString(home) must include ("Welcome to Play")
    // }

    // "render the index page from the router" in {
    //   val request = FakeRequest(GET, "/")
    //   val home = route(app, request).get

    //   status(home) mustBe OK
    //   contentType(home) mustBe Some("text/html")
    //   contentAsString(home) must include ("Welcome to Play")
    // }

    "register the user" in {
      val register = controllers.reg()

      status(register) mustBe Ok
    }

    "update he user's information" in {
      val update = controllers.upd()

      status(update) mustBe Ok
    }

    "login the user" in {
      val login = controllers.inlog()

      status(login) mustBe Ok
    }

    "logout the user" in {
      val logout = controllers.outlog()

      status(logout) mustBe Ok
    }

    "place an order for the user" in {
      val place = controllers.placeorder()

      status(place) mustBe Ok
    }

    "retrieve the user's order history" in {
      val retrieve = controllers.retrieveorder()

      status(retrieve) mustBe Ok
    }

    "retrieve an order details" in {
      val details = controllers.retrievedetails()

      status(details) mustBe Ok
    }

    "cancel an order" in {
      val cancel = controllers.cancelorder()

      status(cancel) mustBe Ok
    }
  }
}
