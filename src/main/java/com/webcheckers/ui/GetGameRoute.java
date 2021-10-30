package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.model.Game.viewModes;
import com.webcheckers.model.Piece.Color;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetHomeRoute.CURRENT_USER;

/**
 * The UI Controller to GET the Game page.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 */
public class GetGameRoute implements Route {

  // Console Logger
  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  static final String GAME_KEY = "game";
  static final String BOARD_KEY = "board";
  static final String OPPONENT_KEY = "opponent";

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

    // TODO improve this in the next sprint. Very janky code.

    // TODO: Should these be private global variables?
    // Get currentUser
    Player currentUser = httpSession.attribute(CURRENT_USER);

    // If user isn't signed in then return home
    if (currentUser == null) {
      response.redirect(WebServer.HOME_URL);
      return null;
    }

    // Initialize Opponent
    Player opponent = null;

    // Initialize Game and Board
    Game game = null;
    BoardView board = null;

    // If the opponent query param is found then this is game setup
    if (request.queryParams("opponent") != null) {
      opponent = PlayerLobby.getPlayer(request.queryParams("opponent"));
      httpSession.attribute(OPPONENT_KEY, opponent);

      board = new BoardView();
      game = new Game(currentUser, opponent, board);
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
        opponent = game.getOppositePlayer(currentUser);
        httpSession.attribute(OPPONENT_KEY, opponent);
      }
    } else {
      board = new BoardView();
    }

    // Set both players to  be in game
    currentUser.setGame(true);
    opponent.setGame(true);

    vm.put("currentUser", currentUser);

    if (game.getPlayerColor(currentUser) == Color.RED) {
      vm.put("redPlayer", currentUser);
      vm.put("whitePlayer", opponent);
      board.fillRed();
    } else if (game.getPlayerColor(currentUser) == Color.WHITE) {
      vm.put("redPlayer", opponent);
      vm.put("whitePlayer", currentUser);
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
    if (current_turn == currentUser.getName()){
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
