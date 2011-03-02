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
            action="start"
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