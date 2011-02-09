package org.icescrum.plugins.planningPoker
import org.icescrum.components.UtilsWebComponents
class PlanningPokerTagLib {
  static namespace = 'is'

  def planningPokerStoryList = { attrs, body ->
    if(attrs.selectable) {
     def selectableOptions = [
             filter: UtilsWebComponents.wrap(attr: (attrs.selectable.filter), doubleQuote: true),
             cancel: UtilsWebComponents.wrap(attrs.selectable.cancel),
             selected: "function(event,ui){${attrs.selectable.selected}}",
             stop: attrs.selectable.stop,
     ]
    def opts = selectableOptions.findAll {k, v -> v}.collect {k, v -> " $k:$v" }.join(',')
     def jqCode = " \$('#${attrs.id}').selectable({${opts}}); "
    out << jq.jquery(null, jqCode)
    }
     out << "<div id=\"${attrs.id}\" class=\"stories-list\">"+body()+"</div>"
   }

}