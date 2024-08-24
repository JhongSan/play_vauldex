package  repo

import slick.jdbc.H2Profile.api._
import domain._
import java.util.UUID
import scala.concurrent.{Await, Future}

object AccountRepo {
    val accounts = TableQuery[Accounts]

    val createSchema = /*accounts.schema.dropIfExists andThen*/ accounts.schema.create

    def add(acct: Account): DBIO[Int] = accounts += acct

    def findById(acctId: UUID) = accounts.filter(_.id === acctId).result

    def get = accounts.result
}
