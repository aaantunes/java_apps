package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

@Component
public class ApacheHttpHelper implements HttpHelper {

    private static String CONSUMER_KEY = System.getenv("consumerKey");
    private static String CONSUMER_SECRET = System.getenv("consumerSecret");
    private static String ACCESS_TOKEN = System.getenv("accessToken");
    private static String TOKEN_SECRET = System.getenv("tokenSecret");

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
