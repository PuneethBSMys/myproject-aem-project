package com.myproject.aem.core.models.impl;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;
import com.myproject.aem.core.models.EventDetails;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.factory.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = {EventDetails.class},
        resourceType = {EventDetailsImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class EventDetailsImpl implements EventDetails {
    protected static final String RESOURCE_TYPE = "wknd/components/eventdetails";

    @Self
    private SlingHttpServletRequest request;

    @OSGiService
    private ModelFactory modelFactory;

    @ScriptVariable
    private Page currentPage;

    @ScriptVariable
    protected ComponentContext componentContext;

    @ValueMapValue
    private String name;

    @ValueMapValue
    private String date;

    private static final Logger LOGGER = LoggerFactory.getLogger(EventDetailsImpl.class);

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDate() {
        DateTimeFormatter inputTF = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter();
        LocalDate parsedDate = LocalDate.parse(StringUtils.substringBefore(date, "T"), inputTF);
        DateTimeFormatter parsedTM = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return parsedTM.format(parsedDate);
    }

}