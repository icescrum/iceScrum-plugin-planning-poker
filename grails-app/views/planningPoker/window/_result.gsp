<%--*
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
 *--%>

<g:set var="scrumMaster" value="${sec.access([expression:'scrumMaster()'], {true})}"/>

<div class="planning-poker-carte-result  ui-corner-all">
    <g:if test="${scrumMaster}">
        <is:editable
            on="#editableNote"
            typed="[type:'numeric']"
            wrap="true"
            action="estimateStory"
            onExit="submit"
            controller="planningPoker"
            highlight="true"
            params="[product:params.product]"/>
    </g:if>

  <div class="estimation">
    <span id="editableNote">${result}</span>
  </div>
  <div class="acceptButton">
   <is:button
          type="link"
          history="false"
          remote="true"
          rendered="${scrumMaster}"
          button="button-s button-s-light"
          controller="planningPoker"
          action="acceptVote"
          title="${message(code:'is.ui.planningPoker.accept.result')}"
          alt="${message(code:'is.ui.planningPoker.accept.result')}"
          icon="create" ><strong>${message(code:'is.ui.planningPoker.accept.result')}</strong>
    </is:button>
  </div>
  <div class="revotetButton">
   <is:button
          type="link"
          history="false"
          remote="true"
          rendered="${scrumMaster}"
          button="button-s button-s-light"
          controller="planningPoker"
          action="revote"
          title="${message(code:'is.ui.planningPoker.revote')}"
          alt="${message(code:'is.ui.planningPoker.revote')}"
          icon="create" >
     <strong>${message(code:'is.ui.planningPoker.revote')}</strong>
    </is:button>
  </div>
  <div class="cancelButton">
   <is:button
          type="link"
          history="false"
          remote="true"
          rendered="${scrumMaster}"
          button="button-s button-s-light"
          controller="planningPoker"
          action="cancel"
          title="${message(code:'is.ui.planningPoker.cancel')}"
          alt="${message(code:'is.ui.planningPoker.cancel')}"
          icon="create" >
     <strong>${message(code:'is.ui.planningPoker.cancel')}</strong>
    </is:button>
  </div>
</div>