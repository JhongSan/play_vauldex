import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class FutureSpec extends AnyFlatSpec with Matchers:
  "Number 1" should "return the result" in {
    num1.map{
      v1 => v1 shouldBe 11
    }
  } 

  "Number 2" should "return the result" in {
    num2.map{
      v2 => v2 shouldBe 11
    }
  }

  "Number 3" should "return the result" in {
    num3.map{
      v3 => v3 shouldBe 55
    }
  }

  "Number 4" should "return the result" in {
    num4.map{
      n4 => n4 shouldBe 55
    }
  }

  "Number 5" should "transform the the given method" in {
    num5.map{
      n5 => n5 shouldBe (1 to 5, 6 to 10)
    }
  }