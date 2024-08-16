import sttp.client4.quick.*
import sttp.client4.Response
import ujson._

package Printer: 
    
    def pln(s:Any) =
        println(Console.YELLOW+"\n"+s+"\n"+Console.RESET)
    def p(s:Any) =
        println(Console.YELLOW+s+Console.RESET)

    def a(s: String): Unit = {
        def pln(s:Any) =
            println(Console.YELLOW+"\n"+s+"\n"+Console.RESET)
        val response: Response[String] = quickRequest
            .post(uri"https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=AIzaSyDkPjVMfhSkplYIqCDLew2hPrtjd34o1_c")
            .body(s"""{"contents":[{"parts":[{"text":"$s"}]}]}""")
            .send()
        val jsonResponse = ujson.read(response.body)
        val answerText = jsonResponse("candidates")(0)("content")("parts")(0)("text").str
        val cleanedAnswer = answerText
            .replaceAll("\\*\\*([^*]+)\\*\\*", "$1") 
            .replaceAll("\\*([^*]+)\\*", "$1") 
            .replaceAll("\n\n", "\n") 
            .replaceAll("- ", "") 

        pln(cleanedAnswer)
    }