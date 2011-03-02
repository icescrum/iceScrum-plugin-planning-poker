<g:set var="scrumMaster" value="${sec.access([expression:'scrumMaster()'], {true})}"/>

<div class="planning-poker-right">
<is:iconButton
          action="closeSession"
          rendered="${scrumMaster}"
          controller="${id}"
          history="false"
          title="${message(code:'is.ui.planningPoker.toolbar.alt.close')}"
          alt="${message(code:'is.ui.planningPoker.toolbar.alt.close')}"
          onSuccess="jQuery.icescrum.planningpoker.closePlanningPoker()">
${message(code:'is.ui.planningPoker.toolbar.alt.close')}
</is:iconButton>
</div>