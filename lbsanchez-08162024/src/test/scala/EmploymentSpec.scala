import org.apache.pekko.actor.{ ActorSystem, Props, ActorRef }
import org.apache.pekko.testkit.{ TestKit, ImplicitSender, TestProbe }
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.BeforeAndAfterAll
import scala.concurrent.duration._

//import actor._
import domain._
import domain.Rating._

class EmploymentSpec extends TestKit(ActorSystem("employment"))
  with AnyWordSpecLike
  with ImplicitSender
  with BeforeAndAfterAll {

  val mockEmployee = TestProbe("employee")
  val employer = system.actorOf(Props(classOf[Employer], mockEmployee.ref), "employer")
  val employee = system.actorOf(Props(classOf[Employee]), "employee")
    
  override protected def afterAll(): Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "The Employer" should {
    // #1
    "send the employee the Work object when it receives a ProjectPlan" in {
      employer ! ProjectPlan
      mockEmployee.expectMsg(Work(self))
    }
    // #2
    "send the ProjectCompleted object when it receives WorkCompleted object" in {
      employer ! WorkCompleted(self)
      expectMsg(ProjectCompleted)
    }

    // #3
    "send the Employee a Bonus of 5000 when it receives the Excellent Rating" in {
      employer ! Excellent
      mockEmployee.expectMsg(Bonus(5000))
    }

    // #4
    "send the Employee a Bonus of 5000 when it receives the Good Rating" in {
      employer ! Good
      mockEmployee.expectMsg(Bonus(4000))
    }

    // #5
    "send the Employee a Bonus of 3000 when it receives the Average Rating" in {
      employer ! Average
      mockEmployee.expectMsg(Bonus(3000))
    }

    // #6
    "send the Employee a Memo when it receives the Bad Rating" in {
      employer ! Bad
      mockEmployee.expectMsg(Memo)
    }

    // #7
    "send the Employee a SalaryDeduction when it receives the NeverAgain Rating" in {
      employer ! NeverAgain
      mockEmployee.expectMsg(SalaryDeduction)
    }
  }

  "The Employee" should {
    // #8
    "send the Employer the WorkCompleted object with " in {
      employee ! Work(self)
      expectMsg(WorkCompleted(self))
    }

    // #9
    "send Gratitude when receiving a Bonus" in {
      employee ! Bonus(3000)
      expectMsg(Gratitude)
    }

    // #10
    "send Explanation when receiving a Memo" in {
      employee ! Memo
      expectMsg(Explanation)
    }

    // #11
    "send the ResignationLetter object when receiving the SalaryDeduction" in {
      employee ! SalaryDeduction
      expectMsg(ResignationLetter)
    } 
  }
}