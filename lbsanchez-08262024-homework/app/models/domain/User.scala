package models.domain

import java.util.UUID

case class User(
    id: UUID, 
    username: String, 
    password: String, 
    email: String)
