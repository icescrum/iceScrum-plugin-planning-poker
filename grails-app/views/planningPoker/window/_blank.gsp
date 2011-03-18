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

<div class="box-blank clearfix">
  <p><g:message code="is.ui.planningPoker.help"/> </p>
  <table cellspacing="0" cellpadding="0" border="0" class="box-blank-button">
    <tbody><tr>
      <td class="empty">&nbsp;</td>
      <td>
        <is:button
            type="link"
            rendered="${scrumMaster}"
            history="false"
            remote="true"
            controller="planningPoker"
            action="startSession"
            button="button-s button-s-light"
            title="${message(code:'is.ui.planningPoker.start')}"
            alt="${message(code:'is.ui.planningPoker.start')}"
            update="window-content-${id}"
            icon="create" >
            <strong>${message(code:'is.ui.planningPoker.start')}</strong>
        </is:button>
      </td>
      <td class="empty">&nbsp;</td>
    </tr>
  </tbody></table>
</div>