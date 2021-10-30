package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import java.util.Objects;
import spark.*;

import java.util.logging.Logger;

/**
 * The UI Controller to POST the resign route.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 */
public class PostResignRoute implements Route {
  private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

  private Gson gson;

  // TODO improve documentation
  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /resign} HTTP requests.
   *
   * @param gson
   */
  public PostResignRoute(Gson gson) {
    this.gson = Objects.requireNonNull(gson, "gson is required");
    LOG.config("PostResignRoute is initialized.");
  }

  // TODO improve documentation
  /**
   * Resign and redirect to the WebCheckers Home page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("PostResignRoute is invoked.");

    final Session httpSession = request.session();
    Player player = httpSession.attribute(GetHomeRoute.CURRENT_PLAYER);

    return gson.toJson(Message.info("true"));
  }
}
