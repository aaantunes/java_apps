package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.Util.JsonUtil;
import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dto.Coordinates;
import ca.jrvs.apps.twitter.dto.Tweet;
import com.fasterxml.jackson.core.JsonProcessingException;
import oauth.signpost.exception.OAuthException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class TwitterServiceImp implements TwitterService {

    private static final int MAX_TWEET_LENGTH = 280;
    private static final double MIN_LATITUDE = -90;
    private static final double MAX_LATITUDE = 90;
    private static final double MIN_LONGITUDE = -180;
    private static final double MAX_LONGITUDE = 180;
    private static final String COORDINATE_TYPE = "Point";

    private CrdDao dao;

    @Autowired
    public TwitterServiceImp(CrdDao dao) {
        this.dao = dao;
    }

    @Override
    public void postTweet(String text, Double latitude, Double longitude) {
        Tweet tweet = generateBody(text, latitude, longitude);
        try {
            Tweet response = (Tweet) dao.create(tweet);
            printTweet(tweet);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Failed to post tweet");
        }
    }

    @Override
    public void showTweet(String id, String[] fields) {
        if (!StringUtils.isNumeric(id)) {
            throw new RuntimeException("ID must be numeric");
        }
        try {
            Tweet tweet = (Tweet) dao.findById(id);
            printTweet(tweet);
        } catch (OAuthException | IOException e) {
            throw new RuntimeException("Failed to show tweet");
        }
    }

    @Override
    public void deleteTweets(String[] ids) {
        for (String s : ids) {
            if (!StringUtils.isNumeric(s)) {
                throw new RuntimeException("ID must be numeric");
            }
            try {
                Tweet tweet = (Tweet) dao.deleteById(s);
                printTweet(tweet);
            } catch (OAuthException | IOException e) {
                throw new RuntimeException("Failed to delete tweet");
            }
        }
    }

    private Tweet generateBody(String text, Double latitude, Double longitude) {
        Tweet tweet = new Tweet();
        Coordinates coordinates = new Coordinates();

        if (text.toCharArray().length > MAX_TWEET_LENGTH || text.isEmpty()) {
            throw new IllegalArgumentException("Not a valid tweet");
        }
        if (latitude < MIN_LATITUDE || latitude > MAX_LATITUDE || longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE) {
            throw new IllegalArgumentException("Not a valid coordinate");
        }

        coordinates.setCoordinates(Arrays.asList(latitude, longitude));
        coordinates.setType(COORDINATE_TYPE);
        tweet.setText(text);
        tweet.setCoordinates(coordinates);
        return tweet;
    }

    private void printTweet(Tweet tweet) throws JsonProcessingException {
        System.out.println(JsonUtil.toJson(tweet, true, false));
    }
}
