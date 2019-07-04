package ca.jrvs.apps.twitter.example;

import ca.jrvs.apps.twitter.example.dto.Dividend;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JsonParser {

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

    public static void main(String[] args) throws IOException {
        Dividend dividend = new Dividend();
        dividend.setExDate("2019-07-04");
        dividend.setPaymentDate("2019-07-05");
        dividend.setDeclaredDate("2019-07-04");
        dividend.setRecordDate("2019-07-04");
        dividend.setAmount(123456789);

        String string = toJson(dividend, true, true);
        System.out.println(string);

        Dividend dividend1 = toObjectFromJson(string, Dividend.class);
        System.out.println(dividend1);
    }
}
