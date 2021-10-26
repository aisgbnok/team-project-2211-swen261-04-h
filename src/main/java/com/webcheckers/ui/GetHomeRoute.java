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
  static final String CURRENT_USER = "currentUser";

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

    // Set the title
    vm.put("title", "Welcome!");

    // Display welcome message on the Home page
    vm.put("message", WELCOME_MSG);

    // Get the currentUser from the CURRENT_USER attribute
    Player currentUser = httpSession.attribute(CURRENT_USER);

    // If a user is signed in
    if (currentUser != null) {

      // If the user is in a game
      if (currentUser.inGame()) {
        response.redirect(
            WebServer.GAME_URL + "?gameID=" + GameCenter.findGame(currentUser).getGameID());
        return null;
      }

      // Set the currentUser to the CURRENT_USER name
      vm.put("currentUser", currentUser);

      // Set the playerCount to PlayerLobby size, minus one to account for current user.
      vm.put("playerCount", (PlayerLobby.size() - 1));

    } else {
      // If there is no player signed in
      // Set the currentUser to null
      vm.put("currentUser", null);

      // Set the playerCount to PlayerLobby size
      vm.put("playerCount", PlayerLobby.size());
    }

    // TODO: Improve
    // Get players from PlayerLobby
    ArrayList<Player> players = PlayerLobby.getPlayers();
    // If players is not null
    if (players != null) {
      // if players is not empty
      if (!players.isEmpty()) {
        // Create a new ArrayList of Strings for storing the HTML player list
        ArrayList<String> list_construction = new ArrayList<>();
        // For each player except the current player generate an HTML list entry
        for (Player player : players) {
          if (!player.equals(currentUser)) {
            list_construction.add(player.getName());
          }
        }
        if (list_construction.isEmpty()) {
          // Set currentPlayers to null
          vm.put("currentPlayers", null);
        } else {
          // Set currentPlayers to the list_construction
          vm.put("currentPlayers", list_construction);
        }

      } else {
        // Set currentPlayers to null
        vm.put("currentPlayers", null);
      }
    } else {
      // Set currentPlayers to null
      vm.put("currentPlayers", null);
    }

    // Render the Home page view
    return templateEngine.render(new ModelAndView(vm, "home.ftl"));
  }
}
