 <div class="navigation">
  <#if current_player!=''>
    <a href="/">my home</a> |
      <form id="signout" action="/signout" method="get">
      <a href="/signout" >sign out [${current_player}]</a>
    </form>
  <#else>
    <a href="/signin">sign in</a>
  </#if>
 </div>
