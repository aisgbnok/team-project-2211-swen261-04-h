package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Game.viewModes;
import com.webcheckers.model.Piece.Color;
import com.webcheckers.model.Player;
import java.util.UUID;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetHomeRoute.CURRENT_PLAYER_KEY;
import static com.webcheckers.ui.PostStartGameRoute.BOARD_KEY;
import static com.webcheckers.ui.PostStartGameRoute.GAME_KEY;
import static com.webcheckers.ui.WebServer.GAME_URL;

/**
 * The UI Controller to GET the Game page.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 */
public class GetGameRoute implements Route {

  static final String RED_PLAYER_KEY = "redPlayer";
  static final String WHITE_PLAYER_KEY = "whitePlayer";
  static final String GAME_ID_KEY = "gameID";

  // Console Logger
  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());
  private static final String PLAYER_IN_GAME = " is already in a game, choose someone else!";
  // TemplateEngine used for HTML page rendering
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /game} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public GetGameRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");

    LOG.config("GetGameRoute is initialized.");
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
    LOG.finer("GetGameRoute is invoked.");
    final Session currentSession = request.session();
    Map<String, Object> vm = new HashMap<>();

    // Set the title
    vm.put("title", "Game");

    // Get currentPlayer
    Player currentPlayer = currentSession.attribute(CURRENT_PLAYER_KEY);
    Player opponentPlayer = currentSession.attribute(CURRENT_PLAYER_KEY);

    // If game isn't in progress then return
    if (!gameInProgress(request, response, currentPlayer)) {
      response.redirect(WebServer.HOME_URL);
      return null;
    }

    // Now that we know a game is in progress let's render the game
    Game game = currentSession.attribute(GAME_KEY);
    Board board = game.getBoard();

    vm.put(CURRENT_PLAYER_KEY, currentPlayer);
    if (game.getPlayerColor(currentPlayer) == Color.RED) {
      vm.put("redPlayer", currentPlayer);
      vm.put("whitePlayer", opponentPlayer);
      board.fillRed();
    } else if (game.getPlayerColor(currentPlayer) == Color.WHITE) {
      vm.put("redPlayer", opponentPlayer);
      vm.put("whitePlayer", currentPlayer);
      board.fillWhite();
    }

    vm.put("viewMode", viewModes.PLAY);
    vm.put("activeColor", Color.RED);

    // TODO movement
    /*if (board.getTurn().equals("OPPONENT")) {
      vm.put("activeColor", Color.RED);
    } else {
      vm.put("activeColor", Color.WHITE);
    }

    LOG.finer(request.queryParams("opponent"));
    if (request.queryParams("opponent") != null) {
      opponent = PlayerLobby.getPlayer(request.queryParams("opponent"));
      vm.put("whitePlayer", opponent);
    }

    /*
    for (Row row : board.getRows()) {
      for (Space space : row.getSpaces()) {
        int x = space.getCell();
        int y = space.getRow();
        Space space2 = board.getPieceAtPosition(x + 1, y + 1);
        if (space2 != null) {
          if (space2.getPiece() == null) {
            Move newMove = new Move();
            newMove.setStart(space);
            newMove.setEnd(space2);
            board.addValidMove(newMove);
          }
        }
      }
    }

    /*
    String current_turn = board.getTurn();
    if (current_turn == currentPlayer.getName()){
      vm.put("turn", "YOUR TURN");
    } else {
      vm.put("turn", "OPPONENTS TURN");
    }


    board.getValidMoves();
     */

    // Give freemarker the board
    vm.put(BOARD_KEY, board);

    // Render the Game View
    currentSession.attribute(BOARD_KEY, board);
    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }

  private boolean gameInProgress(Request request, Response response, Player currentPlayer) {
    // Check if the current Player is in a game
    if (!currentPlayerInGame(currentPlayer)) return false;

    // Get the gameID as a string
    String gameIDString = request.queryParams(GAME_ID_KEY);

    // If gameID is not given then redirect to the game
    if (gameIDString == null) {
      // The NullPointerException should never occur because we already know that the currentPlayer
      // is in a game, meaning the game should be found and every game has a gameID.
      response.redirect(GAME_URL + "?gameID=" + GameCenter.getGame(currentPlayer).getGameID());
      return false;
    }

    // gameID must be given, so now validate that the gameID is a UUID
    UUID gameID;
    try{
       gameID = UUID.fromString(gameIDString);

    } catch (IllegalArgumentException exception){
      // gameID is not a valid UUID so we redirect home.
      // TODO: we should eventually give user error message
      return false;
    }


    return true;
  }

  private boolean currentPlayerInGame(Player currentPlayer) {
    if (currentPlayer == null) return false;

    if (!currentPlayer.inGame()) return false; // IDE might tell you to simplify, don't!

    return true;
  }
}
