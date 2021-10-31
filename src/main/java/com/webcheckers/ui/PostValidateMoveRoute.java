package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Message;
import com.webcheckers.model.Move;
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
public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());
    private final TemplateEngine templateEngine;

    public PostValidateMoveRoute(TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetSignInRoute is initialized.");
    }


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
        //
        Map<String, Object> vm = new HashMap<>();

        String param = request.queryParams("actionData");
        Gson gson = new Gson();
        Move newMove = gson.fromJson(param, Move.class);


        BoardView board = httpSession.attribute("BOARD");

        Message message;
        if (board.getValidMoves().contains(newMove)) {
            message = Message.info("true");
            board.proposedMove = newMove;
        } else {
            message = Message.info("false");
        }
        return gson.toJson(message);

    }
}
