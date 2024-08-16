package controllers

import controllers.PokemonController
import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._
import org.scalatestplus.play.guice._

class PokemonControllerSpec extends PlaySpec with GuiceOneAppPerTest {

  "PokemonController GET" should {
    "return Pokémon data for a valid id or name" in {
      val controller = app.injector.instanceOf[PokemonController]
      val pokemon = controller.getPokemon("pikachu").apply(FakeRequest(GET, "/pokemon/pikachu"))
      status(pokemon) mustBe OK
    }

    "return NotFound for an invalid id or name" in {
      val controller = app.injector.instanceOf[PokemonController]
      val pokemon = controller.getPokemon("invalid").apply(FakeRequest(GET, "/pokemon/invalid"))
      status(pokemon) mustBe NOT_FOUND
    }
  }
}
