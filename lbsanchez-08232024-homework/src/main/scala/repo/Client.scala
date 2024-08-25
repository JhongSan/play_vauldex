package repo

import slick.jdbc.H2Profile.api._
import domain._

object Client {
    val clients = TableQuery[Clients]

    val createSchema = clients.schema.create
}
