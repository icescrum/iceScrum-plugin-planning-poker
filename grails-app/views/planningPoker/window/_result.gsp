<g:set var="scrumMaster" value="${sec.access([expression:'scrumMaster()'], {true})}"/>

<div class="planning-poker-carte-result  ui-corner-all">
  <div class="estimation">
    ${result}
  </div>
  <div class="acceptButton">
   <is:button
          type="link"
          history="false"
          remote="true"
          rendered="${scrumMaster}"
          controller="planningPoker"
          button="button-s button-s-light"
          action="acceptResult"
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
          controller="planningPoker"
          button="button-s button-s-light"
          action="startVote"
          title="${message(code:'is.ui.planningPoker.accept.result')}"
          alt="${message(code:'is.ui.planningPoker.accept.result')}"
          icon="create" ><strong>${message(code:'is.ui.planningPoker.revote')}</strong>
    </is:button>
  </div>
</div>