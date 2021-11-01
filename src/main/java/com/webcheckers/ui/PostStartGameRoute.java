package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.CURRENT_PLAYER;
import static com.webcheckers.ui.WebServer.GAME_URL;
import static com.webcheckers.ui.WebServer.HOME_URL;
import static spark.Spark.halt;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

/**
 * The UI Controller to start a new game.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class PostStartGameRoute implements Route {

  static final String OPPONENT_PLAYER_KEY = "opponent";
  static final String GAME_KEY = "game";
  static final String BOARD_KEY = "board";

  // Console Logger
  private static final Logger LOG = Logger.getLogger(PostStartGameRoute.class.getName());

  /** Create the Spark Route (UI controller) to handle all {@code POST /startGame} HTTP requests. */
  public PostStartGameRoute() {

    LOG.config("PostStartGameRoute is initialized.");
  }

  /**
   * Render the WebCheckers Game page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Game page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("PostStartGameRoute is invoked.");

    final Session currentSession = request.session();

    Player currentPlayer = currentSession.attribute(CURRENT_PLAYER);

    // Check to see if canGameStart is false, if it is false we can't start a new game
    if (!canGameStart(request, currentPlayer)) {
      halt("Conditions aren't met for game start.");
      return null;
    }

    // All conditions for staring a game have been met, so we can set up a new game.
    Player opponentPlayer = PlayerLobby.getPlayer(request.queryParams(OPPONENT_PLAYER_KEY));
    setupGame(currentSession, currentPlayer, opponentPlayer);

    // Redirect to home
    response.redirect(HOME_URL);
    return null;
  }

  private boolean canGameStart(Request request, Player currentPlayer) {

    // We can't start a game if there is no currentPlayer
    if (currentPlayer == null) return false;

    // We shouldn't start a new game if the currentPlayer is already in a game
    if(currentPlayer.inGame()) return false;

    // We can't start a game if there is no opponent QueryParameter key
    if (request.queryParams(OPPONENT_PLAYER_KEY).isEmpty()) return false;


    // TODO: Check to see if opponent QueryParameter matches with a Player in PlayerLobby

    // All Game Start conditions are met
    return true;
  }

  private void setupGame(Session currentSession, Player currentPlayer, Player opponentPlayer) {
    // Create new Game Model
    Game newGame = new Game(currentPlayer, opponentPlayer);

    // Set Game and Board Session attributes
    currentSession.attribute(GAME_KEY, newGame);
    currentSession.attribute(BOARD_KEY, newGame.getBoard());

    // Set the opponent attribute
    currentSession.attribute(OPPONENT_PLAYER_KEY, opponentPlayer);

    // Register Game with GameCenter
    GameCenter.addGame(newGame);

    // TODO eventually have GameCenter handle adding and removing player inGame status
    // Set currentPlayer and opponentPlayer as inGame
    currentPlayer.setGame(true);
    opponentPlayer.setGame(true);
  }
}
