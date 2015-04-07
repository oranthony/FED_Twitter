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
        ArrayList<String> traite = new ArrayList<String>();

        File file = new File("apriori.trans");
        Scanner scan = new Scanner(file);

        File assoc = new File("assoc.txt");
        FileWriter associer = new FileWriter(assoc);
        scan.nextLine();
        while(scan.hasNextLine()){
            aTraiter.add(scan.nextLine());
        }

        String content;
        String parcours;
        for(int i=1; i<aTraiter.size();++i){

            content = aTraiter.get(i);

            for(int a=1; a<aTraiter.size();++a){

                parcours = aTraiter.get(a);

                if(!getContent(content).equals(getContent(parcours))){

                    String[] contain = getContent(content).split(" ");

                    ArrayList<String> find = new ArrayList<String>();

                    for(String str : getContent(content).split(" ")){
                        find.add(str);
                    }

                    boolean good = true;

                    for (int u = 0; u<contain.length;++u){
                        if(!find.contains(contain[u])){
                            good=false;
                        }
                    }

                    if(good && getFreq(parcours)/getFreq(content) >= MIN_CONF){

                        String ret = getContent(content);
                        for (int u = 0; u<contain.length;++u){
                            ret = ret.replace(contain[u],"");
                        }

                        associer.append(getContent(content) + " -> " + getContent(parcours).replace(getContent(content), ""));
                        associer.append(System.getProperty("line.separator"));
                    }
                }

            }

        }
        associer.flush();
        associer.close();

    }

    public static void main(String[] args) throws IOException {
        Extraction_règle_assoc ext = new Extraction_règle_assoc(200);

    }
}
