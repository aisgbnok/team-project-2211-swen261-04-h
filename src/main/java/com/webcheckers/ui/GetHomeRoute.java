package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
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
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 * @author <a href='mailto:idc7947@rit.edu'>Ian Chasse</a>
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  // Console Logger
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  // User Welcome Message
  private static final Message WELCOME_MSG =
      Message.info("Welcome to the world of online Checkers.");

  // HTTP Attribute Keys
  public static final String CURRENT_PLAYER = "currentPlayer";
  public static final String CURRENT_PLAYERS = "currentPlayers";
  public static final String PLAYER_COUNT = "playerCount";

  // TemplateEngine used for HTML page rendering
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");

    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetHomeRoute is invoked.");
    final Session httpSession = request.session();
    Map<String, Object> vm = new HashMap<>();

    // Get the currentPlayer from the CURRENT_USER attribute
    Player currentPlayer = httpSession.attribute(CURRENT_PLAYER);

    // If a user is signed in and is in a game
    if (currentPlayer != null && currentPlayer.inGame()) {
      response.redirect(
          WebServer.GAME_URL + "?gameID=" + GameCenter.findGame(currentPlayer).getGameID());
      return null;
    }

    // Generate Player List
    ArrayList<String> playersHTML = new ArrayList<>();

    // For each player in player lobby that isn't the current user add them to the playersHTML list
    for (Player player : PlayerLobby.getPlayers()) {
      if (!player.equals(currentPlayer)) {
        playersHTML.add(player.getName());
      }
    }

    // Set the title
    vm.put("title", "Welcome!");

    // Display welcome message on the Home page
    vm.put("message", WELCOME_MSG);

    // Set the CURRENT_PLAYER to the currentPlayer name
    vm.put(CURRENT_PLAYER, currentPlayer);

    // Set the PLAYER_COUNT to playersHTML size
    vm.put(PLAYER_COUNT, playersHTML.size());

    // Set PLAYER_LIST to the playersHTML list
    vm.put(CURRENT_PLAYERS, playersHTML);

    // Render the Home page view
    return templateEngine.render(new ModelAndView(vm, "home.ftl"));
  }
}
