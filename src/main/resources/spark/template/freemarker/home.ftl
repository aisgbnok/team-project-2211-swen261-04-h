<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
    <#include "nav-bar.ftl" />


  <div class="body">
      <#if current_player!=''>
        <h2>Welcome back, ${current_player}</h2>
        <b>Current Player: ${current_player}</b>
          <#list all_players as player>
            <p>${player}
              <a href="/game?versus=${player}">Challenge</a>
            </p>
          </#list>
      <#else>
        Players online: ${count}
      </#if>
    <!-- Provide a message to the user, if supplied. -->
      <#include "message.ftl" />

    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

  </div>

</div>
</body>

</html>
