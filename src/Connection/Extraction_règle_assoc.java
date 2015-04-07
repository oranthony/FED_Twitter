package Connection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by anthonyloroscio on 07/04/15.
 */
public class Extraction_règle_assoc {
    final static String FILE_NAME = "./apriori.trans";
    final static Charset ENCODING = StandardCharsets.UTF_8;
    int MIN_CONF;

    public int getFreq(String line){
        return Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(")")));
    }

    public String getContent(String line){
        return line.substring(0, line.indexOf("(") - 1);
    }

    public Extraction_règle_assoc(int MIN_CONF) throws IOException {

        this.MIN_CONF = MIN_CONF;

        ArrayList<String> aTraiter = new ArrayList<String>();


        File file = new File("apriori.trans");
        Scanner scan = new Scanner(file);

        File assoc = new File("assoc.txt");
        FileWriter associer = new FileWriter(assoc);
        scan.nextLine();
        while(scan.hasNextLine()){
            aTraiter.add(scan.nextLine());
        }

        ArrayList<String> enCours = new ArrayList<String>();
        ArrayList<String> check = new ArrayList<String>();

        for(String i : aTraiter){
            for (String x : getContent(i).split(" ")){
                enCours.add(x);
            }

            for(String a : aTraiter){

            }
        }

        associer.flush();
        associer.close();

    }

    public static void main(String[] args) throws IOException {
        Extraction_règle_assoc ext = new Extraction_règle_assoc(200);

    }
}
