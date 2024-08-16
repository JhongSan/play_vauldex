
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

object commonSidebar extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](_display_(/*2.2*/defining(play.core.PlayVersion.current)/*2.41*/ { version =>_display_(Seq[Any](format.raw/*2.54*/("""
"""),format.raw/*3.1*/("""<h3>Table of Contents</h3>
<ul>
  <li><a href=""""),_display_(/*5.17*/routes/*5.23*/.HomeController.index()),format.raw/*5.46*/("""#Introduction">Welcome</a>
  <li><a href=""""),_display_(/*6.17*/routes/*6.23*/.HomeController.explore()),format.raw/*6.48*/("""">Play application overview</a>
  <li><a href=""""),_display_(/*7.17*/routes/*7.23*/.HomeController.tutorial()),format.raw/*7.49*/("""">Implementing Hello World</a>
</ul>
<h3>Related Resources</h3>
<ul>
  <li><a href="https://playframework.com/documentation/"""),_display_(/*11.57*/version),format.raw/*11.64*/("""" target="_blank">Play documentation</a></li>
  <li><a href="https://github.com/playframework/playframework/discussions" target="_blank">Forum</a></li>
  <li><a href="https://discord.gg/g5s2vtZ4Fa" target="_blank">Play Discord Server</a></li>
  <li><a href="https://stackoverflow.com/questions/tagged/playframework" target="_blank">Stackoverflow</a></li>
  <li><a href="https://www.playframework.com/sponsors" target="_blank">Sponsor Play</a></li>
</ul>
""")))}),format.raw/*17.2*/("""
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
                  SOURCE: app/views/commonSidebar.scala.html
                  HASH: 6e0f53fbacdbdc6075d2c6cd75940a300860e75f
                  MATRIX: 730->1|826->5|873->44|923->57|950->58|1024->106|1038->112|1081->135|1150->178|1164->184|1209->209|1283->257|1297->263|1343->289|1495->414|1523->421|2008->876
                  LINES: 21->1|26->2|26->2|26->2|27->3|29->5|29->5|29->5|30->6|30->6|30->6|31->7|31->7|31->7|35->11|35->11|41->17
                  -- GENERATED --
              */
          