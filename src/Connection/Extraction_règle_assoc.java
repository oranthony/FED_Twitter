package Connection;

import java.io.File;
import java.io.FileNotFoundException;
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
    int MIN_FREQ;
    int MIN_CONF;

    public int getFreq(String line){
        return Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(")")));
    }

    public String getContent(String line){

    }

    public Extraction_règle_assoc(int MIN_FREQ, int MIN_CONF) throws FileNotFoundException {
        this.MIN_FREQ = MIN_FREQ;
        this.MIN_CONF = MIN_CONF;

        ArrayList<String> aTraiter = new ArrayList<String>();
        ArrayList<String> traite = new ArrayList<String>();

        File file = new File("apriori.trans");
        Scanner scan = new Scanner(file);
        String line;
        while(scan.hasNextLine()){
            line = scan.nextLine();
            int freq = getFreq(line);

            if(freq >= MIN_FREQ){
                aTraiter.add(line);
            }
        }

        String split;

        for(int i=0; i<aTraiter.size();++i){



            for(int a=0; a<aTraiter.size();++a){



            }

        }

    }
}
