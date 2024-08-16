// trait Numeric[N]:
// 	def sum(n: Seq[N]): N

// object Numeric:
// 	given Numeric[Int] with
// 		def sum(n: Seq[Int]) = n.sum
// 	given Numeric[Float] with
// 		def sum(n: Seq[Float]) = n.sum
// 	given Numeric[Long] with
// 		def sum(n: Seq[Long]) = n.sum
// 	given Numeric[Double] with
// 		def sum(n: Seq[Double]) = n.sum

// extension[N](num: Seq[N])(using ns: Numeric[N])
// 	def sumElements: N = ns.sum(num)

// trait Ordering[O]:
// 	def order(o: Seq[O]): O

// object Ordering:
// 	given Ordering[Int] with
// 		def order(o: Seq[Int]) = o.max
// 	given Ordering[String] with
// 		def order(o: Seq[String]) = o.max

// extension[O](str: Seq[O])(using me: Ordering[O])
// 	def maxElement: O = me.order(str)

// extension[F](fle: List[F])
// 	def filterElements(predicate: F => Boolean): List[F] = 
// 		fle match
// 			case Nil => Nil
// 			case _ => fle.filter(f => predicate(f))

// @main def test =
// 	val listInt: List[Int] = List(1, 2, 3, 4)
// 	println(listInt.sumElements) // Output: 10
// 	val listFloat: List[Float] = List(1.0f, 2.1f, 3.2f, 4.3f)
// 	println(listFloat.sumElements) // Output: 10
// 	val listDouble: List[Double] = List(1.04d, 2.13d, 3.22d, 4.31D)
// 	println(listDouble.sumElements) // Output: 10
// 	val listLong: List[Long] = List(10, 20, 30, 40)
// 	println(listLong.sumElements) // Output: 10

// 	val intList = List(1, 5, 3, 9, 2)
// 	println(intList.maxElement) // Output: 9
// 	val stringList = List("apple", "banana", "cherry")
// 	println(stringList.maxElement) // Output: cherry

// 	val filterIntList = List(1, 2, 3, 4, 5)
// 	println(filterIntList.filterElements(_% 2 == 0)) // Output: List(2, 4)
// 	val filterStringList = List("apple", "banana", "cherry")
// 	println(filterStringList.filterElements(_.startsWith("b"))) // Output: List("banana")

// trait Merger[T]:
// 	def merge(lst1: List[T], lst2: List[T]): List[T]

// object Merger:
// 	given Merger[Int] with
// 		def merge(lst1: List[Int], lst2: List[Int]): List[Int] =
// 			lst1.zip(lst2).map{
// 							case (a, b) => a + b
// 			}
// 	given Merger[String] with
// 		def merge(lst1: List[String], lst2: List[String]): List[String] =
// 			lst1.zip(lst2).map{
// 				case (a, b) => a ++ b
// 			}

// extension[T](value: List[T])(using mer: Merger[T])
// 	def mergeWith(m: List[T]): List[T] = mer.merge(value,m)

// @main def testMergeWith = 
//   val intList1 = List(1, 2, 3)
//   val intList2 = List(4, 5, 6)
//   val stringList1 = List("a", "b", "c")
//   val stringList2 = List("d", "e", "f")

//   println(intList1.mergeWith(intList2))
//   println(stringList1.mergeWith(stringList2))

//   assert(intList1.mergeWith(intList2) == List(5, 7, 9))
//   assert(stringList1.mergeWith(stringList2) == List("ad", "be", "cf"))


trait Flattener[F[_]]:
	def flattened[B](lst: F[F[B]]): F[B]

object Flattener:
	given Flattener[List] with
		def flattened[B](lst: List[List[B]]): List[B] = lst.flatten
	given Flattener[Some] with
		def flattened[B](lst: Some[Some[B]]): Some[B] =
			lst.match {
				case Some(a) => a
			}

extension[T[_], V](value: T[T[V]])(using flat: Flattener[T])
	def customFlatten: T[V] = flat.flattened(value)

@main def testCustomFlatten = {
  val nestedList = List(List(1, 2), List(3, 4))
  val nestedOption = Some(Some(5))

  println(nestedList.customFlatten)
  println(nestedOption.customFlatten)

  assert(nestedList.customFlatten == List(1, 2, 3, 4))
  assert(nestedOption.customFlatten == Some(5))
}