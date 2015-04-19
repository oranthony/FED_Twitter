package Connection;

import java.io.*;
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

    String MotifTMP;
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


    public void AssociationsRulesToWords() throws IOException, ClassNotFoundException {

        //Adresse du fichier de serialisation de la hashmap généré lors de CSVtoTrans.java
        FileInputStream fis = new FileInputStream("saveHash1.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        HashMap hash =(HashMap) ois.readObject();

        Set keys = hash.keySet();
        Collection val = hash.values();

        HashMap Rhash = new HashMap<String, String>();

        for(int i = 0;i<keys.size();++i){
            Object tmp = val.toArray()[i];
            String tmp2 = (String) keys.toArray()[i];
            Rhash.put(tmp, tmp2);

        }

        //System.out.println(Rhash);

        String Current;
        String[] tab;

        //Nom du fichier qui contient les règles sous forme de chiffres
        File file = new File("assoc4sansLift.txt");
        Scanner scan = new Scanner(file);

        //Nom du fichier qui contiendra les règles sous forme de mots et non plus sous forme numérique
        FileWriter fw = new FileWriter("AssocAprèsExctractWithWords4.txt");

        while (scan.hasNextLine()){
            Current = scan.nextLine();

            tab = Current.split(" -> ");


            for(int i = 0; i < tab.length; i += i + 2){
                tab[i].toCharArray();
                if(i < tab.length-1) {

                    fw.append((String) Rhash.get(tab[i]));
                    fw.append(" -> ");
                    fw.append((String) Rhash.get(tab[i + 1]));
                }
                else {
                    fw.append((String) Rhash.get(tab[i]));
                    fw.append(" -> ");
                    fw.append("fin");
                }

            }
            fw.append(System.getProperty("line.separator"));
        }
        fw.flush();
        fw.close();



    }


    public Extraction_règle_assoc(double MIN_CONF) throws IOException {

        /*System.out.println("Is two a subset of one? ");
        System.out.println(isSubset(Two, One));*/

        this.MIN_CONF = MIN_CONF;

        ArrayList<String> MotsInutiles = new ArrayList<String>();

        //Nom du fichier d'origine, celui duquel on extrait les règles
        File file = new File("Test4.out");
        Scanner scan = new Scanner(file);

        //Fichier de sortit sans le Lift
        File assocSansLift = new File("assoc4sansLift.txt");
        FileWriter associerSansLift = new FileWriter(assocSansLift);
        scan.nextLine();

        //Fichier de sortit avec le Lift
        File assocAvecLift = new File("assoc4avecLift.txt");
        FileWriter associerAvecLift = new FileWriter(assocAvecLift);


        //Stock les itemSet et leur fréquence dans un hasmap
        while(scan.hasNextLine()){
            //aTraiter.add(scan.nextLine());
            MotifTMP = scan.nextLine();
            //génère le Hashmap avec le motif en clé (String) et la fréq en value
            FreqFinder.put(getContent(MotifTMP), getFreq(MotifTMP));

            //prend chaque clé dans le hashmap et prend le surensemble correspondant, puis calcule la fréquence
        }

        File fileCleaner = new File("motsinutiles.txt");
        Scanner scanInutile = new Scanner(file);
        String élementActuel;

        while(scanInutile.hasNextLine()){

            élementActuel = scanInutile.nextLine();
            MotsInutiles.add(élementActuel);

        }


        //Stock toutes les clés (les motifs) dans un set pour y acceder rapidement
        Set<String> set = FreqFinder.keySet();
        Object[] SetArray = set.toArray();
        boolean isUseless=false;

        //Parcour chaque motif
        for(Object object : set) {

            //Pour chaque motif cherche les sur-ensembles
            //element correspond à x
            String element = (String) object;
            for(int i = 0; i < set.size(); ++i){

                //CurrentLine correspond à Y
                String CurrentLine = (String) SetArray[i];
                if(MotsInutiles.contains(element)){
                    isUseless = true;
                }
                else if (CurrentLine.contains(element) && CurrentLine != element && isUseless == false){

                    /*System.out.println("element sous ensemble " + element);
                    System.out.println("currentline sur-ensemble " + CurrentLine);*/


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

                        associerSansLift.append(element + " -> " + CurrentLine.replace(element, ""));
                        associerSansLift.append(System.getProperty("line.separator"));

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
                            /*System.out.println("Y Sans X " + SansX + "     freq " + DoublefreqSansX);
                            System.out.println("Confiance " + Confiance);*/

                            // Calcul le Lift
                            double Lift = Confiance/DoublefreqSansX;
                            //System.out.println("result :" + Confiance);
                            System.out.println("Lift :" + Lift);


                            //Si le lift est supérieur à 1 alors génère les règles correspondantes
                            if (Lift > 1) {

                                associerAvecLift.append(element + " -> " + CurrentLine.replace(element, ""));
                                associerAvecLift.append(System.getProperty("line.separator"));

                            }
                        }
                    }
                }
            }

            //if(isContain(KeySetString, element)) //System.out.println("trouvé");
        }


        associerAvecLift.flush();
        associerAvecLift.close();
        associerSansLift.flush();
        associerSansLift.close();
        //System.out.println(aTraiter);
        //System.out.println(FreqFinder);
        //System.out.println(KeySetString);

    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {

        //j'ai mis 0.75 en confiance minimale car dans les tp de Lotfi on mettais toujours des valeurs de cet ordre là
        Extraction_règle_assoc ext = new Extraction_règle_assoc(0.75);
        ext.AssociationsRulesToWords(); //Transforme Les règles généres en mots grace à la sérialisation de la hashmap
    }




}
