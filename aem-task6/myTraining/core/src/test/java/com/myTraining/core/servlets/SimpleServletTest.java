package com.myTraining.core.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletRequest;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.myTraining.core.services.SimpleInt;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class SimpleServletTest {

    private final AemContext context = new AemContext();
    private SimpleServlet fixture;

    @BeforeEach
    void setUp() {
        // Mocking SimpleInt service
        SimpleInt simpleIntMock = mock(SimpleInt.class);
        when(simpleIntMock.getHelloWorld()).thenReturn("Mocked Hello World");

        // Register the mock service with the context
        context.registerService(SimpleInt.class, simpleIntMock);

        // Adapt the servlet from the context to ensure service injection works
        fixture = context.registerInjectActivateService(new SimpleServlet());
    }

    @Test
    void doGet() throws ServletException, IOException {
        context.build().resource("/content/test", "jcr:title", "resource title").commit();
        context.currentResource("/content/test");

        MockSlingHttpServletRequest request = context.request();
        MockSlingHttpServletResponse response = context.response();

        fixture.doGet(request, response);

        String output = response.getOutputAsString();
        assertEquals("Title = resource titlehello serviceMocked Hello World", output.trim());
    }
}