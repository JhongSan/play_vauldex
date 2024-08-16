/*
Instructions:
  - Fulfill the requirements below.
  - Create a scala file and name it as <yourid>-08022024
  - Fulfill the requirements below
*/

// #1
case class Book(title: String, author: String, genre: String, price: Double)

val books: List[Book] = List(
	  Book("Ejisan's Adventures", "Ryo Ejisan", "Fantasy", 10.99),
	  Book("To Kill a Mockingbird", "Harper Lee", "Classic", 10.99),
	  Book("1984", "George Orwell", "Dystopian", 9.99),
	  Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy", 12.99),
	  Book("The Hobbit", "J.R.R. Tolkien", "Fantasy", 11.99),
	  Book("Brave New World", "Aldous Huxley", "Dystopian", 8.99),
	  Book("Moby-Dick", "Herman Melville", "Classic", 7.99),
	  Book("One Hundred Years of Solitude", "Gabriel Garcia Marquez", "Magic Realism", 13.99),
	  Book("Crime and Punishment", "Fyodor Dostoevsky", "Classic", 14.99))

def oneOne = books.filter(_.price < 10)
def oneTwo = books.filter(_.genre == "Classic").map(_.title)
def oneThree = books.groupBy(_.genre)
def oneFour = books.map(_.price).sum / books.size
def oneFive = books.maxBy(_.price)
def oneSix = oneThree.view.mapValues(_.sortBy(_.price)).toMap
def oneSeven = oneThree.view.mapValues(m => m.map(_.price).sum / m.size).toMap
def oneEight = books.groupBy(_.author).view.mapValues(m => m.map(_.price).sum / m.size).toList.sortBy(_._2).reverse
def oneNine = oneThree.view.mapValues(_.groupBy(_.author).view.mapValues(_.map(_.title).toList).toMap).toMap
def oneTen = books.map(_.title.split(" ").toList).map(_.last).sorted

// 1.1. Find all the books that cost less than $10.
// 1.2. Find the titles of all books that are "Classic".
// 1.3. Group the books by genre.
// 1.4. Calculate the average price of all books.
// 1.5. Find the most expensive book.
// 1.6. Group the books by genre, and within each genre, sort the books by price in ascending order.
// 1.7. Find the total price of all books for each genre.
// 1.8. Find the average price of books for each author, sorted by average price in descending order.
// 1.9. Transform the dataset into a Map[String, Map[String, List[String]]], where the first key is the genre, the second key is the author, and the list contains titles.
// 1.10. Create a list of all titles, sorted by the last word in the title.

// #2
/*
Imagine you're building a system to model and track the activities of a university.
You have to track students, their courses, and grades for each course. Your task is to calculate:
The average grade for each course.
The average grade for each student.
The top-performing student in each course.
The overall top-performing student.
The worst-performing student in each course.
The overall worst-performing student based on average grades.
The most challenging course, i.e., the course with the lowest average grade.
The easiest course, i.e., the course with the highest average grade.
- A grade is a simple integer between 0 and 100
- Answer them returning methods on the bottom
- No loop, mutable state, no internet (except for API references)
*/

case class Student(id: Int, name: String)
case class Course(id: Int, name: String)
case class Enrollment(studentId: Int, courseId: Int, grade: Int):
  require(0 <= grade && grade <= 100)

val students = List(
  Student(1, "Alice"),
  Student(2, "Bob"),
  Student(3, "Catherine"))

val courses = List(
  Course(1, "Math"),
  Course(2, "Science"),
  Course(3, "History"))

val enrollments = List(
  Enrollment(1, 1, 85),
  Enrollment(1, 2, 91),
  Enrollment(1, 3, 76),
  Enrollment(2, 1, 78),
  Enrollment(2, 2, 87),
  Enrollment(3, 1, 92),
  Enrollment(3, 3, 89))

// 2.1. The average grade for each course
def avgGradePerCourse = enrollments.groupBy(_.courseId).map{
	(course, courseEnrollment) =>
		val courseName = courses.find(_.id == course).map(_.name).getOrElse("Unknown")
		val avgGrade = courseEnrollment.map(_.grade).sum / courseEnrollment.size
		courseName -> avgGrade
}
// 2.2. The average grade for each student
def avgGradePerStudent = enrollments.groupBy(_.studentId).map{
	(student, studentEnrollment) =>
		val studentName = students.find(_.id == student).map(_.name).getOrElse("Unknown")
		val avgGrade = studentEnrollment.map(_.grade).sum / studentEnrollment.size
		studentName -> avgGrade
}
// 2.3. The top-performing student in each course
def topStudentPerCourse = enrollments.groupBy(_.courseId).map{
	(course, student) =>
		val courseName = courses.find(_.id == course).map(_.name).getOrElse("Unknown")
		val studentName = students.find(_.id == student.maxBy(_.grade).studentId).map(_.name).getOrElse("Unknown")
		courseName -> studentName
}
// 2.4. The overall top-performing student
def overallTopStudent = avgGradePerStudent.max._1
// 2.5. The worst-performing student in each course
def worstStudentPerCourse = enrollments.groupBy(_.courseId).map{
	(course, student) =>
		val courseName = courses.find(_.id == course).map(_.name).getOrElse("Unknown")
		val studentName = students.find(_.id == student.minBy(_.grade).studentId).map(_.name).getOrElse("Unknown")
		courseName -> studentName
}
// 2.6. The overall worst-performing student based on average grades
def overallWorstStudent = avgGradePerStudent.min._1
// 2.7. The most challenging course (course with the lowest average grade)
def mostChallengingCourse = avgGradePerCourse.min._1
// 2.8. The easiest course (course with the highest average grade)
def easiestCourse = avgGradePerCourse.max._1

@main def test = 
	println(oneOne) //1.1
	println(oneTwo) //1.2
	println(oneThree) //1.3
	println(oneFour) //1.4
	println(oneFive) //1.5
	println(oneSix) //1.6
	println(oneSeven) //1.7
	println(oneEight) //1.8
	println(oneNine) //1.9
	println(oneTen) //1.10

	println(avgGradePerCourse) //2.1
	println(avgGradePerStudent) //2.2
	println(topStudentPerCourse) //2.3
	println(overallTopStudent) //2.4
	println(worstStudentPerCourse) //2.5
	println(overallWorstStudent) //2.6
	println(mostChallengingCourse) //2.7
	println(easiestCourse) //2.8