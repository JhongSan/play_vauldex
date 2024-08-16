package domain

import org.apache.pekko.actor.{ ActorRef }

case object ProjectPlan
case object ProjectCompleted
case object Memo
case object SalaryDeduction
case object ResignationLetter
case object Gratitude
case object Explanation

case class Work(client: ActorRef)
case class WorkCompleted(client: ActorRef)
case class Bonus(value: Int)

enum Rating:
  case Excellent
  case Good
  case Average
  case Bad
  case NeverAgain


