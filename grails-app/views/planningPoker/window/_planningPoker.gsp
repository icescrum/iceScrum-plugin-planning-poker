<r:use module="planningPoker"/>

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
          <div class="planning-poker-carte ui-corner-top me">${n}</div>
        </td>
      </g:each>
    </tr>
  </table>
</div>
<div id="stories">

  <p><g:message code="is.ui.planningPoker.accepted"/> :</p>

  <is:planningPokerStoryList
          id='accepted-list'
          style="width:100%"
          selectable="[filter:'.postit',
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
         id='estimated-list'>
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
                dblclickable='[selector:".postit", callback:is.quickLook(params:"\"story.id=\"+obj.attr(\"elemId\")")]'
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

  $("#accepted-list .accepted-list").click(function() {
$(this).addClass("selected ui-corner-all").siblings().removeClass("selected ui-corner-all");
});
   $("#accepted-list .accepted-list").dblclick(function() {
   alert("TODO quickloock")  ;
});
</jq:jquery>
