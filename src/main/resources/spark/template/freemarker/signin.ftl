<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>Login | Web Checkers</title>
  <link rel="stylesheet" href="/css/style.css">
  <link rel="stylesheet" href="/css/game.css">
</head>
<body>
  <div class="page">
    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />
    <form action="./signin" method="POST">
      <div>
        <label for="playerName">Who do you want to log in as?</label>
        <input name="playerName" id="name" value="">
      </div>
      <div>
        <button>Log In</button>
      </div>
    </form>
  </div>
</body>
</html>
