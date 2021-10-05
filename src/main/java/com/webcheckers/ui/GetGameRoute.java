package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Message;
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
  };

  private PlayerLobby playerLobby;

  private final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  static final String PLAYER_KEY = "playerServices";

  // TODO: Make enumeration
  private final Player currentUser;
  private final String viewMode = "PLAY";
  // private final Map<String, Object> modeOptionsAsJSON;

  private final TemplateEngine templateEngine;

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
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    vm.put("message", WELCOME_MSG);
    if (httpSession.attribute(PLAYER_KEY) != null) {
      vm.put("current_player", ((Player) httpSession.attribute(PLAYER_KEY)).getName());
      vm.put("count", "");

    } else {
      vm.put("current_player", "");
      vm.put("count", PlayerLobby.size());
    }
    ArrayList<Player> players = PlayerLobby.getPlayers();
    if (players != null) {
      if (!players.isEmpty()) {
        StringBuilder list_construction = new StringBuilder();
        list_construction.append("<ul>");
        for (Player player : players) {
          if (!player.equals(((Player) httpSession.attribute(PLAYER_KEY)))) {
            list_construction.append("<li>").append(player.getName()).append("</li>");
          }
        }
        list_construction.append("</ul>");
        vm.put("all_players", list_construction.toString());
      } else {
        vm.put("all_players", "");
      }
    } else {
      vm.put("all_players", "");
    }
    // render the View
    return templateEngine.render(new ModelAndView(vm, "game.ftl"));
  }
}
