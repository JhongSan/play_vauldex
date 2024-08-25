package repo

import slick.jdbc.H2Profile.api._
import domain._
import repo.Service._
import repo.Client._
import java.util.UUID
import java.time._
import java.sql.{Date, *}
import scala.concurrent.Future

object Appointment {
    val appointments = TableQuery[Appointments]

    val createSchema = appointments.schema.create

    def getAppointmentsByProviderAndDate(providerId: UUID, date: Instant): Future[Seq[(String, String, String, UUID, String)]] = {
        val query = for {
            a <- appointments if a.serviceId in services.filter(_.serviceProvider === providerId).map(_.id)
            if a.startAt === date
            c <- clients if c.id === a.clientId
            s <- services if s.id === a.serviceId
        } yield (c.firstName, c.lastName, c.contactNumber, s.id, s.name)
        db.run(query.result)
    }


    def countAppointmentsByProvider: Future[Seq[(UUID, Int)]] = {
        val query = services join appointments on { 
            case (service: Services, appointment: Appointments) => service.id === appointment.serviceId 
        } groupBy {
            case (service, appointment) => service.serviceProvider 
        } map {
            case (providerId, group) => (providerId, group.length)
        }

        db.run(query.result)
    }

    def getProviderWithMostAppointments: Future[Option[(java.util.UUID, Int)]] = {
        val query = services.join(appointments).on (_.id === _.serviceId)
            .groupBy(_._1.serviceProvider)
            .map { case (providerId, group) => (providerId, group.length) }
            .sortBy(_._2.desc)
            .result.headOption

        db.run(query)
    }

    def getTop10Services(): Future[Seq[(UUID, Option[String], Int)]] = {
        val query = services.join(appointments).on (_.id === _.serviceId)
            .groupBy(_._1.id)
            .map { case (serviceId, group) => (serviceId, group.map(_._1.name).max, group.length) }
            .sortBy(_._3.desc)
            .take(10)
        db.run(query.result)
    }


    def getUnfulfilledAppointments: Future[Seq[Appointment]] = {
        val query = appointments.filter(_.status =!= "fulfilled")
        db.run(query.result)
    }

}
