<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>Login | Web Checkers</title>
  <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="page">
  <h1>Web Checkers | ${title}</h1>
  <div class="body">
    <!-- Provide a message to the user, if supplied. -->
      <#include "message.ftl" />
    <form action="./signin" method="POST">
      <div id="username__entry">
        <label for="playerName" id="name-label">Who do you want to log in as?</label>
        <input name="playerName" id="name" value="" autofocus>
      </div>
      <div id="sign-in_controls">
        <button class="button" id="sign-in">
          <span><!---->Log In<!----></span>
        </button>
        <button class="button" id="cancel" name="cancel">
          <span><!---->Cancel<!----></span>
        </button>
      </div>
    </form>
  </div>

</div>
</body>
</html>
