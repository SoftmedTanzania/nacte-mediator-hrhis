package tz.go.moh.him.nacte.mediator.hrhis.gsonTypeAdapter;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import tz.go.moh.him.nacte.mediator.hrhis.domain.HrhisRequest;

import java.lang.reflect.Type;

/**
 * Gson Custom Json Serializer for {@link HrhisRequest.SummaryType} enums
 */
public class AttributeSummaryTypeSerializer implements JsonSerializer<HrhisRequest.SummaryType> {
    @Override
    public JsonElement serialize(HrhisRequest.SummaryType summaryType, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(summaryType.getValue());
    }
}