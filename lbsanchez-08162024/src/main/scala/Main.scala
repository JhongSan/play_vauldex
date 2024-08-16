import org.apache.pekko
import pekko.actor.Actor
import pekko.actor.ActorSystem
import pekko.actor.Props
import pekko.actor.ActorRef
import pekko.pattern._
import scala.concurrent.duration._
import pekko.util.Timeout
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import domain.{ProjectPlan, Work, WorkCompleted, ProjectCompleted, Bonus, SalaryDeduction, ResignationLetter, Explanation, Memo, Gratitude}
import domain.Rating._

class Employer(work: ActorRef) extends Actor {
  def receive = {
    case ProjectPlan => work ! Work
    case WorkCompleted => ProjectCompleted
    case Excellent => work ! Bonus(5000)
    case Good => work ! Bonus(4000)
    case Average => work ! Bonus(3000)
    case Bad => work ! Bad
    case NeverAgain => work ! SalaryDeduction
  }
}

class Employee extends Actor {
  def receive = {
    case Work => sender() ! WorkCompleted
    case Bonus => sender() ! Gratitude
    case Memo => sender() ! Explanation
    case SalaryDeduction => sender() ! ResignationLetter
  }
}

@main def actor() =
  val system = ActorSystem("TaskSystem")
  val employee = system.actorOf(Props[Employee](), "employee")
  val employer = system.actorOf(Props(classOf[Employer], employee), "employer")

  employer ! ProjectPlan
