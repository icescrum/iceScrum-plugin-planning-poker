<div class="planning-poker-right">
<is:iconButton
          action="close"
          rendered="${productOwner}"
          controller="${id}"
          history="false"
          title="${message(code:'is.ui.planningPoker.toolbar.alt.close')}"
          alt="${message(code:'is.ui.planningPoker.toolbar.alt.close')}"
          onSuccess="jQuery.icescrum.planningpoker.closePlanningPoker()">
${message(code:'is.ui.planningPoker.toolbar.alt.close')}
</is:iconButton>
</div>