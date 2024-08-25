package repo

import slick.jdbc.H2Profile.api._
import domain._

object ServiceProvider { 
  val serviceProviders = TableQuery[ServiceProviders]

  val createSchema = serviceProviders.schema.create
}
