package Connection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by anthonyloroscio on 07/04/15.
 */
public class Extraction_règle_assoc {
    final static String FILE_NAME = "/Users/anthonyloroscio/FED_TEST2/apriori.trans";
    final static Charset ENCODING = StandardCharsets.UTF_8;
    String MotifTMP;
    int cpttest;
    HashMap FreqFinder = new HashMap();
    int MIN_CONF;

    public int getFreq(String line){
        return Integer.parseInt(line.substring(line.indexOf("(") + 1, line.indexOf(")")));
    }

    public String getContent(String line){
        return line.substring(0, line.indexOf("(") - 1);
    }

    private static boolean isContain(String source, String subItem){
        String pattern = "\\b"+subItem+"\\b";
        Pattern p= Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        return m.find();
    }

    public boolean isSubset(String A, String B) {
        return B.contains(A);
    }

    String One = "2 4";
    String Two = "2";



    public Extraction_règle_assoc(int MIN_CONF) throws IOException {

        /*System.out.println("Is two a subset of one? ");
        System.out.println(isSubset(Two, One));*/

        this.MIN_CONF = MIN_CONF;

        ArrayList<String> aTraiter = new ArrayList<String>();
        ArrayList<String> traite = new ArrayList<String>();

        File file = new File("apriori.trans");
        Scanner scan = new Scanner(file);

        File assoc = new File("assoc.txt");
        FileWriter associer = new FileWriter(assoc);
        scan.nextLine();
        while(scan.hasNextLine()){
            //aTraiter.add(scan.nextLine());
            MotifTMP = scan.nextLine();
            //génère le Hashmap avec le motif en clé (String) et la fréq en value
            FreqFinder.put(getContent(MotifTMP), getFreq(MotifTMP));
            //si la ligne en cours de lecture contient UNIQUEMENT 4 alors ++cpttest
            //if(isContain(MotifTMP, "42")) ++cpttest; //a enlever check que la ligne courante

            //prend chaque clé dans le hashmap et prend le surensemble correspondant, puis calcule la fréquence
        }
        Set<String> set = FreqFinder.keySet();
        String KeySetString = set.toString();
        Object[] SetArray = set.toArray();

        for(Object object : set) {

            String element = (String) object;
            for(int i = 0; i < set.size(); ++i){
                if(isSubset((String) SetArray[i], element)){
                    //System.out.println(i);
                    /*
                    SetArray[i] est trouvé, calcul la freq
                     */

                    String freqObjFound = FreqFinder.get(SetArray[i]).toString();
                    String freqObjTested = FreqFinder.get(element).toString();

                    String tmp = (String) SetArray[i];


                    int freqFound = Integer.parseInt(freqObjFound);
                    int freqTested = Integer.parseInt(freqObjTested);

                    int result = freqFound/freqTested;

                    //System.out.println(result);

                    if(result > MIN_CONF){
                        associer.append(element + " -> " + tmp.replace(element, "")); //Marche pas -> faut enlever element à tmp
                        associer.append(System.getProperty("line.separator"));
                    }
                }
            }
            //System.out.println(element);
            //if(isContain(KeySetString, element)) //System.out.println("trouvé");
        }

        //if(isContain(KeySetString, "4")) ++cpttest;
        //FreqFinder.keySet();

        /*System.out.println("set");
        System.out.println(set);
        System.out.println(FreqFinder);
        System.out.println("cpttest");
        System.out.println(cpttest);*/

        //ArrayList<Integer> CurrentValue = new ArrayList<Integer>();
        /*int[] results = new int[aTraiter.size];


        for(int i=1; i<aTraiter.size(); ++i) {
            CurrentValue.add(aTraiter.get(i));
        }*/

        //TO DO : prendre un elem chercher tous les sur élements dans lequel il est inclus, calculer la confiance si sup à celle fixée par l'user alors ok
        //Tricky point : parser chaque chiifre individuellement sinon fait une recherche groupée : trouve 1 dans 122 alors que pas censé.
        /*String content ="";
        String parcours;
        ArrayList<String> find = new ArrayList<String>();
        for(int i=1; i<aTraiter.size();++i) {

            content = aTraiter.get(i);
        }

        System.out.println(isContain(content, "2"));*/
            /*for(int a=1; a<aTraiter.size();++a){

                parcours = aTraiter.get(a);

                if(!getContent(content).equals(getContent(parcours))){

                    String[] contain = getContent(content).split(" ");



                    for(String str : getContent(content).split(" ")){
                        find.add(str);
                    }

                    boolean good = true;

                    for (int u = 0; u<contain.length;++u){
                        //check if it's not part of a number
                        if(!find.contains(" " + contain[u]) || !find.contains(contain[u]+ " ")){
                            good=false;
                        }
                    }

                   /* if(good && getFreq(parcours)/getFreq(content) >= MIN_CONF){

                        String ret = getContent(content);
                        for (int u = 0; u<contain.length;++u){
                            ret = ret.replace(contain[u],"");
                        }

                        associer.append(getContent(content) + " -> " + getContent(parcours).replace(getContent(content), ""));
                        associer.append(System.getProperty("line.separator"));
                    }
                }

            }*/


        associer.flush();
        associer.close();
        //System.out.println(aTraiter);
        //System.out.println(find);

    }

    public static void main(String[] args) throws IOException {
        Extraction_règle_assoc ext = new Extraction_règle_assoc(3500);

    }
}
