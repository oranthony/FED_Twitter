package Connection;
import sun.rmi.rmic.Main;
import sun.tools.jar.resources.jar;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by aelar on 01/04/15.
 */
public class Connection {







    public static void CSVMaker(FileWriter writer,String date,String user, String msg) throws IOException {


        writer.append(date);
        writer.append(';');
        writer.append(user);


        for(String i : msg.split(" ")){
            writer.append(";");
            writer.append(i);
        }

        writer.append("\n");
        writer.flush();


    }

    public static void main(String[] args) throws TwitterException, IOException {



        /*ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("5M5te2LlQRfMGhQjWf5x4UTIG")
                .setOAuthConsumerSecret("ZKtfq8V1xUHqGye3B5cJmHrvd1bLy7s6m0DNTdQULiLds1jMaF")
                .setOAuthAccessToken("172911780-8qXDqBlhSCYwTq8MALxKZqY2v92EmAgexkKHGEN6")
                .setOAuthAccessTokenSecret("MyhwF5yty0EjkSYhBtMcMB4SM4lpyZtGs2gA9i6MajS0W");*/



        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query("#PSG");

        int numberOfTweets = 5000;
        long lastID = Long.MAX_VALUE;

        ArrayList<Status> tweets = new ArrayList<Status>();
        QueryResult result = null;
        FileWriter writer = new FileWriter("Test4.csv");
        while (tweets.size () < numberOfTweets) {
            if (numberOfTweets - tweets.size() > 100)
                query.setCount(100);
            else
                query.setCount(numberOfTweets - tweets.size());

            try {
                Thread.sleep(1500);
                result = twitter.search(query);
                tweets.addAll(result.getTweets());
                System.out.println("Gathered " + tweets.size() + " tweets");
                for (Status t: tweets)
                    if(t.getId() < lastID) lastID = t.getId();

            }

            catch (TwitterException te) {
                System.out.println("Couldn't connect: " + te);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            query.setMaxId(lastID-1);
        }
        for (int i = 0; i < tweets.size(); i++) {
            Status t = tweets.get(i);

            GeoLocation loc = t.getGeoLocation();

            String user = t.getUser().getScreenName();
            String msg = t.getText();
            Date time = t.getCreatedAt();
            CSVMaker(writer,time.toString(), user, msg);
        }
    }

}
