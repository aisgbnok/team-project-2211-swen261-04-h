package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.util.Message;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * UI Controller that handles reverting the previously validated move. {@code POST /backupMove}.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class PostBackupMoveRoute implements Route {
  private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());

  // Global GSON instance
  private final Gson gson;

  /**
   * Create the Spark Route (UI controller) to handle all {@code POST /backupMove} HTTP requests.
   *
   * @param gson The GSON instance used for communicating messages to Webpage Javascript
   */
  public PostBackupMoveRoute(final Gson gson) {
    this.gson = Objects.requireNonNull(gson, "gson is required");

    LOG.config("PostBackupMoveRoute is initialized.");
  }

  /**
   * Handle reverting last validated move and returning appropriate json ajax response.
   *
   * @param request The HTTP request
   * @param response The HTTP response
   * @return Json Ajax response
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("PostBackupMoveRoute is invoked.");

    // Get the Game
    UUID uuid = gson.fromJson(request.queryParams("gameID"), UUID.class);
    Game game = GameCenter.getGame(uuid);

    // TODO: add once backupMove method is written
    // return new Gson().toJson(game.backupMove());
    return Message.info("True?");
  }
}
