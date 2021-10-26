package com.webcheckers.ui;

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

    // If user isn't signed in then return home
    if (httpSession.attribute(CURRENT_USER) == null) {
      response.redirect(WebServer.HOME_URL);
      return null;
    }

    // TODO: Should these be private global variables?
    // Get currentUser
    Player currentUser = httpSession.attribute(CURRENT_USER);
    Player opponent = PlayerLobby.getPlayer(request.queryParams("opponent"));

    // Create a game
    Game game = httpSession.attribute(GAME_KEY);
    BoardView board = httpSession.attribute(BOARD_KEY);
    if (game == null) {
      // If there is no board then create one
      if (board == null) {
        board = new BoardView();
        httpSession.attribute(BOARD_KEY, board);
      }

      game = new Game(currentUser, opponent, board);
      httpSession.attribute(GAME_KEY, game);
    }

    vm.put("currentUser", currentUser);
    vm.put("redPlayer", currentUser);
    vm.put("whitePlayer", opponent);

    if (board.getTurn().equals("OPPONENT")) {
      vm.put("activeColor", Color.RED);
    } else {
      vm.put("activeColor", Color.WHITE);
    }
    vm.put("viewMode", viewModes.PLAY);

    LOG.finer(request.queryParams("opponent"));
    if (request.queryParams("opponent") != null) {
      opponent = PlayerLobby.getPlayer(request.queryParams("opponent"));
      vm.put("whitePlayer", opponent);
    }

    vm.put("board", board);
    board.fillRed();
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
     */

    // render the View
    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }
}
