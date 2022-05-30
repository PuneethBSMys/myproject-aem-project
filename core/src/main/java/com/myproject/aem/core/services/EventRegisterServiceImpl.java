package com.myproject.aem.core.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component(service = EventRegisterService.class,immediate=true)
@Designate(ocd = EventDetailsConfiguration.class)
public class EventRegisterServiceImpl implements  EventRegisterService{

    private static final Logger LOG = LoggerFactory.getLogger(EventRegisterServiceImpl.class);

    private EventDetailsConfiguration configuration;

    @Activate
    protected void activate(EventDetailsConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String makeHttpCall(String json) {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(configuration.getEndpoint());
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == 201){
                byte[] byteArray = EntityUtils.toByteArray(response.getEntity());
                String resp = new String(byteArray, StandardCharsets.UTF_8);
                JsonObject jsonParser = new JsonParser().parse(resp).getAsJsonObject();
                return jsonParser.get("status").getAsString();
            }
        }catch(IOException ex){
            LOG.error("IOException in EventRegisterService", ex);
        }
        return "Failure";
    }
}
