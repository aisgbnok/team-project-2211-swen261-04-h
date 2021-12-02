package com.webcheckers.ui;

import static com.webcheckers.model.Game.GAME_OVER_RESIGN;
import static com.webcheckers.ui.GetHomeRoute.CURRENT_PLAYER_KEY;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import java.util.Objects;
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
  private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());

  // Global GSON instance
  private final Gson gson;

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /resignGame} HTTP requests.
   *
   * @param gson The GSON instance used for communicating messages to Webpage Javascript
   */
  public PostResignRoute(final Gson gson) {
    this.gson = Objects.requireNonNull(gson, "gson is required");

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

    // currentPlayer that wants to resign
    Player player = request.session().attribute(CURRENT_PLAYER_KEY);

    // Message
    String messageText = String.format(GAME_OVER_RESIGN, player.getName());
    Message message = Message.info(messageText);

    // Get the gameID UUID from queryParam
    UUID gameID = gson.fromJson(request.queryParams("gameID"), UUID.class);

    // Signal to GameCenter that the game is over and why
    GameCenter.gameOver(gameID, messageText);

    return gson.toJson(message);
  }
}
