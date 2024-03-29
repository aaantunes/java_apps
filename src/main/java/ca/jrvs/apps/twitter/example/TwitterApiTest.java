package ca.jrvs.apps.twitter.example;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Arrays;

public class TwitterApiTest {

    private static String CONSUMER_KEY = "YnBeahbk7JIAGik0O6hGVe8gY";
    private static String CONSUMER_SECRET = "a1XzeGBYwDl9XuefZGcu11dFskKDJujjFB7frhHTn9yuT3VtKb";
    private static String ACCESS_TOKEN = "1144303513759096832-sjTCyYPAt05Xh0K990WUILFvolqowK";
    private static String TOKEN_SECRET = "V1cBQzj5dEnsjfNzSm8dSBtrg4ayoHaBvIlOv6Ge1AM4i";

    public static void main(String[] args) throws Exception {

        //Setup oauth
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);

        //Create an HTTP GET request
        HttpGet request = new HttpGet("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=AndreAn46482633");

        //Sign the request (add headers)
        consumer.sign(request);

        System.out.println("Http Request Headers:");

        Arrays.stream(request.getAllHeaders()).forEach(System.out::println);

        //Send/Execute the request
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
}
