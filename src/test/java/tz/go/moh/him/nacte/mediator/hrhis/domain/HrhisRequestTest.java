package tz.go.moh.him.nacte.mediator.hrhis.domain;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import tz.go.moh.him.nacte.mediator.hrhis.HrhisOrchestratorTest;

import java.io.IOException;
import java.io.InputStream;

/**
 * Contains tests for the {@link HrhisRequest} class.
 */
public class HrhisRequestTest {
    /**
     * Tests the deserialization of an HRHIS request.
     */
    @Test
    public void testDeserializeHfrRequest() {
        InputStream stream = HrhisOrchestratorTest.class.getClassLoader().getResourceAsStream("request.json");

        Assert.assertNotNull(stream);

        String data;

        try {
            data = IOUtils.toString(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assert.assertNotNull(data);

        Gson gson = new Gson();

        HrhisRequest hrhisRequest = gson.fromJson(data, HrhisRequest.class);

        Assert.assertEquals(2020, hrhisRequest.getAcademicYear());
        Assert.assertEquals(1, hrhisRequest.getPageNumber());
        Assert.assertEquals(100, hrhisRequest.getPageSize());
        Assert.assertEquals(0, hrhisRequest.getSummary());
    }
}