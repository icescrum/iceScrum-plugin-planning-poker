/*
 * Copyright (c) 2011 BE ISI iSPlugins Université Paul Sabatier.
 *
 * This file is part of iceScrum.
 *
 * Planning Poker plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * Planning Poker plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Planning Poker plugin.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Authors: 	Claude AUBRY (claude.aubry@gmail.com)
 * 		Vincent BARRIER (vbarrier@kagilum.com)
 *		Marc-Antoine BEAUVAIS (marcantoine.beauvais@gmail.com)
 *		Vincent CARASSUS (vincentcarassus@gmail.com)
 *		Gabriel GIL (contact.gabrielgil@gmail.com)
 *		Julien GOUDEAUX (julien.goudeaux@orange.fr)
 *		Guillaume JANDIN (guillaume.baz@gmail.com)
 *		Jihane KHALIL (khaliljihane@gmail.com)
 *		Paul LABONNE (paul.labonne@gmail.com)
 *		Nicolas NOULLET (nicolas.noullet@gmail.com)
 *		Bertrand PAGES (pages.bertrand@gmail.com)
 *		Jérémy SIMONKLEIN (jeremy.simonklein@gmail.com)
 *		Steven STREHL (steven.strehl@googlemail.com)
 *
 *
 */

package org.icescrum.plugins.planningPoker
import org.icescrum.components.UtilsWebComponents
class PlanningPokerTagLib {
  static namespace = 'is'

  def planningPokerStoryList = { attrs, body ->
    def jqCode=""
    if (attrs.selectable != null && UtilsWebComponents.rendered(attrs.selectable)){
      def selectableOptions = [
              filter: UtilsWebComponents.wrap(attr: (attrs.selectable.filter), doubleQuote: true),
              cancel: UtilsWebComponents.wrap(attrs.selectable.cancel),
              selected: "function(){ ${attrs.selectable.selected}}"
      ]
      def opts = selectableOptions.findAll {k, v -> v}.collect {k, v -> " $k:$v" }.join(',')
      jqCode = "\$('#${attrs.id } .accepted-list').selectable({${opts}});"

    out << jq.jquery(null, jqCode)
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