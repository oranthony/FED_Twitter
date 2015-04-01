package Connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by anthonyloroscio on 01/04/15.
 */
public class CSVtoTrans {

    final static String FILE_NAME = "/Users/anthonyloroscio/FED_TEST2/quenelle.csv";
    final static Charset ENCODING = StandardCharsets.UTF_8;

    void readLargerTextFile(String aFileName) throws IOException {

    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get(FILE_NAME);
        Scanner scanner = new Scanner(path, ENCODING.name());
        HashMap ConversionTable = new HashMap();
        String CreatedLine = "";
        //ConversionTable.put(0,"banane");
        try {
            while (scanner.hasNextLine()) {
                //process each line in some way
                String CurrentLine;
                CurrentLine = scanner.nextLine();
                //System.out.println(CurrentLine);
                String[] arr = CurrentLine.split(" ");
                String str;
                Object key;

                for ( String ss : arr) {
                //ss => mot actuel
                    if(ConversionTable.containsKey(ss)){
                        //System.out.println("ici");
                        key = ConversionTable.get(ss);
                        System.out.println(key);
                        CreatedLine += ConversionTable.get(ss);
                        CreatedLine += " ";
                    }
                    else {
                        //System.out.println("la");
                        ConversionTable.put(ss,ConversionTable.size());
                        CreatedLine += ConversionTable.get(ss);
                        CreatedLine += " ";
                    }
                }
            }
        } finally {
            scanner.close();
            /*Enumeration e = ConversionTable.elements();
            while(e.hasMoreElements())
                System.out.println(e.nextElement());*/
            System.out.println(ConversionTable);
            System.out.println(CreatedLine);

        }

    }
}
