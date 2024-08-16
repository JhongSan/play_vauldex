// def maxList[T: Ordering]/*[T]*/(elements: List[T])/*(using ordering: Ordering[T])*/: T =
// 	elements match
// 		case List() => throw new IllegalArgumentException("Empty List")
// 		case List(x) => x
// 		case x :: rest =>
// 			val maxRest = maxList(rest)//(using ordering)
// 			if summon[Ordering[T]].lt(x, maxRest) then x else maxRest
// 			//if ordering.lt(x, maxRest) then x else maxRest

// def summon[T](using t: T) = t

// @main def test =
// 	println(maxList(List(2, 1, 3, 6, 4)))
// 	println(maxList(List("bc", "ad", "hq")))

// enum Mood:
// 	case Surprised, Angry, Neutral

// object Mood:
// 	import scala.util.CommandLineParser.FromString

// 	given moodFromString: FromString[Mood] with
// 		def fromString(s: String): Mood =
// 			s.trim.toLowerCase match {
// 				case "angry" => Mood.Angry
// 				case "surprised" => Mood.Surprised
// 				case "neutral" => Mood.Neutral
// 				case _ => throw new IllegalArgumentException(ermsg)
// 			}

// val ermsg = "Please enter a positive integer count, and\n" + "a mood (one of 'angry', 'surprised', or 'neutral')"

// @main def repeat(word: String, count: Int, mood: Mood) =
// 	val msg =
// 		if count > 0 then
// 			val words = List.fill(count)(word)
// 			val punc =
// 				mood match {
// 					case Mood.Angry => "!"
// 					case Mood.Surprised => "?"
// 					case Mood.Neutral => ""
// 				}
// 			val sep = punc + " "
// 			words.mkString(sep) + punc
// 		else ermsg
// 	println(msg)


// import scala.language.strictEquality

// // sealed trait CanEqual[-L, -R]

// // object CanEqual:
// // 	object derived extends CanEqual[Any, Any]

// case class Apple(size: Int) derives CanEqual

// // object Apple:
// // 	given canEq: CanEqual[Apple, Apple] = CanEqual.derived

// case class Orange(size: Int)

// @main def fruit =
// 	val appleTwo = Apple(2)
// 	val appleTwoToo = Apple(2)
// 	val orangeTwo = Orange(2)
// 	println(appleTwo == appleTwoToo)


// trait JsonSerializer[T]:
// 	def serialize(o: T): String

// 	extension(a: T)
// 		def toJson: String = serialize(a)

// object JsonSerializer:
// 	given stringSerializer: JsonSerializer[String] with
// 		def serialize(s: String) = s"\"$s\""
// 	given intSerializer: JsonSerializer[Int] with
// 		def serialize(n: Int) = n.toString
// 	given boolSerializer: JsonSerializer[Boolean] with
// 		def serialize(b: Boolean) = b.toString
// 	given listSerializer[T](using JsonSerializer[T]): JsonSerializer[List[T]] with
// 		def serialize(ts: List[T]) = s"[${ts.map(t => t.toJson).mkString(", ")}]" 

// object ToJsonMethods:
// 	extension[T](a: T)(using jser: JsonSerializer[T])
// 		def toJson: String = jser.serialize(a)

// case class Address(street: String, city: String, state: String, zip: Int)
// case class Phone(countryCode: Int, phoneNumber: Long)
// case class Contact(name: String, addresses: List[Address], phones: List[Phone])
// case class AddressBook(contacts: List[Contact])

// object Address:
// 	given addressSerializer: JsonSerializer[Address] with
// 		def serialize(a: Address) =
// 			import ToJsonMethods.{toJson}
// 			s"""{"street": ${a.street.toJson}, "city": ${a.city.toJson}, "state": ${a.state.toJson}, "zip": ${a.zip.toJson}}""".stripMargin

// object Phone:
// 	given phoneSerializer: JsonSerializer[Phone] with
// 		def serialize(p: Phone) =
// 			import ToJsonMethods.{toJson}
// 			s"""{"countryCode": ${p.countryCode.toJson}, "phoneNumber": ${p.phoneNumber.toJson}}""".stripMargin

// @main def json =
// 	import ToJsonMethods.toJson
// 	// println("tennis".toJson)
// 	// println(10.toJson)
// 	// println(false.toJson)

// 	val addressBook =
// 		AddressBook(
// 			List(
// 				Contact(
// 					"Bob Smith",
// 					List(Address("12345 Main Street", "San Francisco", "CA", 94105), Address("500 State Street", "Los Angeles", "MI", 90007)),
// 					List(Phone(1, 55588813), Phone(49, 4234782634))
// 				)
// 			)
// 		)
// 	println(addressBook.toJson)


trait Functor[F[_]]:
	def map[A, B](fa: F[A])(fb: A => B): F[B]

object Functor:
	given Functor[Option] with
		def map[A, B](fa: Option[A])(fb: A => B): Option[B] =
			fa match {
				case None => None
				case Some(a) => Some(fb(a))
			}

	given Functor[List] with
		def map[A, B](fa: List[A])(fb: A => B): List[B] =
			fa.map(fb)

extension[T[_], U](value: T[U])(using func: Functor[T])
	def functorForAll[B](f: U => B): T[B] = func.map(value)(f)

@main def m() =
	println(Option("a").functorForAll(x => x.concat("b")))
	println(Option(2).functorForAll(_ + 1))
	println(List("za", "yb", "xc").functorForAll(_.reverse))
	println(List(9, 8, 7, 6).functorForAll(_ - 1))