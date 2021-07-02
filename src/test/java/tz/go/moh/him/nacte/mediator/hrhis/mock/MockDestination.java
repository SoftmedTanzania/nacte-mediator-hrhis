package tz.go.moh.him.nacte.mediator.hrhis.mock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import org.openhim.mediator.engine.testing.MockHTTPConnector;
import tz.go.moh.him.nacte.mediator.hrhis.HrhisOrchestratorTest;
import tz.go.moh.him.nacte.mediator.hrhis.domain.HrhisRequest;
import tz.go.moh.him.nacte.mediator.hrhis.gsonTypeAdapter.AttributeSummaryTypeDeserializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

/**
 * Represents a mock destination.
 */
public class MockDestination extends MockHTTPConnector {

    /**
     * Initializes a new instance of the {@link MockDestination} class.
     */
    public MockDestination() {
    }

    /**
     * Gets the response.
     *
     * @return Returns the response.
     */
    @Override
    public String getResponse() {
        return null;
    }

    /**
     * Gets the status code.
     *
     * @return Returns the status code.
     */
    @Override
    public Integer getStatus() {
        return 200;
    }

    /**
     * Gets the HTTP headers.
     *
     * @return Returns the HTTP headers.
     */
    @Override
    public Map<String, String> getHeaders() {
        return Collections.emptyMap();
    }

    /**
     * Handles the message.
     *
     * @param msg The message.
     */
    @Override
    public void executeOnReceive(MediatorHTTPRequest msg) {


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(HrhisRequest.SummaryType.class, new AttributeSummaryTypeDeserializer());
        gsonBuilder.registerTypeAdapter(HrhisRequest.SummaryType.class, new AttributeSummaryTypeDeserializer());
        Gson gson = gsonBuilder.create();


        if (msg.getUri().contains("/enrollmenthealth")) {
            InputStream stream = HrhisOrchestratorTest.class.getClassLoader().getResourceAsStream("enrollments_request.json");
            Assert.assertNotNull(stream);
            HrhisRequest expected;
            try {
                expected = gson.fromJson(IOUtils.toString(stream), HrhisRequest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Assert.assertTrue(msg.getUri().contains("/enrollmenthealth/" + expected.getAcademicYear() + "-" + expected.getPageNumber() + "-" + expected.getPageSize() + "-" + expected.getSummary() + "/token"));
        } else if (msg.getUri().contains("/graduateshealth/")) {
            InputStream stream = HrhisOrchestratorTest.class.getClassLoader().getResourceAsStream("enrollments_request.json");
            Assert.assertNotNull(stream);
            HrhisRequest expected;
            try {
                expected = gson.fromJson(IOUtils.toString(stream), HrhisRequest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Assert.assertTrue(msg.getUri().contains("/graduateshealth/" + expected.getAcademicYear() + "-" + expected.getPageNumber() + "-" + expected.getPageSize() + "-" + expected.getSummary() + "/token"));
        } else if (msg.getUri().contains("/institutionsdetailshas")) {
            Assert.assertTrue(msg.getUri().contains("/institutionsdetailshas/token"));
        } else if (msg.getUri().contains("/hasteachingstaff/")) {
            InputStream stream = HrhisOrchestratorTest.class.getClassLoader().getResourceAsStream("enrollments_request.json");
            Assert.assertNotNull(stream);
            HrhisRequest expected;
            try {
                expected = gson.fromJson(IOUtils.toString(stream), HrhisRequest.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Assert.assertTrue(msg.getUri().contains("/hasteachingstaff/" + expected.getInstitutionCode() + "/token"));
        }

    }
}
