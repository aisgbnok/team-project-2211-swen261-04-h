package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.application.SessionTimeoutWatchdog;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import static spark.Spark.halt;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Login page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class PostSignInRoute implements Route {
  private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");
  static final String PLAYER_KEY = "playerServices";
  static final String TIMEOUT_SESSION_KEY = "timeoutWatchdog";

  // The length of the session timeout in seconds
  static final int SESSION_TIMEOUT_PERIOD = 120;
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public PostSignInRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetSignInRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {

    final Session httpSession = request.session();

    LOG.finer("GetSignInRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();

    String param = request.queryParams("playerName");
    param = param.strip();
    if (param.length() == 0){
        response.redirect(WebServer.LOGIN_URL);
        halt();
        return null;
    }
    if (PlayerLobby.addPlayer(new Player(param))){
        // get the object that will provide client-specific services for this player
        final Player playerService = PlayerLobby.getPlayer(param);
        httpSession.attribute(PLAYER_KEY, playerService);

        // setup session timeout. The valueUnbound() method in the SessionTimeoutWatchdog will
        // be called when the session is invalidated. The next invocation of this route will
        // have a new Session object with no attributes.
        httpSession.attribute(TIMEOUT_SESSION_KEY, new SessionTimeoutWatchdog(playerService));
        httpSession.maxInactiveInterval(SESSION_TIMEOUT_PERIOD);
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;

    }
    else {
    return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }
  }
}
