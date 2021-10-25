 <div class="navigation">
  <#if current_player!=''>
    <a href="/">Go Home</a> |
      <form id="signout" action="/signout" method="get">
      <a href="/signout" >Sign Out [${current_player}]</a>
    </form>
  <#else>
    <a href="/signin">Sign In</a>
  </#if>
 </div>
