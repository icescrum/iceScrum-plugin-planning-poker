<%--
  Created by IntelliJ IDEA.
  User: emotik
  Date: 19/01/11
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<r:resourceLink url="[dir:'css',file:'planningPoker.css',plugin:'iceScrum-plugin-planning-poker']"  media="screen, projection"/>


<ul id="planning-poker-members-list">
  <g:each in="${u}" var="user">
    <li>
        <is:avatar userid="${user.id}" class="ico"/><br/>
        ${user.firstName} ${user.lastName}
    </li>
  </g:each>
</ul>