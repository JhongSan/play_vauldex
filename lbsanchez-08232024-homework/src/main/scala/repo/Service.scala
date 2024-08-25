package repo

import slick.jdbc.H2Profile.api._
import java.util.UUID
import domain._
import repo.Appointment
import scala.concurrent.Future

object Service {
    val services = TableQuery[Services]

    val createSchema = services.schema.create

    def getServicesByProviderId(serviceId: UUID): Future[Seq[Service]] = 
        db.run(services.filter(_.id === serviceId).result)
}
