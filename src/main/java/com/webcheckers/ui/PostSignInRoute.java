package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.SessionTimeoutWatchdog;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;
import static com.webcheckers.ui.GetHomeRoute.PLAYER_KEY;

/**
 * The UI Controller to POST the user sign in.
 *
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class PostSignInRoute implements Route {
  // Console Logger
  private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

  // Username Error Messages
  private static final Message USER_EMPTY = Message.info("Username is empty!");
  private static final Message USER_INVALID = Message.info("Username invalid, don't use quotes!");
  private static final Message USER_TAKEN = Message.info("Username taken, please select another!");

  // The length of the session timeout in seconds
  static final int SESSION_TIMEOUT_PERIOD = 120;
  static final String TIMEOUT_SESSION_KEY = "timeoutWatchdog";

  // TemplateEngine used for HTML page rendering
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public PostSignInRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");

    LOG.config("PostSignInRoute is initialized.");
  }

  /**
   * Render the WebCheckers Sign In page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Sign-In page
   */
  @Override
  public Object handle(Request request, Response response) {

    final Session httpSession = request.session();
    LOG.finer("PostSignInRoute is invoked.");
    Map<String, Object> vm = new HashMap<>();

    if (request.queryParams("cancel") != null) {
      response.redirect(WebServer.HOME_URL);
      return null;
    }
    // Set the title
    vm.put("title", "Sign In");

    // Get username
    String username = request.queryParams("playerName").strip();

    // Username is empty tell the user it is empty.
    if (username.isEmpty()) {
      vm.put("message", USER_EMPTY);
      return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }

    // If the username is accepted
    else if (PlayerLobby.addPlayer(new Player(username))) {
      // Get the object that will provide client-specific services for this player
      final Player playerService = PlayerLobby.getPlayer(username);
      httpSession.attribute(PLAYER_KEY, playerService);

      // setup session timeout. The valueUnbound() method in the SessionTimeoutWatchdog will
      // be called when the session is invalidated. The next invocation of this route will
      // have a new Session object with no attributes.
      httpSession.attribute(TIMEOUT_SESSION_KEY, new SessionTimeoutWatchdog(playerService));
      httpSession.maxInactiveInterval(SESSION_TIMEOUT_PERIOD);
      response.redirect(WebServer.HOME_URL);
      halt();
      return null;

    } else {
      vm.put("message", USER_INVALID);
      return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }
  }
}
