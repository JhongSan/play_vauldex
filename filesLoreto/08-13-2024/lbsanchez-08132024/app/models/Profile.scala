package models

import java.util.UUID

case class Profile(id: UUID, firstName: String, middleName: Option[String], lastName: String, age: Int)
object Profile {
  def apply(firstName: String, middleName: Option[String], lastName: String, age: Int): Profile = new Profile(UUID.randomUUID, firstName, middleName, lastName, age)
}