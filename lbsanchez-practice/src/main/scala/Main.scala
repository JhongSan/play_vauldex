import slick.jdbc.H2Profile.api._
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._

import domain._
import repo._
import scala.languageFeature.reflectiveCalls

def transformProfileToString = (profile: Profile) => s"${profile.firstName} ${profile.lastName} (${profile.gender})"
def transformAccountToString = (account: Account) => s"${account.email}"

@main def Slick() = 
  val db = Database.forConfig("lbsanchez")
  def exec[T](program: DBIO[T]): T = Await.result(db.run(program), 2.seconds)

  val account1ID = UUID.fromString("ef49bdf8-d9b8-40bf-9bc6-38fb3cd755a5")
  val account2ID = UUID.fromString("23ac66c0-f773-44fd-9990-6a01f561b89a")

  for {
    _ <- AccountRepo.createSchema // #2.1
    _ <- AccountRepo.add(Account(account1ID, "email1")) //#2.3
    account <- AccountRepo.findById(account1ID) // #2.5
    account2 <- AccountRepo.add(Account(account2ID, "email2"))
    accounts <- AccountRepo.get // #2.8
  } yield {
    println(s"accounts schema created & populated: [${accounts.map(transformAccountToString).mkString(", ")}]")
  }
  for {
    _ <- ProfileRepo.createSchema // #2.2
    _ <- ProfileRepo.add(Profile(account1ID, "firstName1", "lastName1", 'M')) //#2.4
    _ <- ProfileRepo.add(Profile(account2ID, "firstName2", "lastName2", 'F'))
    profiles <- ProfileRepo.get // #2.9
    profile <- ProfileRepo.findById(account1ID) // #2.6
    profilesByGender <- ProfileRepo.filterByGender('M') // #2.7
  } yield {
    println(s"profile schema created & populated: [${profiles.map(transformProfileToString).mkString(", ")}]") 
    if (profile.isEmpty) {
      println(s"account 1 profile not found")
    } else {
      transformProfileToString(profile.get)
    }
    println(s"profiles by gender: ${profilesByGender.map(transformProfileToString).mkString(", ")}") 
  }

  println(exec(AccountRepo.createSchema))
  exec(AccountRepo.accounts += Account(account2ID, "email2"))
  println(exec(AccountRepo.accounts.result))
  println(exec(AccountRepo.accounts.filter(_.id === account1ID).result))
