<%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 17/01/11
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head><title>Simple INDEX GSP page</title></head>
  <body>
  <g:set var="productOwner" value="${sec.access([expression:'productOwner()'], {true})}"/>

  <is:link controller="planningPoker"
            action="pushTest"
            update="window-content-planningPoker"
            onSuccess="\$.icescrum.displayView('testPush')"
            remote="true"
            value="push"/>


  <jq:jquery>
    jQuery("#window-content-${id}").removeClass('window-content-toolbar');
    if(!jQuery("#dropmenu").is(':visible')){
      jQuery("#window-id-${id}").focus();
    }
    <is:renderNotice />
    <icep:notifications
          name="planningPokerWindow"
          reload="[update:'#window-content-planningPoker',action:'pushTest',params:[product:params.product]]"
          group="${params.product}-planningPoker"
          listenOn="#window-content-planningPoker"/>
  </jq:jquery>
  </body>
</html>