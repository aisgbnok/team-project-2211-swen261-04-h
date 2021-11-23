package com.webcheckers.ui;

import static com.webcheckers.ui.GetHomeRoute.CURRENT_PLAYER_KEY;
import static com.webcheckers.ui.PostStartGameRoute.BOARD_KEY;
import static com.webcheckers.ui.PostStartGameRoute.OPPONENT_PLAYER_KEY;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Color;
import com.webcheckers.model.Game;
import com.webcheckers.model.Game.viewModes;
import com.webcheckers.model.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.TemplateEngine;

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

  // GameCenter used for coordinating games
  private final GameCenter gameCenter;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /game} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine
   * @param gameCenter the GameCenter used for holding all games
   */
  public GetGameRoute(final TemplateEngine templateEngine, final GameCenter gameCenter) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");

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

    // Get currentPlayer and opponentPlayer
    Player currentPlayer = currentSession.attribute(CURRENT_PLAYER_KEY);

    // If game isn't in progress then return
    Game game = gameInProgress(request, response, currentPlayer);
    if (game == null) {
      response.redirect(WebServer.HOME_URL);
      return null;
    }

    // Setup Opponent
    Player opponentPlayer = setOpponent(currentPlayer, game, currentSession);

    // Now that we know a game is in progress let's render the game
    Board board = game.getBoard(game.getPlayerColor(currentPlayer));

    vm.put(CURRENT_PLAYER_KEY, currentPlayer);

    if (game.getPlayerColor(currentPlayer) == Color.RED) {
      vm.put(RED_PLAYER_KEY, currentPlayer);
      vm.put(WHITE_PLAYER_KEY, opponentPlayer);

    } else if (game.getPlayerColor(currentPlayer) == Color.WHITE) {
      vm.put(RED_PLAYER_KEY, opponentPlayer);
      vm.put(WHITE_PLAYER_KEY, currentPlayer);
    }

    // board.fill(game.getPlayerColor(currentPlayer));

    vm.put("viewMode", viewModes.PLAY);
    vm.put("activeColor", game.getActiveColor());
    vm.put("gameID", "\"" + game.getGameID() + "\"");

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

    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }

  private Game gameInProgress(Request request, Response response, Player currentPlayer) {
    // Check if the current Player is in a game
    if (!currentPlayerInGame(currentPlayer)) return null;

    // Get the gameID as a string
    String gameIDString = request.queryParams(GAME_ID_KEY);

    // TODO: As of now we return null which causes the if statement above to redirect to home, and
    // then home redirects us to game with the proper gameID. Need to think about how to improve
    // this.

    // If gameID is not given then redirect to the game
    if (gameIDString == null) {
      // The NullPointerException should never occur because we already know that the currentPlayer
      // is in a game, meaning the game should be found and every game has a gameID.
      // response.redirect(GAME_URL + "?gameID=" + GameCenter.getGame(currentPlayer).getGameID());
      return null;
    }

    // gameID must be given, so now validate that the gameID is a UUID
    UUID gameID;
    try {
      gameID = UUID.fromString(gameIDString);

    } catch (IllegalArgumentException exception) {
      // gameID is not a valid UUID so we redirect home.
      // TODO: we should eventually give user error message
      return null;
    }

    // gameID is given, and it is a valid UUID, so return game from UUID
    return gameCenter.getGame(gameID);
  }

  private boolean currentPlayerInGame(Player currentPlayer) {
    if (currentPlayer == null) return false;

    if (!currentPlayer.inGame()) return false; // IDE might tell you to simplify, don't!

    return true;
  }

  private Player setOpponent(Player currentPlayer, Game game, Session currentSession) {
    Player opponentPlayer = currentSession.attribute(OPPONENT_PLAYER_KEY);

    if (opponentPlayer == null) {
      opponentPlayer = game.getOpponent(currentPlayer);
      currentSession.attribute(OPPONENT_PLAYER_KEY, opponentPlayer);
    }

    return opponentPlayer;
  }
}
