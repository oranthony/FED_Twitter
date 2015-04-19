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
    double MIN_CONF;

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



    public Extraction_règle_assoc(double MIN_CONF) throws IOException {

        /*System.out.println("Is two a subset of one? ");
        System.out.println(isSubset(Two, One));*/

        this.MIN_CONF = MIN_CONF;

        ArrayList<String> aTraiter = new ArrayList<String>();
        ArrayList<String> traite = new ArrayList<String>();

        File file = new File("Quenelle1aprèsApriori.out");
        Scanner scan = new Scanner(file);

        File assoc = new File("assoc.txt");
        FileWriter associer = new FileWriter(assoc);
        scan.nextLine();

        //Stock les itemSet et leur fréquence dans un hasmap
        while(scan.hasNextLine()){
            //aTraiter.add(scan.nextLine());
            MotifTMP = scan.nextLine();
            //génère le Hashmap avec le motif en clé (String) et la fréq en value
            FreqFinder.put(getContent(MotifTMP), getFreq(MotifTMP));

            //prend chaque clé dans le hashmap et prend le surensemble correspondant, puis calcule la fréquence
        }

        //Stock toutes les clés (les motifs) dans un set pour y acceder rapidement
        Set<String> set = FreqFinder.keySet();
        Object[] SetArray = set.toArray();

        //Parcour chaque motif
        for(Object object : set) {

            //Pour chaque motif cherche les sur-ensembles
            //element correspond à x
            String element = (String) object;
            for(int i = 0; i < set.size(); ++i){

                //CurrentLine correspond à Y
                String CurrentLine = (String) SetArray[i];
                if (CurrentLine.contains(element) && CurrentLine != element){

                    System.out.println("element sous ensemble " + element);
                    System.out.println("currentline sur-ensemble " + CurrentLine);


                    //récupère les fréquences dans la hashmap (mais en String)
                    String freqObjFound = FreqFinder.get(CurrentLine).toString();    //=Y, sur ensemble
                    String freqObjTested = FreqFinder.get(element).toString();       //=X,


                    /*System.out.println(CurrentLine + "freq : " + freqObjFound);
                    System.out.println("contient : " + element + "freq : " + freqObjTested);*/

                    //Cast les fréquences (String) en double
                    double freqFound = Integer.parseInt(freqObjFound);
                    double freqTested = Integer.parseInt(freqObjTested);

                    /*System.out.println(freqFound);
                    System.out.println(freqTested);
                    System.out.println("separator");*/

                    //Calcul de la confiance
                    double Confiance = freqFound/freqTested;


                    //Check Confiance
                    if(Confiance > MIN_CONF){

                        //Do The Lift



                        //Construction de la String Y privé de X
                        String SansX = CurrentLine.replace(" "+element, "");

                        //System.out.println("Current line" + CurrentLine);
                        //System.out.println("element" + element);
                        //System.out.println("sansx " + SansX);
                        //System.out.println(KeySetString);

                        //Find in hashmap the freq of F(X->Y/X) // la freq de y sans x
                        if (FreqFinder.containsKey(SansX)) {

                            //récupère la fréquence puis la cast en double
                            String freqSansX = FreqFinder.get(SansX).toString();
                            double DoublefreqSansX = Integer.parseInt(freqSansX);

                            //System.out.println("DoublefreqSansX " + DoublefreqSansX);
                            System.out.println("Y Sans X " + SansX + "     freq " + DoublefreqSansX);
                            System.out.println("Confiance " + Confiance);

                            // Calcul le Lift
                            double Lift = Confiance/DoublefreqSansX;
                            //System.out.println("result :" + Confiance);
                            //System.out.println("Lift :" + Lift);

                            //Si le lift est supérieur à 1 alors génère les règles correspondantes
                            if (Lift > 1) {
                                //System.out.println("cc");
                                associer.append(element + " -> " + CurrentLine.replace(element, "")); //Marche pas -> faut enlever element à tmp
                                associer.append(System.getProperty("line.separator"));
                            }
                        }
                    }
                }
            }

            //if(isContain(KeySetString, element)) //System.out.println("trouvé");
        }


        associer.flush();
        associer.close();
        //System.out.println(aTraiter);
        //System.out.println(FreqFinder);
        //System.out.println(KeySetString);

    }



    public static void main(String[] args) throws IOException {

        //j'ai mis 0.75 en confiance minimale car dans les tp de Lotfi on mettais toujours des valeurs de cet ordre là
        Extraction_règle_assoc ext = new Extraction_règle_assoc(0.75);

    }
}
