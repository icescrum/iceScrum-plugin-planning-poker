<%--
  Created by IntelliJ IDEA.
  User: pol
  Date: 12/01/11
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%><g:set var="scrumMaster" value="${sec.access([expression:'scrumMaster()'], {true})}"/>

  <is:link controller="planningPoker"
            rendered="${scrumMaster}"
            action="start"
            update="window-content-planningPoker"
            history="false"
            onSuccess="\$.icescrum.displayView('start')"
            remote="true"
            value="${message(code:'is.ui.planningPoker.start')}"/>
