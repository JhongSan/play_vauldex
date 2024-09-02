import java.util.UUID

trait JsonSerializable[A]:
  def toJson(value: A): String

class Profile(val id: UUID, val name: String, val age: Int)

object Profile:
  def apply(name: String, age: Int): Profile = new Profile(UUID.randomUUID, name, age)

case class User(email: String, username: String, idProfile: UUID)

object JsonSerializer:
  def toJson[A](value: A)(using serializer: JsonSerializable[A]): String = serializer.toJson(value)

given JsonSerializable[Profile] with
  def toJson(profile: Profile): String =
    s"""{"id": "${profile.id}","name": "${profile.name}", "age": ${profile.age}}"""

given JsonSerializable[User] with
  def toJson(user: User): String = 
    s"""{"email": "${user.email}", "username": "${user.username}", "id_profile": ${user.idProfile}}"""

case class Kilogram(value: Double)
case class Gram(value: Double)

given Conversion[Kilogram, Gram] with
  def apply(kg: Kilogram): Gram = Gram(kg.value * 1000)
given Conversion[Gram, Kilogram] with
  def apply(g: Gram): Kilogram = Kilogram(g.value / 1000)

case class Money(value: Double, currency: Currency)

enum Currency:
  case USD, EUR, GBP

def convertMoney(amount: Money, currency: Currency): Money =
  (amount.currency, currency).match{
    case (Currency.USD, Currency.EUR) => Money(amount.value * 0.85, currency)
    case (Currency.USD, Currency.GBP) => Money(amount.value * 0.73, currency)
    case (Currency.EUR, Currency.USD) => Money(amount.value * 1.18, currency)
    case (Currency.EUR, Currency.GBP) => Money(amount.value * 0.86, currency)
    case (Currency.GBP, Currency.USD) => Money(amount.value * 1.37, currency)
    case (Currency.GBP, Currency.EUR) => Money(amount.value * 1.16, currency)
    case (_, _) => Money(amount.value, currency)
  }

// DO NOT CHANGE
@main def test =
  // 1
  val johnProfile = Profile("John Doe", 30)
  val johnUser = User("jdoe@gmail.com", "jdoe", johnProfile.id)

  val obtainedSerializedProfile = JsonSerializer.toJson(johnProfile)
  val expectedSerilizedProfile = s"""{"id": "${johnProfile.id}","name": "John Doe", "age": 30}"""
  println(s"obtained: $obtainedSerializedProfile")
  println(s"expected: $expectedSerilizedProfile")
  assert(obtainedSerializedProfile == expectedSerilizedProfile)

  val obtainedSerializedUser = JsonSerializer.toJson(johnUser)
  val expectedSerilizedUser = s"""{"email": "jdoe@gmail.com", "username": "jdoe", "id_profile": ${johnProfile.id}}"""
  println(s"obtained: $obtainedSerializedUser")
  println(s"expected: $expectedSerilizedUser")
  assert(obtainedSerializedUser == expectedSerilizedUser)

  // 2
  val testCases1: List[(Kilogram, Gram)] = List(
    (Kilogram(1.0), Gram(1000.0)), // (argument value, expected result value)
    (Kilogram(1.6), Gram(1600.0)),
    (Kilogram(2.89), Gram(2890.0))
  )

  val testCases2: List[(Gram, Kilogram)] = List(
    (Gram(100.0), Kilogram(0.1)), // (argument value, expected result value)
    (Gram(3.0), Kilogram(0.003)),
    (Gram(19.8), Kilogram(0.0198))
  )
  
  testCases1.foreach(t => {
    val obtained: Gram = t._1
    val expected = t._2
    println(s"obtained: $obtained")
    println(s"expected: $expected")
    assert(obtained == expected)
  })

  testCases2.foreach(t => {
    val obtained: Kilogram = t._1
    val expected = t._2
    println(s"obtained: $obtained")
    println(s"expected: $expected")
    assert(obtained == expected)
  })

  // 3 & 4
  val amountInUSD = Money(100.0, Currency.USD)
  val amountInEUR = convertMoney(amountInUSD, Currency.EUR)
  val amountInGBP = convertMoney(amountInUSD, Currency.GBP)

  println(s"$amountInUSD is equivalent to $amountInEUR")
  println(s"$amountInUSD is equivalent to $amountInGBP")