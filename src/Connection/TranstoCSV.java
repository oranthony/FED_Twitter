package Connection;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Created by anthonyloroscio on 02/04/15.
 */
public class TranstoCSV {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saveHash.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap hash =(HashMap) ois.readObject();


        String Current;
        String[] tab;
        File file = new File("apriori.trans");
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()){
            Current = scan.nextLine();

            tab = Current.split(" ");

            for(String i : tab){
                if(!i.startsWith("(")){
                    //System.out.println(i);
                    System.out.println(hash.containsValue(i.));

                }


            }
        }

    }
}
