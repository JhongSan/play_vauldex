import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

def f1: Future[Int] = Future(1)
def f2: Future[Option[Int]] = Future.successful(Some(10))
def f3: Future[Option[Int]] = Future.successful(None)
def f4: Future[Seq[Int]] = Future.successful(1 to 10)
def f5: Future[Seq[Int]] = Future.successful(Nil)
def f6: Future[Option[Seq[Int]]] = Future.successful(Some(1 to 10))
def f7: Future[Option[Seq[Int]]] = Future.successful(None)
def f8: Future[Seq[Option[Int]]] = Future.successful(Seq(Some(100), Some(1000), None))
def f9: Future[Option[Future[Int]]] = Future.successful(Some(Future.successful(1)))
def f10: Future[Seq[Future[Int]]] = Future.successful((1 to 10).map(Future.successful))

// #1 Get the result of f1 + f2
val num1 = f1.flatMap{
  r1 => f2.map(r2 => r1 + r2.getOrElse(0))
}
//num1.foreach(println)

// #2: Get the result of f1 + f2 + f3
val num2 = num1.flatMap{
  n1 => f3.map(r3 => n1 + r3.getOrElse(0))
}
//num2.foreach(println)

// #3: Get sum total of f4
val num3 = f4.map(_.sum)
//num3.foreach(println)

// #4: Get sum total of f4 and f5
val num4 = f4.flatMap{
  r4 => f5.map(r5 => (r5 ++ r4).sum)
}
//num4.foreach(println)

// #5 Transform f4 to `Future[Seq[Int], Seq[Int]]` as `Future((Range 1 to 5, Range 6 to 10))`
val num5 = f4.map(_.splitAt(5))
//num5.foreach(println)

@main def test =
  // #1 Get the result of f1 + f2
  num1.foreach(println)

  // #2: Get the result of f1 + f2 + f3
  num2.foreach(println)

  // #3: Get sum total of f4
  num3.foreach(println)

  // #4: Get sum total of f4 and f5
  num4.foreach(println)

  // #5 Transform f4 to `Future[Seq[Int], Seq[Int]]` as `Future((Range 1 to 5, Range 6 to 10))`
  num5.foreach(println)