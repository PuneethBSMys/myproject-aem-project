package com.myproject.aem.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
        name = "Event Details Configuration",
        description = "This configuration reads the values to make an Event Registration")
public @interface EventDetailsConfiguration {

    @AttributeDefinition(
            name = "Endpoint",
            description = "Enter the endpoint",
            defaultValue = "https://6294915863b5d108c18e3554.mockapi.io/api/eventregistration/eventRegister")
    public String getEndpoint();

}
