<r:use module="planningPoker"/>

<g:set var="productOwner" value="${sec.access([expression:'productOwner()'], {true})}"/>

<ul id="planning-poker-members-list">
  <g:each in="${u}" var="user">
    <li style="display:inline-block;list-style:none;padding:0 5px 0 5px; text-align:center">
        <is:avatar userid="${user.id}" class="ico"/><br/>
        ${user.firstName} ${user.lastName}
      <div class="planning-poker-carte ui-corner-all" style="width:50%; margin:auto; border:1px silver solid; height:50px"></div>
    </li>
  </g:each>
</ul>

<div id="planning-poker-table" class="ui-corner-all" style="background-color:#2e8b57; width:90%; height:50px; margin:auto; border:3px #daa520 solid"></div>
<div align="center"> <is:avatar userid="${me.id}" class="ico"/><br/>
        ${me.firstName} ${me.lastName}</div>


<ul id="planning-poker-card-list" style="margin:0; padding-bottom:0">
  <g:each in="${suite_fibo}" var="n">
    <li style="display:inline-block;list-style:none;padding:0 5px 0 5px; text-align:center">
      <div class="planning-poker-carte ui-corner-top" style="width:30px; margin:auto; font-size:1.4em; text-align:center; border:1px silver solid; height:25px; border-bottom:0">${n}</div>
    </li>
  </g:each>
</ul>

<div style="background-color:#F2F2F2;border-top:1px silver solid;">
  <div id="accepted-list" style="width:5000px; overflow:hidden" class="stories-list">
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
    <is:truncated size="50" encodedHTML="true"><is:storyTemplate story="${story}" /></is:truncated>



    <g:if test="${story.name?.length() > 17 || is.storyTemplate(story:story).length() > 50}">
      <is:tooltipPostit
              type="story"
              id="${story.id}"
              title="${story.name}"
              text="${is.storyTemplate([story:story])} "
              apiBeforeShow="if(\$('#dropmenu').is(':visible') || \$('#postit-select-suite').is(':visible')){return false;}"
              container="\$('#window-content-${id}')"/>
    </g:if>
  </is:postit>
  </div>
</g:each>
 </div>
<div id="estimated-list" style="width:5000px; overflow:hidden" class="stories-list">
<g:each in="${stories_e}" var="story" >
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
    <is:truncated size="50" encodedHTML="true"><is:storyTemplate story="${story}" /></is:truncated>



    <g:if test="${story.name?.length() > 17 || is.storyTemplate(story:story).length() > 50}">
      <is:tooltipPostit
              type="story"
              id="${story.id}"
              title="${story.name}"
              text="${is.storyTemplate([story:story])} "
              apiBeforeShow="if(\$('#dropmenu').is(':visible') || \$('#postit-select-suite').is(':visible')){return false;}"
              container="\$('#window-content-${id}')"/>
    </g:if>
  </is:postit>
</div>
</g:each>
</div>
</div>


<jq:jquery>
  $('.stories-list').draggable({ axis: 'x',
                                 drag: function(event, ui) { console.log(event);}});
</jq:jquery>