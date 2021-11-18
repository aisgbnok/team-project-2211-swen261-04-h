package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Login page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class PostSubmitTurnRoute implements Route {
  private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());
  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public void PostSignInRoute(final TemplateEngine templateEngine) {
    //
    LOG.config("GetSignInRoute is initialized.");
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

    Player sessionPlayer = request.session().attribute("Player");
    Game game = GameCenter.getGame(sessionPlayer);
    assert game != null;
    return game.submitTurn(sessionPlayer).toString();

  }
}
