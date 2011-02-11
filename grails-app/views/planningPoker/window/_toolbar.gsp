<%--
  Created by IntelliJ IDEA.
  User: pol
  Date: 12/01/11
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<div class="right">
<is:iconButton
          action="close"
          icon="close"
          rendered="${productOwner}"
          controller="${id}"
          history="false"
          title="${message(code:'is.ui.planningPoker.toolbar.alt.close')}"
          alt="${message(code:'is.ui.planningPoker.toolbar.alt.close')}"
          onSuccess="jQuery.icescrum.openWindow('project')">
${message(code:'is.ui.planningPoker.toolbar.alt.close')}
</is:iconButton>
</div>