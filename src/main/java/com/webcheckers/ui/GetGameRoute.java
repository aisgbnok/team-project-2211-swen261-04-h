package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Game.viewModes;
import com.webcheckers.model.Message;
import com.webcheckers.model.Piece.Color;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetHomeRoute.CURRENT_PLAYER_KEY;
import static com.webcheckers.ui.GetHomeRoute.MESSAGE;

/**
 * The UI Controller to GET the Game page.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 */
public class GetGameRoute implements Route {


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
    final Session httpSession = request.session();
    Map<String, Object> vm = new HashMap<>();

    // Set the title
    vm.put("title", "Game");

    // Get currentPlayer
    Player currentPlayer = httpSession.attribute(CURRENT_PLAYER_KEY);
    Player opponentPlayer = httpSession.attribute(CURRENT_PLAYER_KEY);

    // If user isn't signed in then return home
    if (currentPlayer == null) {
      response.redirect(WebServer.HOME_URL);
      return null;
    }

    // Initialize Opponent
    Player opponent = null;

    // Initialize Game and Board
    Game game = null;
    Board board = null;

    // If the opponent query param is found then this is game setup
    if (request.queryParams(OPPONENT_KEY) != null) {

      opponent = PlayerLobby.getPlayer(request.queryParams(OPPONENT_KEY));
      if (opponent.inGame()) {
        httpSession.attribute(MESSAGE, Message.error(opponent.getName() + PLAYER_IN_GAME));
        response.redirect(WebServer.HOME_URL);
        return null;
      }
      httpSession.attribute(OPPONENT_KEY, opponent);

      game = new Game(currentPlayer, opponent);
      board = game.getBoard();
      GameCenter.addGame(game);
      httpSession.attribute(BOARD_KEY, board);
      httpSession.attribute(GAME_KEY, game);

      response.redirect(WebServer.GAME_URL + "?gameID=" + game.getGameID());
      return null;
    }

    if (request.queryParams("gameID") != null) {
      game = GameCenter.getGame(Integer.parseInt(request.queryParams("gameID")));
      board = game.getBoard();
      httpSession.attribute(GAME_KEY, game);
      httpSession.attribute(BOARD_KEY, board);

      opponent = httpSession.attribute(OPPONENT_KEY);

      if (opponent == null) {
        opponent = game.getOppositePlayer(currentPlayer);
        httpSession.attribute(OPPONENT_KEY, opponent);
      }
    } else {
      board = new Board();
    }

    // Set both players to  be in game
    currentPlayer.setGame(true);
    opponent.setGame(true);

    vm.put(CURRENT_PLAYER_KEY, currentPlayer);

    if (game.getPlayerColor(currentPlayer) == Color.RED) {
      vm.put("redPlayer", currentPlayer);
      vm.put("whitePlayer", opponent);
      board.fillRed();
    } else if (game.getPlayerColor(currentPlayer) == Color.WHITE) {
      vm.put("redPlayer", opponent);
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
    httpSession.attribute(BOARD_KEY, board);
    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }
}
