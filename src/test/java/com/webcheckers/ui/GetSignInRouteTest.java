package com.webcheckers.ui;

import com.webcheckers.model.Player;
import com.webcheckers.ui.GetSignInRoute;
import com.webcheckers.ui.TemplateEngineTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;
import org.junit.jupiter.api.Tag;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
public class GetSignInRouteTest {
    private GetSignInRoute CuT;

    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);

        CuT = new GetSignInRoute(engine);

    }

    @Test
    public void handle(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Sign In");

        testHelper.assertViewName("signin.ftl");
    }
}
