package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * UI Controller that handles move validation. {@code POST /validateMove}.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class PostValidateMoveRoute implements Route {
  private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /validateMove} HTTP requests.
   */
  public PostValidateMoveRoute() {
    LOG.config("PostValidateMoveRoute is initialized.");
  }

  /**
   * Handle piece movement validation and return appropriate json ajax response.
   *
   * @param request The HTTP request
   * @param response The HTTP response
   * @return Json Ajax response
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("PostValidateMoveRoute is invoked.");

    // Get Move
    Gson gson = new Gson();
    Move move = gson.fromJson(request.queryParams("actionData"), Move.class);

    // TODO validate move

    return new Gson().toJson(Message.info("Move Successful!"));
  }
}
