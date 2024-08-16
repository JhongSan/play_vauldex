
package views.html.yeah

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

object loreto extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(mf: String):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*2.1*/("""
"""),_display_(/*3.2*/main("Wazzup")/*3.16*/ {_display_(Seq[Any](format.raw/*3.18*/("""
  """),format.raw/*4.3*/("""<section id="content">
    <div class="wrapper doc">
      <article>
        <h1>The game is on! """),_display_(/*7.30*/mf),format.raw/*7.32*/("""</h1>
      </article>  
      <aside>
        """),_display_(/*10.10*/commonSidebar()),format.raw/*10.25*/("""
      """),format.raw/*11.7*/("""</aside>
    </div>
  </section>
""")))}))
      }
    }
  }

  def render(mf:String): play.twirl.api.HtmlFormat.Appendable = apply(mf)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (mf) => apply(mf)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  SOURCE: app/views/yeah/loreto.scala.html
                  HASH: 192398ca75a1afd893531f2a7268c175182b0e6a
                  MATRIX: 735->1|841->14|868->16|890->30|929->32|958->35|1082->133|1104->135|1179->183|1215->198|1249->205
                  LINES: 21->1|26->2|27->3|27->3|27->3|28->4|31->7|31->7|34->10|34->10|35->11
                  -- GENERATED --
              */
          