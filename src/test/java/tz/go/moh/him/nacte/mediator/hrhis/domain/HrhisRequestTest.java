package tz.go.moh.him.nacte.mediator.hrhis.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import tz.go.moh.him.nacte.mediator.hrhis.HrhisOrchestratorTest;
import tz.go.moh.him.nacte.mediator.hrhis.gsonTypeAdapter.AttributeSummaryTypeDeserializer;
import tz.go.moh.him.nacte.mediator.hrhis.gsonTypeAdapter.AttributeSummaryTypeSerializer;

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

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(HrhisRequest.SummaryType.class, new AttributeSummaryTypeDeserializer());
        Gson gson = gsonBuilder.create();

        HrhisRequest hrhisRequest = gson.fromJson(data, HrhisRequest.class);

        Assert.assertEquals(2020, hrhisRequest.getAcademicYear());
        Assert.assertEquals(1, hrhisRequest.getPageNumber());
        Assert.assertEquals(100, hrhisRequest.getPageSize());
        Assert.assertEquals(0, hrhisRequest.getSummary());
    }


    /**
     * Tests the getters and setters of the HRHIS request.
     */
    @Test
    public void testGettersAndSettersHfrRequest() {

        HrhisRequest hrhisRequest = new HrhisRequest();

        hrhisRequest.setAcademicYear(2020);
        Assert.assertEquals(2020, hrhisRequest.getAcademicYear());

        hrhisRequest.setPageNumber(1);
        Assert.assertEquals(1, hrhisRequest.getPageNumber());

        hrhisRequest.setPageSize(100);
        Assert.assertEquals(100, hrhisRequest.getPageSize());

        hrhisRequest.setSummary(HrhisRequest.SummaryType.RESULTS);
        Assert.assertEquals(0, hrhisRequest.getSummary());

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(HrhisRequest.SummaryType.class, new AttributeSummaryTypeSerializer());
        Gson gson = gsonBuilder.create();

        System.out.println("value = " + gson.toJson(hrhisRequest));


        hrhisRequest.setSummary(HrhisRequest.SummaryType.SUMMARY);
        Assert.assertEquals(1, hrhisRequest.getSummary());

    }
}