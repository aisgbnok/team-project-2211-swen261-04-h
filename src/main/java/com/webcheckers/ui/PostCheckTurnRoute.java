package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.CURRENT_PLAYER_KEY;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import java.util.UUID;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * UI Controller that handles notifying the user if it is their turn. {@code POST /checkTurn}.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class PostCheckTurnRoute implements Route {

  // Console Logger
  private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

  /** Create the Spark Route (UI controller) to handle all {@code POST /checkTurn} HTTP requests. */
  public PostCheckTurnRoute() {
    LOG.config("PostCheckTurnRoute is initialized.");
  }

  /**
   * Handle checking user turn and return appropriate json ajax response.
   *
   * @param request The HTTP request
   * @param response The HTTP response
   * @return Json Ajax response
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("PostCheckTurnRoute is invoked.");

    // Setup new Gson
    Gson gson = new Gson();

    // currentPlayer that wants to resign
    Player player = request.session().attribute(CURRENT_PLAYER_KEY);

    // Get the gameID UUID from queryParam
    UUID gameID = gson.fromJson(request.queryParams("gameID"), UUID.class);

    //Determine if it is the player's turn and generate the correct message
    Game game = GameCenter.getGame(gameID);

    Message message;
    if(game.isActivePlayer(player)) {
      message = Message.info("true");
    } else {
      message = Message.info("false");
    }

    return gson.toJson(message);
  }
}
