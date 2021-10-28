package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetHomeRoute.CURRENT_PLAYER;
import static spark.Spark.halt;

/**
 * The UI Controller to POST the Sign-Out page.
 *
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class PostSignOutRoute implements Route {
  // Console Logger
  private static final Logger LOG = Logger.getLogger(PostSignOutRoute.class.getName());

  // TemplateEngine used for HTML page rendering
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /signout} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public PostSignOutRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");

    LOG.config("PostSignInRoute is initialized.");
  }

  /**
   * Sign out and redirect to the WebCheckers home page
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("PostSignOutRoute is invoked.");
    final Session httpSession = request.session();

    Player currentPlayer = httpSession.attribute(CURRENT_PLAYER);
    PlayerLobby.removePlayer(currentPlayer);
    httpSession.removeAttribute(CURRENT_PLAYER);
    Map<String, Object> vm = new HashMap<>();
    response.redirect(WebServer.HOME_URL);
    halt();
    return null;
  }
}
