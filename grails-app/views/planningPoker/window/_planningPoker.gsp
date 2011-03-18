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

<r:use module="planningPoker"/>
<g:set var="scrumMaster" value="${sec.access([expression:'scrumMaster()'], {true})}"/>

<div id="jeu">
  <table cellpadding="0" width="100%" cellspacing="0" border="0" id="planning-poker-members-list">
      <tr>
          <g:each in="${projectMembers}" var="projectMember">
              <td align="center">
                  <div align="center">
        <is:avatar userid="${projectMember.id}" class="ico"/><br/>
        <span class="username">${projectMember.firstName} ${projectMember.lastName}</span></div>
        <div id="planning-poker-members-list-card-${projectMember.id}" class="planning-poker-carte-others ui-corner-all">&nbsp;</div>
   </td> </g:each>
      </tr>
  </table>

  <div id="planning-poker-table" class="ui-corner-all" style="height:20%">
       <div id="voteButton">
        <is:button
             type="link"
             history="false"
             remote="true"
             rendered="${scrumMaster}"
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

  <div align="center"><is:avatar userid="${me.id}" class="ico"/><br/>
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
                    product:params.product]">
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
         dblclickable='[selector:".postit", callback:is.quickLook(params:"\"story.id=\"+obj.attr(\"elemId\")")]'
          style="width:100%"
          selectable="[rendered:scrumMaster,
                 filter:'.postit',
                 cancel:'.postit-label, .postit-story, a',
                 selected:'\$.icescrum.planningpoker.selectStory(this, \$(this).find(\'div\').attr(\'elemid\'));']">

    <g:each in="${stories_ne}" var="story">
      <div style="float:left" class="accepted-list">
        <is:postit id="${story.id}"
                miniId="${story.id}"
                title="${story.name}"
                attachment="${story.totalAttachments}"
                styleClass="story type-story-${story.type}"
                type="story"
                typeNumber="${story.type}"
                typeTitle="${is.bundleFromController(bundle:'typesBundle',value:story.type)}"
                miniValue="${story.effort >= 0 ? story.effort :'?'}"
                color="${story.feature?.color}"
                stateText="${is.bundleFromController(bundle:'stateBundle',value:story.state)}"
                controller="planningPoker"
                comment="${story.totalComments >= 0 ? story.totalComments : ''}">
          <is:truncated size="50" encodedHTML="true"><is:storyTemplate story="${story}"/></is:truncated>
          <g:if test="${story.name?.length() > 17 || is.storyTemplate(story:story).length() > 50}">
            <is:tooltipPostit
                    type="story"
                    id="${story.id}"
                    title="${story.name?.encodeAsHTML()}"
                    text="${is.storyTemplate([story:story])} "
                    apiBeforeShow="if(\$('#dropmenu').is(':visible') || \$('#postit-select-suite').is(':visible')){return false;}"
                    container="\$('#window-content-${id}')"
            />
          </g:if>
        </is:postit>
      </div>
    </g:each>
  </is:planningPokerStoryList>

  <p id="estimated-title"><span>-</span> <g:message code="is.ui.planningPoker.estimated"/> :</p>
<is:planningPokerStoryList
         id='estimated-list'
         dblclickable='[selector:".postit", callback:is.quickLook(params:"\"story.id=\"+obj.attr(\"elemId\")")]'>
    <g:each in="${stories_e}" var="story">
      <div style="float:left">
        <is:postit id="${story.id}"
                miniId="${story.id}"
                title="${story.name}"
                attachment="${story.totalAttachments}"
                styleClass="story type-story-${story.type}"
                type="story"
                typeNumber="${story.type}"
                typeTitle="${is.bundleFromController(bundle:'typesBundle',value:story.type)}"
                miniValue="${story.effort >= 0 ? story.effort :'?'}"
                color="${story.feature?.color}"
                stateText="${is.bundleFromController(bundle:'stateBundle',value:story.state)}"
                controller="planningPoker"
                comment="${story.totalComments >= 0 ? story.totalComments : ''}">
          <is:truncated size="50" encodedHTML="true"><is:storyTemplate story="${story}"/></is:truncated>
          <g:if test="${story.name?.length() > 17 || is.storyTemplate(story:story).length() > 50}">
            <is:tooltipPostit
                    type="story"
                    id="${story.id}"
                    title="${story.name?.encodeAsHTML()}"
                    text="${is.storyTemplate([story:story])} "
                    apiBeforeShow="if(\$('#dropmenu').is(':visible') || \$('#postit-select-suite').is(':visible')){return false;}"
                    container="\$('#window-content-${id}')"/>
          </g:if>
        </is:postit>
      </div>
    </g:each>
  </is:planningPokerStoryList>
</div>


<jq:jquery>
  jQuery.icescrum.planningpoker.init(${params.product}, ${valueBeforeVote}, ${valueUnvoted});

  <is:renderNotice />
  <icep:notifications
        name="planningPokerWindow"
        callback="jQuery.icescrum.planningpoker.closePlanningPoker();"
        group="${params.product}-planningPoker-close"
        listenOn="#window-content-planningPoker"/>
  <icep:notifications
        name="planningPokerWindow"
        callback="jQuery.icescrum.planningpoker.showResult();"
        group="${params.product}-planningPoker-finalResult"
        listenOn="#window-content-planningPoker"/>
  <icep:notifications
        name="planningPokerWindow"
        callback="jQuery.icescrum.planningpoker.notifyStorySelected(${params.product});"
        group="${params.product}-planningPoker-selection-story"
        listenOn="#window-content-planningPoker"/>
  <icep:notifications
        name="planningPokerWindow"
        callback="jQuery.icescrum.planningpoker.startVote(${me.id});"
        group="${params.product}-planningPoker-beginningOfCountDown"
        listenOn="#window-content-planningPoker"/>
  <icep:notifications
        name="planningPokerWindow"
        reload="[update:'#window-content-planningPoker',controller:'planningPoker',action:'display',params:[product:params.product]]"
        group="${params.product}-planningPoker-voteAccepted"
        listenOn="#window-content-planningPoker"/>
  <icep:notifications
        name="planningPokerWindow"
        callback="jQuery.icescrum.planningpoker.endOfCountDown(${me.id});"
        group="${params.product}-planningPoker-endOfCountDown"
        listenOn="#window-content-planningPoker"/>
  <icep:notifications
        name="planningPokerWindow"
        callback="jQuery.icescrum.planningpoker.displayStatusOthers(${me.id});"
        group="${params.product}-planningPoker-displayStatusOthers"
        listenOn="#window-content-planningPoker"/>
   <icep:notifications
        name="planningPokerWindow"
        callback="jQuery.icescrum.planningpoker.revote();"
        group="${params.product}-planningPoker-revote"
        listenOn="#window-content-planningPoker"/>
</jq:jquery>