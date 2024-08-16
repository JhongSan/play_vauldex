extension(s: String)
	def singleSpace: String = s.trim.split("\\s+").mkString(" ")

extension[T](xl: List[T])
	def tailOption: Option[List[T]] =
		if xl.nonEmpty then Some(xl.tail) else None

// extension(n: Int)
// 	def absOption: Option[Long] =
// 		if n != Int.MinValue then Some(n.abs) else Some(n.toLong.abs)
// 	def negateOption: Option[Long] =
// 		if n != Int.MinValue then Some(-n) else Some(-n.toLong)

trait TwosComplement[N]:
	def eqMinValue(n: N): Boolean
	def absOf(n: N): N
	def negationOf(n: N): N

object TwosComplement:
	given tcOfByte: TwosComplement[Byte] with
		def eqMinValue(n: Byte) = n == Byte.MinValue
		def absOf(n: Byte) = n.abs
		def negationOf(n: Byte) = (-n).toByte
	given tcOfShort: TwosComplement[Short] with
		def eqMinValue(n: Short) = n == Short.MinValue
		def absOf(n: Short) = n.abs
		def negationOf(n: Short) = (-n).toShort
	given tcOfInt: TwosComplement[Int] with
		def eqMinValue(n: Int) = n == Int.MinValue
		def absOf(n: Int) = n.abs
		def negationOf(n: Int) = (-n).toInt
	given tcOfLong: TwosComplement[Long] with
		def eqMinValue(n: Long) = n == Long.MinValue
		def absOf(n: Long) = n.abs
		def negationOf(n: Long) = (-n).toLong

extension[N](n: N)(using tc: TwosComplement[N])
	def isMinValue: Boolean = tc.eqMinValue(n)
	def absOption: Option[N] = if !isMinValue then Some(tc.absOf(n)) else None
	def negateOption: Option[N] = if !isMinValue then Some(tc.negationOf(n)) else None

trait Ord[T]:
	def compare(x: T, y: T): Int
	def lt(x: T, y: T): Boolean = compare(x, y) < 0
	def lteq(x: T, y: T): Boolean = compare(x, y) <= 0
	def gt(x: T, y: T): Boolean = compare(x, y) > 0
	def gteq(x: T, y: T): Boolean = compare(x, y) >= 0

	extension(lhs: T)
		def <(rhs: T): Boolean = lt(lhs, rhs)
		def <=(rhs: T): Boolean = lteq(lhs, rhs)
		def >(rhs: T): Boolean = gt(lhs, rhs)
		def >=(rhs: T): Boolean = gteq(lhs, rhs)

object Ord:
	given intOrd: Ord[Int] with
		def compare(x: Int, y: Int) = if x == y then 0 else if x > y then 1 else -1

def isort[T](xs: List[T])(using ord: Ord[T]): List[T] =
	if xs.isEmpty then Nil else insert(xs.head, isort(xs.tail))
def insert[T](x: T, xs: List[T])(using Ord[T]): List[T] =
	if xs.isEmpty || x <= xs.head then x :: xs else xs.head :: insert(x, xs.tail)

@main def test =
	println(singleSpace("Gwapoha \t na nimo \t ahaka"))
	println(List(1, 2, 3).tailOption)
	println(List.empty.tailOption)
	println(tailOption[String](List("Retardo", "Crisnel", "Sanchez")))
	println(Int.MaxValue.negateOption)	
	println(Int.MinValue.absOption)

	println(Byte.MaxValue.negateOption)
	println(Short.MinValue.absOption)
	println(-42.absOption)

	println(isort(List(2, 6, 9, 3)))