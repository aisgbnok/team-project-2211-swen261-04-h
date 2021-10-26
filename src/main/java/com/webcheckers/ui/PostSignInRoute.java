package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.SessionTimeoutWatchdog;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import java.util.Locale;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;
import static com.webcheckers.ui.GetHomeRoute.CURRENT_USER;

/**
 * The UI Controller to POST the user sign in.
 *
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 * @author <a href='mailto:idc7947@rit.edu'>Ian Chasse</a>
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
   * Create the Spark Route (UI controller) to handle all {@code POST /signin} HTTP requests.
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
    LOG.finer("PostSignInRoute is invoked.");

    final Session httpSession = request.session();
    Map<String, Object> vm = new HashMap<>();

    // If cancel button isn't null then the user clicked it
    if (request.queryParams("cancel") != null) {
      // Redirect to the homepage
      response.redirect(WebServer.HOME_URL);
      return null;
    }

    // Set the title
    vm.put("title", "Sign In");

    // Get username
    String username = request.queryParams("playerName").strip();

    // Handle Username Validation Checking
    if (username.isEmpty()) {
      // If username is empty, notify the user.
      vm.put("message", USER_EMPTY);
      return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    } else if (username.contains("\"") || username.contains("'")) {
      // If username is invalid, notify the user.
      vm.put("message", USER_INVALID);
      return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    } else if (PlayerLobby.contains(username)) {
      // If username is already taken, notify the user.
      vm.put("message", USER_TAKEN);
      return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }

    // Username passed validation
    // Now we can create a new Player
    final Player currentUser = new Player(username);
    // We can also register that player with the PlayerLobby
    PlayerLobby.addPlayer(currentUser);

    // Set the httpSession CURRENT_USER attribute to the currentUSer
    httpSession.attribute(CURRENT_USER, currentUser);

    // TODO: Anthony Swierkosz needs to understand what this does
    // setup session timeout. The valueUnbound() method in the SessionTimeoutWatchdog will
    // be called when the session is invalidated. The next invocation of this route will
    // have a new Session object with no attributes.
    httpSession.attribute(TIMEOUT_SESSION_KEY, new SessionTimeoutWatchdog(currentUser));
    httpSession.maxInactiveInterval(SESSION_TIMEOUT_PERIOD);
    response.redirect(WebServer.HOME_URL);
    halt();
    return null;
  }
}
