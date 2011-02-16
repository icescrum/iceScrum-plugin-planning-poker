package org.icescrum.plugins.planningPoker
import org.icescrum.components.UtilsWebComponents
class PlanningPokerTagLib {
  static namespace = 'is'

  def planningPokerStoryList = { attrs, body ->
    def jqCode=""
    if (attrs.selectable) {
      def selectableOptions = [
              filter: UtilsWebComponents.wrap(attr: (attrs.selectable.filter), doubleQuote: true),
              cancel: UtilsWebComponents.wrap(attrs.selectable.cancel),
              selected: "function(event,ui){${attrs.selectable.selected}}",
              stop: attrs.selectable.stop,
      ]
      def opts = selectableOptions.findAll {k, v -> v}.collect {k, v -> " $k:$v" }.join(',')
      jqCode += " \$('#${attrs.id } div').selectable({${opts}}); "

    }


    if (attrs.dblclickable) {
     jqCode += "\$('#${attrs.id} ${attrs.dblclickable.selector}').dblclick(function(e){var obj = jQuery(e.currentTarget); ${attrs.dblclickable.callback}});"
    }

    out << "<div id=\"${attrs.id}\" class=\"stories-list\">" + body() + "</div>"

    out << jq.jquery(null, jqCode)
  }
    
  def planningPokerFinalEstimate = { attrs, body ->
     out << "<div class=\"planning-poker-carte-result  ui-corner-all\"><div class=\"estimation\">${attrs.number}"+body()+"</div></div>"
  }
}