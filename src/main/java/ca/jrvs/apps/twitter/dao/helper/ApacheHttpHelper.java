package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;

public class ApacheHttpHelper implements HttpHelper {
    private static String CONSUMER_KEY = "YnBeahbk7JIAGik0O6hGVe8gY";
    private static String CONSUMER_SECRET = "a1XzeGBYwDl9XuefZGcu11dFskKDJujjFB7frhHTn9yuT3VtKb";
    private static String ACCESS_TOKEN = "1144303513759096832-sjTCyYPAt05Xh0K990WUILFvolqowK";
    private static String TOKEN_SECRET = "V1cBQzj5dEnsjfNzSm8dSBtrg4ayoHaBvIlOv6Ge1AM4i";

    @Override
    public HttpResponse httpPost(URI uri) throws OAuthException, IOException {
        HttpPost request = new HttpPost(uri);
        createConsumerWithSecret().sign(request);
        return new DefaultHttpClient().execute(request);
    }

    @Override
    public HttpResponse httpPost(URI uri, StringEntity stringEntity) {
        return null;
    }

    @Override
    public HttpResponse httpGet(URI uri) throws OAuthException, IOException {
        HttpGet request = new HttpGet(uri);
        createConsumerWithSecret().sign(request);
        return new DefaultHttpClient().execute(request);
    }

    private OAuthConsumer createConsumerWithSecret() {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
        consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET);
        return consumer;
    }

}
