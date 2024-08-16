package models

import java.util.UUID

case class User(id: UUID, email: String, username: String, password: String)
object User {
  def apply(email: String, username: String, password: String): User = new User(UUID.randomUUID, email, username, password)
}