<%--
  Created by IntelliJ IDEA.
  User: pol
  Date: 12/01/11
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%><g:set var="productOwner" value="${sec.access([expression:'productOwner()'], {true})}"/>

  <is:link controller="planningPoker"
            rendered="${productOwner}"
            action="start"
            update="window-content-planningPoker"
            onSuccess="\$.icescrum.displayView('start')"
            remote="true"
            value="Demarrer la session"/>




<jq:jquery>
    jQuery("#window-content-${id}").removeClass('window-content-toolbar');
    if(!jQuery("#dropmenu").is(':visible')){
      jQuery("#window-id-${id}").focus();
    }
    <is:renderNotice />
    <icep:notifications
          name="planningPokerWindow"
          reload="[update:'#window-content-planningPoker',action:'join',params:[product:params.product]]"
          group="${params.product}-planningPoker"
          listenOn="#window-content-planningPoker"/>
  </jq:jquery>