<div class="navigation">
    <#if currentUser??>
      <a class="button nav-button" href="/">Go Home</a>
      <form id="signout" action="/signout" method="post">
        <a class="button nav-button" href="#" onclick="event.preventDefault(); signout.submit();">
          Sign Out [${currentUser.name}]</a>
      </form>
    <#else>
      <a class="button nav-button" href="/signin">Sign In</a>
    </#if>
</div>
