package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.service.TwitterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwitterCLIRunner {

    private static final String COLON = ":";
    private static final String COMMA = ",";
    private static TwitterService service;

    @Autowired
    public TwitterCLIRunner(TwitterService service) {
        this.service = service;
    }

    private static void postTweet(String[] args) {
        if (args.length != 3) {
            throw new RuntimeException("USAGE: TwitterCLI post \"tweet_text\" \"latitude:longitude\"");
        }
        String text = args[1];
        String[] coordinates = args[2].split(COLON);
        if (coordinates.length != 2 || StringUtils.isEmpty(text)) {
            throw new RuntimeException("Invalid format\nUSAGE: TwitterCLI post \"tweet_text\" \"latitude:longitude\"");
        }
        Double latitude = null;
        Double longitude = null;
        try {
            latitude = Double.parseDouble(coordinates[0]);
            longitude = Double.parseDouble(coordinates[1]);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid format\nUSAGE: TwitterCLI post \"tweet_text\" \"latitude:longitude\"");
        }
        service.postTweet(text, latitude, longitude);
    }

    private static void showTweet(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("USAGE: TwitterCLI show tweet_id [fields]");
        }
        String tweetId = null;
        String[] fieldsArr = null;

        switch (args.length) {
            case 2:
                tweetId = args[1];
                if (StringUtils.isEmpty(tweetId)) {
                    throw new RuntimeException("Empty Id\nUSAGE: TwitterCLI show tweet_id");
                }
                break;
            case 3:
                String fields = args[2];
                if (StringUtils.isEmpty(fields)) {
                    throw new RuntimeException();
                }
                fieldsArr = fields.split(COMMA);
        }
        service.showTweet(tweetId, fieldsArr);
    }

    private static void deleteTweet(String[] args) {
        if (args.length != 2 || StringUtils.isEmpty(args[1])) {
            throw new RuntimeException("USAGE: TwitterCLI deleteTweets tweet_ids");
        }
        String tweetIds = args[1];
        String[] idArr = tweetIds.split(COMMA);
        service.deleteTweets(idArr);
    }

    public static void run(String[] args) {
        if (args.length < 1) {
            throw new RuntimeException("USAGE: TwitterCLI post|show|delete [tweet_params]");
        }
        switch (args[0].toLowerCase()) {
            case "post":
                postTweet(args);
                break;
            case "show":
                showTweet(args);
                break;
            case "delete":
                deleteTweet(args);
                break;
            default:
                System.out.println("USAGE: TwitterCLI post|show|delete [tweet_params]");
                break;
        }
    }
}
