package Connection;

import java.io.*;
import java.util.*;


/**
 * Created by anthonyloroscio on 02/04/15.
 */
public class TranstoCSV {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saveHash.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap hash =(HashMap) ois.readObject();

        Set keys = hash.keySet();
        Collection val = hash.values();

        HashMap Rhash = new HashMap();

        for(int i = 0;i<keys.size();++i){
            Rhash.put(val.toArray()[i], keys.toArray()[i]);

        }

        String Current;
        String[] tab;
        File file = new File("apriori.trans");
        Scanner scan = new Scanner(file);

        FileWriter fw = new FileWriter("apriori.csv");

        while (scan.hasNextLine()){
            Current = scan.nextLine();

            tab = Current.split(" ");

            for(String i : tab){
                if(!i.startsWith("(")){
                    //System.out.println(i);
                    System.out.println(Rhash.get(Integer.parseInt(i)));
                    fw.append((String) Rhash.get(Integer.parseInt(i)));
                    fw.append(';');

                }

            }
            fw.append(System.getProperty("line.separator"));
        }
        fw.flush();
        fw.close();

    }
}
