package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.CURRENT_PLAYER_KEY;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import java.util.UUID;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * UI Controller that handles game resignation. {@code POST /resignGame}.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class PostResignRoute implements Route {

  // Console Logger
  private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /resignGame} HTTP requests.
   */
  public PostResignRoute() {
    LOG.config("PostResignRoute is initialized.");
  }

  /**
   * Handle user resignation and return appropriate json ajax response.
   *
   * @param request The HTTP request
   * @param response The HTTP response
   * @return Json Ajax response
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("PostResignRoute is invoked.");

    // Setup new Gson
    Gson gson = new Gson();

    // currentPlayer that wants to resign
    Player player = request.session().attribute(CURRENT_PLAYER_KEY);

    // Message
    String messageText = player.getName() + " has resigned.";
    Message message = Message.info(messageText);

    // Get the gameID UUID from queryParam
    UUID gameID = gson.fromJson(request.queryParams("gameID"), UUID.class);

    // Signal to GameCenter that the game is over and why
    GameCenter.gameOver(gameID, messageText);

    return gson.toJson(message);
  }
}
