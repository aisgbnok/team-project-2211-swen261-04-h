package com.webcheckers.ui;

import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetHomeRoute.CURRENT_PLAYER_KEY;

/**
 * The UI Controller to POST the user sign in.
 *
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 * @author <a href='mailto:idc7947@rit.edu'>Ian Chasse</a>
 */
public class PostSignInRoute implements Route {
    // Console Logger
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

    // Player Name Error Messages
    private static final Message PLAYER_NAME_EMPTY = Message.error("Player Name is empty!");
    private static final Message PLAYER_NAME_INVALID =
            Message.error("Player Name invalid, use alphanumeric characters!");
    private static final Message PLAYER_NAME_TAKEN =
            Message.error("Player Name taken, select another!");

    // The length of the session timeout in seconds?
    //  static final int SESSION_TIMEOUT_PERIOD = 120;
    //  static final String TIMEOUT_SESSION_KEY = "timeoutWatchdog";

    // TemplateEngine used for HTML page rendering
    private final TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /signin} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public PostSignInRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");

        LOG.config("PostSignInRoute is initialized.");
    }

    /**
     * Render the WebCheckers Sign In page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Sign-In page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostSignInRoute is invoked.");

        final Session httpSession = request.session();
        Map<String, Object> vm = new HashMap<>();

        // Set the title
        vm.put("title", "Sign In");

        // Get Player Name
        String playerName = request.queryParams("playerName").strip();

        // Handle playerName Validation Checking
        if (playerName.isEmpty()) {
            // If playerName is empty, notify the user.
            vm.put("message", PLAYER_NAME_EMPTY);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        } else if (!playerName.matches("^[a-zA-Z0-9 _]*$")) {
            // If playerName is invalid, notify the user.
            vm.put("message", PLAYER_NAME_INVALID);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        } else if (PlayerLobby.contains(playerName)) {
            // If playerName is already taken, notify the user.
            vm.put("message", PLAYER_NAME_TAKEN);
            return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
        }

        // Username passed validation
        // Now we can create a new Player
        final Player currentPlayer = new Player(playerName);
        // We can also register that player with the PlayerLobby
        PlayerLobby.addPlayer(currentPlayer);

        // Set the httpSession CURRENT_USER attribute to the currentPlayer
        httpSession.attribute(CURRENT_PLAYER_KEY, currentPlayer);

        // TODO: This needs to be implemented much better, before actually including!
        // This seems to set the session timeout to 120 seconds (2 minutes). This is great; however, we
        // don't properly remove the current player from the playerLobby or GameCenter yet.
        // Somebody tell me if i'm wrong?!

    /*
    // setup session timeout. The valueUnbound() method in the SessionTimeoutWatchdog will
    // be called when the session is invalidated. The next invocation of this route will
    // have a new Session object with no attributes.
    httpSession.attribute(TIMEOUT_SESSION_KEY, new SessionTimeoutWatchdog(currentPlayer));
    httpSession.maxInactiveInterval(SESSION_TIMEOUT_PERIOD);
    halt();*/
        response.redirect(WebServer.HOME_URL);
        return null;
    }
}
