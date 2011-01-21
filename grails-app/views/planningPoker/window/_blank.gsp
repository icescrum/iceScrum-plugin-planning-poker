

<ul id="planning-poker-members-list">
  <g:each in="${u}" var="user">
    <li style="display:inline-block;list-style:none;padding:0 5px 0 5px; text-align:center">
        <is:avatar userid="${user.id}" class="ico"/><br/>
        ${user.firstName} ${user.lastName}
      <div class="planning-poker-carte ui-corner-all" style="width:50%; margin:auto; border:1px silver solid; height:50px"></div>
    </li>
  </g:each>
</ul>

<div id="planning-poker-table" class="ui-corner-all" style="background-color:#2e8b57; width:90%; height:100px; margin:auto; border:3px #daa520 solid"></div>
<div align="center"> <is:avatar userid="${me.id}" class="ico"/><br/>
        ${me.firstName} ${me.lastName}</div>


<ul id="planning-poker-card-list">
  <g:each in="${suite_fibo}" var="n">
    <li style="display:inline-block;list-style:none;padding:0 5px 0 5px; text-align:center">
      <div class="planning-poker-carte ui-corner-all" style="width:30px; margin:auto; font-size:1.5em; text-align:center; border:1px silver solid; height:50px">${n}</div>
    </li>
  </g:each>
</ul>