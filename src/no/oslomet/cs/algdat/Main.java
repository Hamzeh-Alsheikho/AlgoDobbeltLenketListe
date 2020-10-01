package no.oslomet.cs.algdat;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
      /* String[] navn = { "Lars" , "Anders" , "Bodil" , "Kari" , "Per" , "Berit" };
        Liste<String> liste = new DobbeltLenketListe<>(navn);
        liste.forEach(s -> System. out .print(s + " " ));
        System. out .println();
        for (String s : liste) System. out .print(s + " " );
// Utskrift:
// Lars Anders Bodil Kari Per Berit
// Lars Anders Bodil Kari Per Berit


       */
        DobbeltLenketListe<String> liste =
                new DobbeltLenketListe<>( new String[]
                        { "Birger" , "Lars" , "Anders" , "Bodil" , "Kari" , "Per" , "Berit" });
        liste.fjernHvis(navn -> navn.charAt(0) == 'B' ); // fjerner navn som starter med B
        System. out .println(liste + " " + liste.omvendtString());
// Utskrift: [Lars, Anders, Kari, Per] [Per, Kari, Anders, Lars]


}}


