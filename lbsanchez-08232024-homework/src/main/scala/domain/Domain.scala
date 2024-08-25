package domain

import slick.jdbc.H2Profile.api._
import java.util.UUID
import java.time._ 
import java.time._

final case class ServiceProvider(
    id: UUID,
    name: String
)
final case class Service(
    id: UUID,
    name: String,
    serviceProvider: UUID
)
final case class Client(
    id: UUID,
    firstName: String,
    lastName: String,
    address: String,
    contactNumber: String
)
final case class Appointment(
    id: UUID,
    startAt: Instant,
    endAt: Instant,
    price: Double,
    status: String,
    serviceId: UUID,
    clientId: UUID
)

final class ServiceProviders(tag: Tag) extends Table[ServiceProvider](tag, "message") {
    def id = column[UUID]("id", O.PrimaryKey)
    def name = column[String]("name")

    def * = (id, name).mapTo[ServiceProvider]
}
final class Services(tag: Tag) extends Table[Service](tag, "message") {
    def id = column[UUID]("id", O.PrimaryKey)
    def name = column[String]("name")
    def serviceProvider = column[UUID]("serviceProvider")

    def * = (id, name, serviceProvider).mapTo[Service]
}
final class Clients(tag: Tag) extends Table[Client](tag, "message") {
    def id = column[UUID]("id", O.PrimaryKey)
    def firstName = column[String]("firstName")
    def lastName = column[String]("lastName")
    def address = column[String]("address")
    def contactNumber = column[String]("contactNumber")

    def * = (id, firstName, lastName, address, contactNumber).mapTo[Client]
}
final class Appointments(tag: Tag) extends Table[Appointment](tag, "message") {
    def id = column[UUID]("id", O.PrimaryKey)
    def startAt = column[Instant]("startAt")
    def endAt = column[Instant]("endAt")
    def price = column[Double]("price")
    def status = column[String]("status")
    def serviceId = column[UUID]("serviceId")
    def clientId = column[UUID]("clientId")

    def * = (id, startAt, endAt, price, status, serviceId, clientId).mapTo[Appointment]
}

val db = Database.forConfig("lbsanchez")