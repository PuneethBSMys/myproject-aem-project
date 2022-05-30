package com.myproject.aem.core.workflow;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.google.gson.JsonObject;
import com.myproject.aem.core.services.EventRegisterService;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(property = {
        Constants.SERVICE_DESCRIPTION + "=Event Registration Form",
        "process.label" + "=Save Event Registration"
})
public class EventRegistrationWorkflow implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(EventRegistrationWorkflow.class);
    @Reference
    EventRegisterService eventRegisterService;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments)
            throws WorkflowException {
        String payloadPath = workItem.getWorkflowData().getPayload().toString();
        ResourceResolver resourceResolver = workflowSession.adaptTo(ResourceResolver.class);
        ValueMap vm = resourceResolver.getResource(payloadPath).adaptTo(ValueMap.class);
        JsonObject reqObj = new JsonObject();
        reqObj.addProperty("firstName", vm.get("firstName", String.class));
        reqObj.addProperty("lastName", vm.get("lastName", String.class));
        reqObj.addProperty("mobileNumber", vm.get("mobileNumber", String.class));
        reqObj.addProperty("email", vm.get("email", String.class));
        String resp = eventRegisterService.makeHttpCall(reqObj.toString());
        if(StringUtils.equalsIgnoreCase(resp, "success")){
            LOG.debug("Event Registration Successfully completed");
        } else {
            LOG.debug("Event Registration Failed");
        }

    }
}