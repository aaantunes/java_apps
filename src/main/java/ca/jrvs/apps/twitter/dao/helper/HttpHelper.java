package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.net.URI;


public interface HttpHelper {
    HttpResponse httpPost(URI uri) throws OAuthException, IOException;

    HttpResponse httpPost(URI uri, StringEntity stringEntity);

    HttpResponse httpGet(URI uri) throws OAuthException, IOException;
}
