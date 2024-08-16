def fibFrom(a: Int, b: Int): LazyList[Int] =
	a #:: fibFrom(b, a + b)

@main def test =
	println(fibFrom(1, 1).take(10).toList)