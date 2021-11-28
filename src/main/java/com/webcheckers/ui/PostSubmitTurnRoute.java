package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.CURRENT_PLAYER_KEY;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.UUID;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * UI Controller that handles turn submission. {@code POST /submitTurn}.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 */
public class PostSubmitTurnRoute implements Route {
  private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /submitTurn} HTTP requests.
   */
  public PostSubmitTurnRoute() {
    LOG.config("PostSubmitTurnRoute is initialized.");
  }

  /**
   * Handle turn submission and return appropriate json ajax response.
   *
   * @param request The HTTP request
   * @param response The HTTP response
   * @return Json Ajax response
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {
    LOG.finer("PostSubmitTurnRoute is invoked.");

    // Setup new Gson
    Gson gson = new Gson();

    // currentPlayer that wants to submit turn
    Player player = request.session().attribute(CURRENT_PLAYER_KEY);

    // Get the current game
    UUID gameID = gson.fromJson(request.queryParams("gameID"), UUID.class);
    Game game = GameCenter.getGame(gameID);

    if (game != null) {
      return gson.toJson(game.submitTurn(player, game));
    } else {
      //TODO: replace with custom exception
      throw new Exception();
    }
  }
}
