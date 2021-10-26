package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.*;
import com.webcheckers.model.Piece.Color;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetHomeRoute.PLAYER_KEY;

/**
 * The UI Controller to GET the Game page.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 */
public class GetGameRoute implements Route {
  // Console Logger
  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  // Possible viewModes
  private enum viewMode {
    PLAY,
    SPECTATOR,
    REPLAY
  }

  private Player currentUser;
  private static viewMode viewMode = GetGameRoute.viewMode.PLAY;
  // private final Map<String, Object> modeOptionsAsJSON;
  private final TemplateEngine templateEngine;
  private PlayerLobby playerLobby;

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

    Player opponent;

    // Ensure player is logged in
    if (httpSession.attribute(PLAYER_KEY) != null) {
      currentUser = httpSession.attribute(PLAYER_KEY);
      vm.put("current_player", currentUser.getName());
      vm.put("currentUser", currentUser);
      vm.put("redPlayer", currentUser);
      vm.put("whitePlayer", currentUser);

      BoardView board = httpSession.attribute("BOARD");
      if (board == null) {
        board = new BoardView();
      }
      if (board.getTurn().equals("OPPONENT")) {
        vm.put("activeColor", Color.RED);
      } else {
        vm.put("activeColor", Color.WHITE);
      }
      vm.put("viewMode", viewMode);

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

      httpSession.attribute("BOARD", board);

      /*
      String current_turn = board.getTurn();
      if (current_turn == currentUser.getName()){
        vm.put("turn", "YOUR TURN");
      } else {
        vm.put("turn", "OPPONENTS TURN");
      }
       */

    } else {
      response.redirect("/");
    }
    // render the View
    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }
}
