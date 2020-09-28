package no.oslomet.cs.algdat;

import java.util.Arrays;

public class Main {
    public static void main (String[] args) {

        String[] s1 = {}, s2 = { "A" }, s3 = {null, "A" ,null, "B" ,null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);
        System.out.println(l1.toString() + " " + l2.toString()
                + " " + l3.toString() + " " + l1.omvendtString() + " "
                + l2.omvendtString() + " " + l3.omvendtString());

       /* DobbeltLenketListe<Integer> integerDobbeltLenketListe = new DobbeltLenketListe<>();
        integerDobbeltLenketListe.leggInn(10);
        integerDobbeltLenketListe.leggInn(20);
        integerDobbeltLenketListe.leggInn(30);
        integerDobbeltLenketListe.leggInn(40);
        integerDobbeltLenketListe.leggInn(50);
        Integer x = integerDobbeltLenketListe.hent(4);*/

       // DobbeltLenketListe<String> stringDobbeltLenketListe = new DobbeltLenketListe<>(new String[]{"a", "b", "c"});
       // System.out.println("hi");

        /*String[] s = { "Ole" , null , "Per" , "Kari ", null };
        Liste<String> liste = new DobbeltLenketListe<>(null);
        System. out .println(liste. antall () + " " + liste. tom ());*/



    }
}

