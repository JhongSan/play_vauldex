package models.domain

import java.util.UUID
import java.time.LocalDate

case class Profile(
  id: UUID,
  userId: UUID,
  firstName: String,
  middleName: Option[String],
  lastName: String,
  birthDate: LocalDate,
  livingCountry: String,
  firstLanguage: String,
  secondLanguage: Option[String]
)
