package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dto.Tweet;
import oauth.signpost.exception.OAuthException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceImpTest {

    @InjectMocks
    private TwitterServiceImp service;
    @Mock
    private CrdDao mockDao;

    @Test
    public void postTweet() throws OAuthException, IOException {
        Tweet mockTweet = new Tweet();
        mockTweet.setText("This is a mock tweet");
        when(mockDao.create(any())).thenReturn(mockTweet);

        service.postTweet("some tweet", 0.0, 0.0);

        try {
            service.postTweet("", 0.0, 0.0);
            fail();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void showTweet() {
    }

    @Test
    public void deleteTweets() {
    }
}