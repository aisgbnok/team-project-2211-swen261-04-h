package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Piece.Color;
import com.webcheckers.model.Player;
import spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Game page.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class GetGameRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

  private enum viewMode {
    PLAY,
    SPECTATOR,
    REPLAY
  }

  static final String PLAYER_KEY = "playerServices";

  private Player currentUser;
  private static viewMode viewMode = GetGameRoute.viewMode.PLAY;
  // private final Map<String, Object> modeOptionsAsJSON;
  private final TemplateEngine templateEngine;
  private PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public GetGameRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");

    //
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
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Game");

    Player opponent;

    // Ensure player is logged in
    if (httpSession.attribute(PLAYER_KEY) != null) {
      currentUser = httpSession.attribute(PLAYER_KEY);
      vm.put("current_player", currentUser.getName());
      vm.put("currentUser", currentUser);
      vm.put("redPlayer", currentUser);
      vm.put("activeColor", Color.RED);
      vm.put("viewMode", viewMode);

      LOG.finer(request.queryParams("opponent"));
      if(request.queryParams("opponent") !=null) {
        opponent = playerLobby.getPlayer(request.queryParams("opponent"));
        vm.put("whitePlayer", opponent);
      }



      BoardView board = new BoardView();
      vm.put("board", board);
      board.fillRed();







    } else {
      response.redirect("/");
    }
    // render the View
    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }
}
