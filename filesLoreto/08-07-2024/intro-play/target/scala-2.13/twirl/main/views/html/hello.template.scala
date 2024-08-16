
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

object hello extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(name: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("Hello")/*3.15*/ {_display_(Seq[Any](format.raw/*3.17*/("""
  """),format.raw/*4.3*/("""<section id="content">
    <div class="wrapper doc">
      <article>
        <h1>Surprise Mother!Father! """),_display_(/*7.38*/name),format.raw/*7.42*/("""</h1>
      </article>  
      <aside>
        """),_display_(/*10.10*/commonSidebar()),format.raw/*10.25*/("""
      """),format.raw/*11.7*/("""</aside>
    </div>
  </section>
""")))}),format.raw/*14.2*/("""
"""))
      }
    }
  }

  def render(name:String): play.twirl.api.HtmlFormat.Appendable = apply(name)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (name) => apply(name)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/hello.scala.html
                  HASH: 682ea4dd524cbb5ed6a30ad329d96902c87e2220
                  MATRIX: 729->1|837->16|864->18|885->31|924->33|953->36|1085->142|1109->146|1184->194|1220->209|1254->216|1318->250
                  LINES: 21->1|26->2|27->3|27->3|27->3|28->4|31->7|31->7|34->10|34->10|35->11|38->14
                  -- GENERATED --
              */
          