package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import java.util.UUID
import java.util.UUID._
import play.api.libs.json._
import scala.concurrent.ExecutionContext
import models.repo._
import models.domain._

@Singleton
class ContactController @Inject()(cc: ControllerComponents, contactRepo: ContactRepo)(implicit ec: ExecutionContext) 
		extends AbstractController(cc){
	def listContacts(userId: UUID) = Action.async { implicit request =>
		contactRepo.get(userId).map{ contact =>
			Ok(Json.toJson(contact))
		}
	}

	def createContact() = Action.async(parse.json) { implicit request =>
		val json = request.body
		val userId = fromString((json \ "userId").as[String])
		val firstName = (json \ "firstName").asOpt[String]
		val middleName = (json \ "middleName").asOpt[String]
		val lastName = (json \ "lastName").asOpt[String]
		val phoneNumber = (json \ "phoneNumber").asOpt[String]
		val email = (json \ "email").asOpt[String]
		val newContact = Contact(randomUUID(), userId, firstName, middleName, lastName, phoneNumber, email)

		contactRepo.add(newContact).map{ _ =>
			Ok(Json.obj("status" -> "success", "message" -> "Contact created."))
		}
	}

	def deleteContact(id: UUID) = Action.async { implicit request =>
		contactRepo.delete(id).map{ _ =>
			Ok(Json.obj("status" -> "success", "message" -> "Contact deleted."))
		}
	}

	def updateContact() = Action.async(parse.json) { implicit request =>
		val json = request.body
		val id = fromString((json \ "id").as[String])
		val userId = fromString((json \ "userId").as[String])
		val firstName = (json \ "firstName").asOpt[String]
		val middleName = (json \ "middleName").asOpt[String]
		val lastName = (json \ "lastName").asOpt[String]
		val phoneNumber = (json \ "phoneNumber").asOpt[String]
		val email = (json \ "email").asOpt[String]
		val newContact = Contact(id, userId, firstName, middleName, lastName, phoneNumber, email)

		contactRepo.update(newContact).map{_ =>
			Ok(Json.obj("status" -> "success", "message" -> "Contact updated."))
		}
	}
}

