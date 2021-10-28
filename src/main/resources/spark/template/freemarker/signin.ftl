<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="page sign-in">
  <h1>Web Checkers | ${title}</h1>
  <div class="body">
    <!-- Provide a message to the user, if supplied. -->
      <#include "message.ftl" />
    <div id="player-name">
      <label id="player-name--label" for="player-name--input">Choose a Player Name:</label>
      <input id="player-name--input" type="text" placeholder="Player Name" name="playerName"
             form="sign-in-form" autofocus>
    </div>
    <div id="sign-in__actions">
      <form id="sign-in-form" method="POST">
        <button class="button" id="sign-in-button">
          <span><!---->Sign In<!----></span>
        </button>
      </form>
      <form id="cancel-form" action="/" method="GET">
        <button class="button" id="cancel-button">
          <span><!---->Cancel<!----></span>
        </button>
      </form>
    </div>
  </div>
</div>
</body>
</html>
