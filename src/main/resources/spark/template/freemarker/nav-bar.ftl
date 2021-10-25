<div class="navigation">
    <#if current_player!=''>
      <a class="button nav-button" href="/">Go Home</a>
      <form id="signout" action="/signout" method="get">
        <a class="button nav-button" href="/signout">Sign Out [${current_player}]</a>
      </form>
    <#else>
      <a class="button nav-button" href="/signin">Sign In</a>
    </#if>
</div>
