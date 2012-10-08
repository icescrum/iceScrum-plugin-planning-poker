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

<div id="jeu">
  <table cellpadding="0" width="100%" cellspacing="0" border="0" id="planning-poker-members-list">
      <tr>
          <g:each in="${projectMembers}" var="projectMember">
              <td align="center">
                  <div align="center">
                    <is:avatar user="${projectMember}" class="ico"/><br/>
                    <span class="username">${projectMember.firstName} ${projectMember.lastName}</span>
                  </div>
                  <div id="planning-poker-members-list-card-${projectMember.id}" class="planning-poker-carte-others ui-corner-all">&nbsp;</div>
              </td>
          </g:each>
      </tr>
  </table>

  <div id="planning-poker-table" class="ui-corner-all" style="height:20%">
       <div id="voteButton">
        <is:button
             type="link"
             history="false"
             remote="true"
             rendered="${request.scrumMaster}"
             controller="planningPoker"
             button="button-s button-s-light"
             action="startVote"
             title="${message(code:'is.ui.planningPoker.vote.start')}"
             alt="${message(code:'is.ui.planningPoker.vote.start')}"
             icon="create"
             >
             <strong>${message(code:'is.ui.planningPoker.vote.start')}</strong>
        </is:button>
       </div>
  </div>

  <div align="center"><is:avatar user="${me}" class="ico"/><br/>
    ${me.firstName} ${me.lastName}
    <div id="planning-poker-members-list-card-me"></div>
  </div>

  <table id="planning-poker-card-list">
    <tr>
      <td class="planning-poker-card">
        <is:link controller="planningPoker"
            action="submitVote"
            history="false"
            remote="true"
            params="[valueCard:-1]">
            <div class="planning-poker-carte ui-corner-top me">?</div>
        </is:link>
      </td>
      <g:each in="${suite_fibo}" var="n">
        <td class="planning-poker-card">
            <is:link controller="planningPoker"
                action="submitVote"
                history="false"
                remote="true"
                params="[valueCard:n,
                    product:params.product,
                    iduser:me.id]">
                <div class='planning-poker-carte ui-corner-top me'>${n}</div>
            </is:link>
        </td>
      </g:each>
    </tr>
  </table>

  <div id="planning-poker-final-estimate">
  </div>

</div>






<div id="stories">

  <p><g:message code="is.ui.planningPoker.accepted"/> :</p>

  <is:planningPokerStoryList
          id='accepted-list'
          dblclickable='[selector:".postit", callback:"\$.icescrum.displayQuicklook"]'
          style="width:100%"
          selectable="[rendered:scrumMaster,
                 filter:'div.postit-story',
                 cancel:'.postit-label, a, .postit-excerpt',
                 selected:'\$.icescrum.planningpoker.selectStory(this, \$(this).find(\'div\').data(\'elemid\'));']">

    <g:each in="${stories_ne}" var="story">
      <div style="float:left" class="accepted-list">
          <g:include view="/story/_postit.gsp" model="[story:story,user:me]" params="[product:params.product]"/>
      </div>
    </g:each>
  </is:planningPokerStoryList>

  <p id="estimated-title"><span>-</span> <g:message code="is.ui.planningPoker.estimated"/> :</p>
<is:planningPokerStoryList
         id='estimated-list'
         dblclickable='[selector:".postit", callback:"\$.icescrum.displayQuicklook"]'>
    <g:each in="${stories_e}" var="story">
      <div style="float:left">
          <g:include view="/story/_postit.gsp" model="[story:story,user:me]" params="[product:params.product]"/>
      </div>
    </g:each>
  </is:planningPokerStoryList>
</div>


<jq:jquery>
  jQuery.icescrum.planningpoker.init(${params.product}, ${valueBeforeVote}, ${valueUnvoted});
</jq:jquery>