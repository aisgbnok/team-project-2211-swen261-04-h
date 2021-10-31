package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Message;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.GetHomeRoute.CURRENT_PLAYER;

/**
 * The UI Controller to POST the resign route.
 *
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 */
public class PostResignRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());


    // TODO improve documentation


    public PostResignRoute() {
    }

    // TODO improve documentation

    /**
     * Resign and redirect to the WebCheckers Home page.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("PostResignRoute is invoked.");

        final Session httpSession = request.session();
        Player player = httpSession.attribute(CURRENT_PLAYER);

        // TODO actually implement player resignation
        // For now player is signed out
       // PlayerLobby.removePlayer(player);
       // httpSession.removeAttribute(CURRENT_PLAYER);

       player.setGame(false);

       Objects.requireNonNull(GameCenter.findGame(player)).active = false;
        response.redirect(WebServer.HOME_URL);
        return Message.info("true");
    }
}
