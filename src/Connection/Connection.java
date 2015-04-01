package Connection;
import twitter4j.*;

import java.util.List;

/**
 * Created by aelar on 01/04/15.
 */
public class Connection {

    public static void main(String[] args) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query("#swag ");
        QueryResult result = twitter.search(query);
        for (Status status : result.getTweets()) {
            System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
        }
    }

}
