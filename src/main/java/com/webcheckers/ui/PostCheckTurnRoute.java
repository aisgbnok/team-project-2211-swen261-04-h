package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Message;
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
public class PostCheckTurnRoute implements Route {
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
     * @param request  the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {

        final Session httpSession = request.session();

        LOG.finer("GetSignInRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        Gson gson = new Gson();
        Message message;
        BoardView board = httpSession.attribute("BOARD");
        if (Objects.equals(board.getTurn(), "SELF")){
            message = Message.info("true");
        } else {
            message = Message.info("false");
        }
        return gson.toJson(message);

    }
}
