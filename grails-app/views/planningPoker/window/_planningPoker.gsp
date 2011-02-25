<r:use module="planningPoker"/>
<g:set var="scrumMaster" value="${sec.access([expression:'scrumMaster()'], {true})}"/>
<g:set var="productOwner" value="${sec.access([expression:'productOwner()'], {true})}"/>

<div id="jeu">

  <ul id="planning-poker-members-list">
    <g:each in="${u}" var="user">
      <li>
        <is:avatar userid="${user.id}" class="ico"/><br/>
        ${user.firstName} ${user.lastName}
        <div class="planning-poker-carte ui-corner-all"></div>
      </li>
    </g:each>
  </ul>

  <div id="planning-poker-table" class="ui-corner-all"></div>
  <div align="center"><is:avatar userid="${me.id}" class="ico"/><br/>
    ${me.firstName} ${me.lastName}</div>

  <table id="planning-poker-card-list">
    <tr>
      <td class="planning-poker-card">
        <div class="planning-poker-carte ui-corner-top me">?</div>
      </td>
      <g:each in="${suite_fibo}" var="n">
        <td class="planning-poker-card">
            <is:link controller="planningPoker"
                action="submitVote"
                history="false"
                remote="true"
                params="[valueCard:n]">
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
                 selected:'\$.icescrum.dblclickSelectable(ui,300,function(obj){'+is.quickLook(params:'\'story.id=\'+\$(obj.selected).icescrum(\'postit\').id()')+';})']">

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
                    container="\$('#window-content-${id}')"/>
          </g:if>
        </is:postit>
      </div>
    </g:each>
  </is:planningPokerStoryList>

  <p><g:message code="is.ui.planningPoker.estimated"/> :</p>
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
 var postitWidth = $('#accepted-list .postit-story').width();
 var windowWidth = $('.window-content').width();
 $('#accepted-list').css({width: postitWidth * $('#accepted-list .postit-story').size() });
 $('#estimated-list').css({width: postitWidth * $('#estimated-list .postit-story').size() });

 $('#accepted-list').scrollbar({contentWidth:windowWidth, position:'bottom'});
 $('#estimated-list').scrollbar({contentWidth:windowWidth, position:'bottom'});

//----------- Vote------------
 $(".planning-poker-carte.me").click(function(){
   $(".planning-poker-carte.me").removeClass("activatedCard");
   $(this).addClass("activatedCard");  // bla
 });
//----------------------------

   jQuery("#window-content-${id}").removeClass('window-content-toolbar');
  if(!jQuery("#dropmenu").is(':visible')){
    jQuery("#window-id-${id}").focus();
  }
  jQuery.icescrum.planningpoker.init();

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
        reload="[update:'#window-content-planningPoker',action:'selectStory',params:[product:params.product]]"
        group="${params.product}-planningPoker-selection-story"
        listenOn="#window-content-planningPoker"/>
</jq:jquery>