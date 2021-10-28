<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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

    <!-- Provide a message to the user, if supplied. -->
      <#include "message.ftl" />

    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->
    <div class="player-info">
      <h2>Players Online</h2>
        <#if currentUser??>
            <#if (currentPlayers?size > 0)>
              <p>Click on any player to start a game against them. There are ${playerCount} players
                to choose!</p>
                <#list currentPlayers as player>
                  <form action="/game" method="GET">
                    <button class="button" type="submit" name="opponent"
                            value="${player}">${player}</button>
                  </form>
                </#list>
            <#else>
              There are no other players online!
            </#if>
        <#else>
            <#if playerCount=0>
              There are no other players online!
            <#else>
              There are ${playerCount} players online. Sign in to see who they are!
            </#if>
        </#if>
    </div>
  </div>
</div>
</body>
</html>
