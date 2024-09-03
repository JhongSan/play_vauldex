package models.repo

import javax.inject._
import java.util.UUID
import scala.concurrent.Future
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.PostgresProfile
import models.domain._
import models.repo._

@Singleton
class ContactRepo @Inject()(dbcp: DatabaseConfigProvider, val userRepo: UserRepo){
	val dbConfig = dbcp.get[PostgresProfile]
	import dbConfig._
	import profile.api._

	class ContactTable(tag: Tag) extends Table[Contact](tag, "CONTACT"){
		def id = column[UUID]("ID", O.PrimaryKey)
		def userId = column[UUID]("USER_ID")
		def firstName = column[Option[String]]("FIRST_NAME")
		def middleName = column[Option[String]]("MIDDLE_NAME")
		def lastName = column[Option[String]]("LAST_NAME")
		def phoneNumber = column[Option[String]]("PHONE_NUMBER")
		def email = column[Option[String]]("EMAIL")

		def fk = foreignKey("USER_ID_FK", userId, userRepo.users)(_.id)
		def * = (id, userId, firstName, middleName, lastName, phoneNumber, email).mapTo[Contact]
	}

	val contacts = TableQuery[ContactTable]

	def get(userId: UUID): Future[Seq[Contact]] =
		db.run(contacts.filter(_.userId === userId).result)

	def add(contact: Contact): Future[Int] =
		db.run(contacts += contact)

	def delete(id: UUID): Future[Int] =
		db.run(contacts.filter(_.id === id).delete)

	def update(contact: Contact): Future[Int] =
		db.run(contacts.filter(_.id === contact.id).update(contact))
}



