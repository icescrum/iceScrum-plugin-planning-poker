<%--
  Created by IntelliJ IDEA.
  User: pol
  Date: 12/01/11
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
   <g:set var="productOwner" value="${sec.access([expression:'productOwner()'], {true})}"/>

  <is:iconButton
          action="close"
          icon="close"
          rendered="${productOwner}"
          controller="${id}"
          title="${message(code:'is.ui.planningPoker.toolbar.alt.close')}"
          alt="${message(code:'is.ui.planningPoker.toolbar.alt.close')}"
          update="window-content-${id}">
    ${message(code: 'is.ui.planningPoker.toolbar.close')}
  </is:iconButton>