package tz.go.moh.him.nacte.mediator.hrhis.gsonTypeAdapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import tz.go.moh.him.nacte.mediator.hrhis.domain.HrhisRequest;

import java.lang.reflect.Type;

/**
 * Gson Custom Json Deserializer for {@link HrhisRequest.SummaryType} enums
 */
public class AttributeSummaryTypeDeserializer implements JsonDeserializer<HrhisRequest.SummaryType> {
    @Override
    public HrhisRequest.SummaryType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        HrhisRequest.SummaryType[] summaryTypes = HrhisRequest.SummaryType.values();
        for (HrhisRequest.SummaryType summaryType : summaryTypes) {
            if (summaryType.getValue() == json.getAsInt())
                return summaryType;
        }
        return null;
    }
}