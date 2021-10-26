package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Sign-In page.
 *
 * @author <a href='mailto:jwd2488@rit.edu'>Jake Downie</a>
 * @author <a href='mailto:ajs2576@rit.edu'>Anthony Swierkosz</a>
 */
public class GetSignInRoute implements Route {
  // Console Logger
  private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

  // TemplateEngine used for HTML page rendering
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /signin} HTTP requests.
   *
   * @param templateEngine the HTML template rendering engine
   */
  public GetSignInRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");

    LOG.config("GetSignInRoute is initialized.");
  }

  /**
   * Render the WebCheckers Sign-In page
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @return the rendered HTML for the Sign-In page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetSignInRoute is invoked.");
    Map<String, Object> vm = new HashMap<>();

    // Set the title
    vm.put("title", "Sign In");

    // Render the Sign-In page view
    return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
  }
}
