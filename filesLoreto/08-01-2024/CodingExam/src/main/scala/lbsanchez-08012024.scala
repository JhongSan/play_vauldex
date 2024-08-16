/* 
Instructions:
  - Fulfill the requirements below.
  - Create a scala file and name it as <yourid>-08012024
  - Submit your answer to me on slack @mribersabal on or before 4 PM today.

#1 Implement a type class named Show that provides a way to represent different types as strings,
and instances of the type class for Int, String, Boolean and List.

#2 Implement a type class named Eq that provides equality for different data types as a method to compare two elements of the same type and determine whether they are equal, and instances of the type class for Int, String and List.
*/

trait Show[T]:
  def show(s: T): String

object Show:
  //def apply[A](using show: Show[A]) = show
  def apply[A:Show] = summon[Show[A]]


  given Show[Int] with
    def show(s: Int): String = s.toString
  given Show[List[Int]] with
    def show(s: List[Int]): String = s"[${s.mkString(", ")}]"

extension[S](sh: S)(using sho: Show[S])
  def show: String = sho.show(sh)

trait Eq[T]:
  def eq(b: T, c: T): Boolean

object Eq:
  //def apply[A](using equa: Eq[A]) = equa
  def apply[A:Eq] = summon[Eq[A]]

  given Eq[Int] with
    def eq(b: Int, c: Int): Boolean = b == c
  given Eq[List[Int]] with
    def eq(b: List[Int], c: List[Int]): Boolean = b == c

def eq[E](eq1: E, eq2: E)(using equ: Eq[E]): Boolean = equ.eq(eq1, eq2)

@main def test =
/* DO NOT CHANGE */
  // 1
  val showInt1 = Show[Int].show(123)
  assert(showInt1 == "123")

  val showList1 = Show[List[Int]].show(List(1, 2, 3))
  assert(showList1 == "[1, 2, 3]")

  val showInt2 = summon[Show[Int]].show(123)
  assert(showInt2 == "123")
  
  val showList2 = summon[Show[List[Int]]].show(List(1, 2, 3))
  assert(showList2 == "[1, 2, 3]")

  // // // 2
  val eqInt1 = Eq[Int].eq(123, 123)
  assert(eqInt1 == true)

  val eqList1 = Eq[List[Int]].eq(List(1, 2, 3), List(1, 2, 3))
  assert(eqList1 == true)

  val eqInt2 = summon[Eq[Int]].eq(123, 123)
  assert(eqInt2 == true)

  val eqList2 = summon[Eq[List[Int]]].eq(List(1, 2, 3), List(1, 2, 3))
  assert(eqList2 == true)
/* DO NOT CHANGE */