package ca.jrvs.apps.twitter.Util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JsonUtil {

    /**
     * Convert a java object to JSON string
     *
     * @param object            input object
     * @param prettyJson
     * @param includeNullValues
     * @return JSON String
     * @throws JsonProcessingException
     */
    public static String toJson(Object object, boolean prettyJson, boolean includeNullValues) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if (!includeNullValues) {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        if (prettyJson) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
        return mapper.writeValueAsString(object);
    }

    /**
     * Parse JSON string to a object
     *
     * @param json JSON str
     * @param c    object class
     * @param <T>  Type
     * @return Object
     * @throws IOException
     */
    public static <T> T toObjectFromJson(String json, Class<T> c) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return (T) mapper.readValue(json, c);
    }
}
