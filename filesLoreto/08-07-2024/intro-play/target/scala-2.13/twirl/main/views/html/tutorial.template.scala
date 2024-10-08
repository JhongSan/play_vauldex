
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

object tutorial extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("Hello World")/*3.21*/ {_display_(Seq[Any](format.raw/*3.23*/("""
"""),_display_(/*4.2*/defining(play.core.PlayVersion.current)/*4.41*/ { version =>_display_(Seq[Any](format.raw/*4.54*/("""

"""),format.raw/*6.1*/("""<section id="content">
  <div class="wrapper doc">
    <article>
      <h2>Implementing Hello World</h2>
      <p>This tutorial provides the instructions for using <a href="http://www.scala-sbt.org/" target="_blank"><code>sbt</code></a>
        (simple build tool) from a command window to build the application, but you can also integrate Play projects
        with your <a href="https://playframework.com/documentation/"""),_display_(/*12.69*/version),format.raw/*12.76*/("""/IDE" target="_blank">favorite IDE</a>.</p>
      <p> To see how simple it is to work with Play, let's add a customized "Hello World" greeting to this tutorial
        app. The main steps include:</p>
      <ul>
        <li><a href="#create">Create a new page</a></li>
        <li><a href="#action">Add an action method</a></li>
        <li><a href="#route">Define a route</a></li>
        <li><a href="#customize">Customize the greeting</a></li>
      </ul>

      <h3 id="create">Create the Hello World page</h3>
      <p>Follow these instructions to add a new page:</p>
      <ol>
        <li>With any text editor, create a file named <code>hello.scala.html</code> and save it in the <code>app/views</code>
          directory of this tutorial project.</li>
        <li>Add the following contents to the file:
          <pre><code class="language-html">&#64;main("Hello") """),format.raw/*28.63*/("""{"""),format.raw/*28.64*/("""
  """),format.raw/*29.3*/("""&lt;section id="content"&gt;
    &lt;div class="wrapper doc"&gt;
      &lt;article&gt;
        &lt;h1&gt;Hello World&lt;/h1&gt;
      &lt;/article&gt;  
      &lt;aside&gt;
        &#64;commonSidebar()
      &lt;/aside&gt;
    &lt;/div&gt;
  &lt;/section&gt;
"""),format.raw/*39.1*/("""}"""),format.raw/*39.2*/("""</code></pre>
          <p>The Twirl and HTML markup for your new page accomplishes the following:</p>
          <ul>
            <li>The <code>&#64;</code> sign tells the template engine to interpret what follows.
              <p>In this case, <code>&#64;main("Hello")</code> calls the main template, <code>main.scala.html</code>
                and passes it the page title of <code>Hello</code>.</p>
            </li>
            <li>The <code>content</code> section contains the <code>Hello World</code> greeting. The main template will
              insert this into the body of the page.</li>
            <li>The <code>&lt;aside&gt;</code> section adds the TOC to the right side so that you will be able to
              navigate back to this page.
          </ul>
      </ol>
      <h3 id="action">Add an action method</h3>
      <p>Next, add an action method that will render the new page. To keep things simple, you will add the new
        controller to the existing class. In a real application, you can organize controllers in multiple classes if
        you wish.</p>
      <p>Open the <code>app/controllers/HomeController.scala</code> file and add the following method:
        <pre><code class="language-scala">def hello() = Action """),format.raw/*57.64*/("""{"""),format.raw/*57.65*/(""" """),format.raw/*57.66*/("""implicit request: Request[AnyContent] =>
  Ok(views.html.hello())
"""),format.raw/*59.1*/("""}"""),format.raw/*59.2*/("""</code></pre>
      </p>
      <p>This method has no input parameters and simply renders the new <code>hello</code> page.</p>

      <h3 id="route">Define a route</h3>

      <p>A <code>route</code> tells Play how to handle incoming requests and includes the request path, an HTTP
        method, and the controller action to invoke. When you add a route to the <code>routes</code> file, Play's
        routes compiler will automatically generate a router class that calls that action using an instance of that
        controller. For more information see <a href="https://www.playframework.com/documentation/"""),_display_(/*68.100*/version),format.raw/*68.107*/("""/ScalaRouting#HTTP-routing"
          target="blank">HTTP Routing</a>. By default, the controller instances are created using dependency
        injection. See <a href="https://www.playframework.com/documentation/"""),_display_(/*70.78*/version),format.raw/*70.85*/("""/ScalaDependencyInjection#Dependency-Injection"
          target="blank">Dependency Injection</a> for more information.</p>
      <p>To define a route for the new page:</p>
      <ol>
        <li>Open the <code>conf/routes</code> file.</li>
        <li>Below the <code>tutorial</code> page route, add the following line:
          <p><code>GET     /hello      controllers.HomeController.hello</code></p>
        </li>
      </ol>
      <p>Test the new page:</p>
      <ol>
        <li>If you stopped the application for some reason, restart it with the <code>sbt run</code> command.</li>
        <li>Enter the URL <a href="http://localhost:9000/hello">http://localhost:9000/hello</a> to view the results
          of your work. The browser should respond with something like the following:
          <p><img src="assets/images/hello-one.png" alt="Add Request and response screen" class="small-5 medium-4 large-3" /></p>
        </li>
      </ol>

      <h3 id="customize">Customize the greeting</h3>

      <p>As the final part of this tutorial, we'll modify the hello page to accept an HTTP request parameter that
        passes in a name. The steps include a deliberate mistake to demonstrate how Play provides useful feedback.</p>
      <p>To customize the Hello World greeting, follow these steps:</p>
      <ol>
        <li>In the <code>app/controllers/HomeController.scala</code> file, modify the <code>hello</code> action
          method to accept a <code>name: String</code> parameter. The modified action should look like the
          following:
          <pre><code class="language-scala">def hello(name: String) = Action """),format.raw/*97.78*/("""{"""),format.raw/*97.79*/("""
  """),format.raw/*98.3*/("""Ok(views.html.hello())
"""),format.raw/*99.1*/("""}"""),format.raw/*99.2*/("""</code></pre>
        </li>
        <li>In the <code>conf/routes</code> file, add a <code>(name: String)</code> parameter at the end of the
          <code>/hello</code> route:
          <p><code>GET  /hello        controllers.HomeController.hello(name: String)</code></p>
        </li>
        <li>In Twirl templates, all variables and their types must be declared. From the <code>app/views/</code>
          directory, open the <code>hello.scala.html</code> file and do the following:
          <ul>
            <li>Insert a new line at the top of the file.</li>
            <li>On that line, add an <code>&#64;</code> directive that declares the name parameter and its type:
              <code>&#64;(name: String)</code>.</li>
            <li>To use the variable on the page, change the text in the <code>&lt;h2&gt;</code> heading from <code>Hello World</code>
              to <code>&lt;h2&gt;Hello &#64;name!&lt;/h2&gt;</code>.
            </li>
          </ul>
          <p>To test the cusomization:</p>
          <ol>
            <li>Open a new browser tab</li>
            <li>Enter the following URL and pass in any name as a query parameter to the hello method: <a target="play-docs"
                href="http://localhost:9000/hello?name=MyName">http://localhost:9000/hello?name=MyName</a>.
              <p>Play responds with a helpful compilation error that tells you the file and line number causing the
                problem. The message shows that the render method in the return value requires a typed parameter:
              </p>
              <p><img src="assets/images/compilation-error.png" alt="Error message" class="small-5 medium-4 large-3" /></p>
            </li>
          </ol>
        <li>
          <p>To fix the compilation error, modify the <code>hello</code> action method in <code>HomeController</code>
            so that the it includes the <code>name</code> parameter when rendering the view:</p>
          <pre><code class="language-scala">def hello(name: String) = Action """),format.raw/*129.78*/("""{"""),format.raw/*129.79*/("""
  """),format.raw/*130.3*/("""Ok(views.html.hello(name))
"""),format.raw/*131.1*/("""}"""),format.raw/*131.2*/("""</code></pre>
        </li>
        <li>
          <p>Save the file and refresh the browser. Play detects the change, automatically recompiles, and reloads
            the page. The page should display a customized greeting similar to the following:</p>
          <p><img src="assets/images/hello-custom.png" alt="Hello Malitha" class="small-5 medium-4 large-3" /></p>
        </li>
      </ol>

      <h3>Summary</h3>
      <p>Thanks for trying our tutorial. You learned how to use an action method, routes, Twirl template, and
        input parameter to create a customized Hello World greeting! You experienced how template compilation
        makes it easier to identify and fix problems and how auto-reloading saves time.
      </p>
      <h3>Next steps</h3>
      <p>To learn more about Play, check out these resources:</p>
      <ul>
        <li>Documentation: <a href="https://www.playframework.com/documentation/"""),_display_(/*148.82*/version),format.raw/*148.89*/("""/ScalaHome" target="_blank">Main
            concepts for Scala</a></li>
        <li><a href="https://github.com/playframework/play-samples/" target="_blank">Play Example Apps</a>,
          just clone or download zip and run.</li>
        <li>Podcast: <a href="https://www.lightbend.com/blog/why-is-play-framework-so-fast"
            target="_blank">What makes Play Framework so fast?</a></li>
      </ul>
    </article>
    <aside>
      """),_display_(/*157.8*/commonSidebar()),format.raw/*157.23*/("""
    """),format.raw/*158.5*/("""</aside>
  </div>
</section>
""")))}),format.raw/*161.2*/("""
""")))}),format.raw/*162.2*/("""
"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/tutorial.scala.html
                  HASH: e3da0c135df5aa854d77ed3856d35674d76db7e9
                  MATRIX: 725->1|821->4|848->6|875->25|914->27|941->29|988->68|1038->81|1066->83|1515->505|1543->512|2446->1387|2475->1388|2505->1391|2791->1650|2819->1651|4095->2899|4124->2900|4153->2901|4246->2967|4274->2968|4912->3578|4941->3585|5182->3799|5210->3806|6872->5440|6901->5441|6931->5444|6981->5467|7009->5468|9053->7483|9083->7484|9114->7487|9169->7514|9198->7515|10148->8437|10177->8444|10646->8886|10683->8901|10716->8906|10777->8936|10810->8938
                  LINES: 21->1|26->2|27->3|27->3|27->3|28->4|28->4|28->4|30->6|36->12|36->12|52->28|52->28|53->29|63->39|63->39|81->57|81->57|81->57|83->59|83->59|92->68|92->68|94->70|94->70|121->97|121->97|122->98|123->99|123->99|153->129|153->129|154->130|155->131|155->131|172->148|172->148|181->157|181->157|182->158|185->161|186->162
                  -- GENERATED --
              */
          