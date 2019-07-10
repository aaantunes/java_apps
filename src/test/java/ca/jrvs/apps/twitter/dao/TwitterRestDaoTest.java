package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.Util.JsonUtil;
import ca.jrvs.apps.twitter.dao.helper.ApacheHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dto.Coordinates;
import ca.jrvs.apps.twitter.dto.Tweet;
import oauth.signpost.exception.OAuthException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class TwitterRestDaoTest {

    private Tweet expectedTweet;
    private CrdDao dao;
    private String id;

    @Before
    public void setup() {
        String tweetText = "Hello Test World " + System.currentTimeMillis();
        this.expectedTweet = new Tweet();
        expectedTweet.setText(tweetText);

        HttpHelper httpHelper = new ApacheHttpHelper();
        this.dao = new TwitterRestDao(httpHelper);
    }

    @After
    public void cleanUp() throws OAuthException, IOException {
        System.out.println("Deleting " + this.id);
        dao.deleteById(this.id);
    }

    @Test
    public void create() throws OAuthException, IOException {
        //prepare tweet text
        Coordinates coordinates = new Coordinates();
        coordinates.setCoordinates(Arrays.asList(50.0, 50.0));
        coordinates.setType("Point");
        expectedTweet.setCoordinates(coordinates);
        System.out.println(JsonUtil.toJson(expectedTweet, true, false));

        //call create method
        Tweet createTweet = (Tweet) dao.create(expectedTweet);
        System.out.println(JsonUtil.toJson(createTweet, true, false));

        //validate tweet obj
        assertTweets(expectedTweet, createTweet);
        this.id = createTweet.getIdStr();

        Tweet showTweet = (Tweet) dao.findById(this.id);
        assertTweets(expectedTweet, showTweet);
    }

    public void assertTweets(Tweet expectedTweet, Tweet actualTweet) {
        assertNotNull(actualTweet);
        assertNotNull(actualTweet.getIdStr());
        assertEquals(expectedTweet.getText(), actualTweet.getText());
        assertEquals(expectedTweet.getCoordinates().getCoordinates(), actualTweet.getCoordinates().getCoordinates());
    }
}