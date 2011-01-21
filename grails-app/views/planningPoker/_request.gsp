<%--
  Created by IntelliJ IDEA.
  User: Marc-Antoine
  Date: 12/01/11
  Time: 14:20
  To change this template use File | Settings | File Templates.
--%>
<form id="pokerForm" name="pokerForm" method="post" class='box-form box-form-small-legend box-content box-form-180'>
<g:message code="is.ui.joinPlanningPoker"/>
  <is:buttonBar id="pokerButtonBar">
    <is:button
              name="pokerButton"
              type="submit"
              url="[controller:'planningPoker', action:'']"
              value="${message(code:'is.yes')}"/>
    <is:button type="link" button="button-s button-s-black"
              value="${message(code: 'is.no')}"/>
 </is:buttonBar>
</form>