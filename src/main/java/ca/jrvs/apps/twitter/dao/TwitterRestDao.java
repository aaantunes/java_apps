package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.Util.JsonUtil;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dto.Tweet;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TwitterRestDao implements CrdRepository<Tweet, String> {

    //URI constants
    private final static String API_BASE_URI = "https://api.twitter.com";
    private final static String POST_PATH = "/1.1/statuses/update.json";
    private final static String SHOW_PATH = "/1.1/statuses/show.json";
    private final static String DELETE_PATH = "/1.1/statuses/destroy/";
    //URI symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";
    //Response Code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    public TwitterRestDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public Tweet create(Tweet tweet) throws OAuthException, IOException {
        //Construct URI
        URI uri = null;
        try {
            uri = getPostUri(tweet);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Execute HTTP request
        HttpResponse response = httpHelper.httpPost(uri);
        return parseResponseBody(response, HTTP_OK);
    }

    @Override
    public Tweet findById(String s) throws OAuthException, IOException {
        //Construct URI
        URI uri = null;
        try {
            uri = getShowUri(s);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpResponse response = httpHelper.httpGet(uri);
        return parseResponseBody(response, HTTP_OK);
    }

    @Override
    public Tweet deleteById(String s) throws OAuthException, IOException {
        URI uri = null;
        try {
            uri = getDeleteUri(s);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpResponse response = httpHelper.httpPost(uri);
        return parseResponseBody(response, HTTP_OK);
    }

    private URI getPostUri(Tweet tweet) throws URISyntaxException, UnsupportedEncodingException {
        String text = tweet.getText();
        Double longitude = tweet.getCoordinates().getCoordinates().get(0);
        Double latitude = tweet.getCoordinates().getCoordinates().get(1);

        StringBuilder sb = new StringBuilder();
        sb.append(API_BASE_URI).append(POST_PATH).append(QUERY_SYM);

        appendQueryParam(sb, "status", URLEncoder.encode(text, StandardCharsets.UTF_8.name()), true);
        appendQueryParam(sb, "long", longitude.toString(), false);
        appendQueryParam(sb, "lat", latitude.toString(), false);

        return new URI(sb.toString());
    }

    private URI getShowUri(String id) throws URISyntaxException, UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append(API_BASE_URI).append(SHOW_PATH).append(QUERY_SYM);
        appendQueryParam(sb, "id", id, true);
        return new URI(sb.toString());
    }

    private URI getDeleteUri(String id) throws URISyntaxException, UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        sb.append(API_BASE_URI).append(DELETE_PATH).append(id).append(".json");
        return new URI(sb.toString());
    }

    private void appendQueryParam(StringBuilder sb, String key, String value, boolean firstParam) {
        if (!firstParam) {
            sb.append(AMPERSAND);
        }
        sb.append(key).append(EQUAL).append(value);
    }

    protected Tweet parseResponseBody(HttpResponse response, int expectedStatusCode) {
        Tweet tweet = null;

        //Check response status
        int status = response.getStatusLine().getStatusCode();
        if (status != expectedStatusCode) {
            try {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                System.out.println("Response has no entity");
            }
            throw new RuntimeException("Unexpected HTTP Status: " + status);
        }

        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }

        //Convert Response Entity to String
        String jsonStr;
        try {
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert entity to String", e);
        }

        //Deserialize JSON string to Tweet Object
        try {
            tweet = JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to convert JSON string to tweet object" + e);
        }
        return tweet;
    }
}