package Connection;
import twitter4j.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aelar on 01/04/15.
 */
public class Connection {

    public static void CSVMaker(String date,String user, String msg) throws IOException {

        FileWriter writer = new FileWriter("./hashtag.csv");
        writer.append(date);
        writer.append(';');
        writer.append(user);


        for(String i : msg.split(" ")){
            writer.append(";");
            writer.append(i);
        }

        writer.append("\n");
        writer.flush();
        writer.close();

    }

    public static void main(String[] args) throws TwitterException, IOException {
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query("#swag");

        int numberOfTweets = 200;
        long lastID = Long.MAX_VALUE;

        ArrayList<Status> tweets = new ArrayList<Status>();
        QueryResult result = null;

        while (tweets.size () < numberOfTweets) {
            if (numberOfTweets - tweets.size() > 100)
                query.setCount(100);
            else
                query.setCount(numberOfTweets - tweets.size());
            try {
                result = twitter.search(query);
                tweets.addAll(result.getTweets());
                System.out.println("Gathered " + tweets.size() + " tweets");
                for (Status t: tweets)
                    if(t.getId() < lastID) lastID = t.getId();

            }

            catch (TwitterException te) {
                System.out.println("Couldn't connect: " + te);
            }
            query.setMaxId(lastID-1);
        }
        for (int i = 0; i < tweets.size(); i++) {
            Status t = tweets.get(i);

            GeoLocation loc = t.getGeoLocation();

            String user = t.getUser().getScreenName();
            String msg = t.getText();
            Date time = t.getCreatedAt();
            CSVMaker(time.toString(), user, msg);
        }
    }

}
