import org.scalatest.flatspec.AnyFlatSpec

class ExampleSpec extends AnyFlatSpec {
	"The main object" should "say hello" in {
		assert(HelloWorld.hello == "Hello World!")
	}
}