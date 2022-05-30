package com.myproject.aem.core.models;

import com.myproject.aem.core.models.impl.EventDetailsImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.models.factory.ModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.osgi.framework.Constants;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class EventDetailsImplTest {

    private final AemContext ctx = new AemContext(ResourceResolverType.JCR_MOCK);

    @Mock
    private ModelFactory modelFactory;

    private EventDetailsImpl eventDetails;


    @BeforeEach
    void setUp() throws Exception {
        eventDetails = new EventDetailsImpl();
        ctx.addModelsForClasses(EventDetailsImpl.class);
        ctx.load().json("/models/EventDetailsImplTest.json", "/content");
        ctx.registerService(ModelFactory.class, modelFactory,
                Constants.SERVICE_RANKING, Integer.MAX_VALUE);
    }

    @Test
    public void testDate() {
        final String expected = "Puneeth";
        ctx.currentResource("/content/eventdetails");
        EventDetails byline = ctx.request().adaptTo(EventDetails.class);
        String actual = byline.getName();
        assertEquals(expected, actual);
    }

    @Test
    public void testEventDate() {
        final String expected = "23 May 2022";
        ctx.currentResource("/content/eventdetails");
        EventDetails byline = ctx.request().adaptTo(EventDetails.class);
        String actual = byline.getDate();
        assertEquals(expected, actual);
    }
}
