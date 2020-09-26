package no.oslomet.cs.algdat;

import java.util.Arrays;

public class Main {
    public static void main (String[] args) {
        DobbeltLenketListe<Integer> integerDobbeltLenketListe = new DobbeltLenketListe<>();
        integerDobbeltLenketListe.leggInn(10);
        integerDobbeltLenketListe.leggInn(20);
        integerDobbeltLenketListe.leggInn(30);
        integerDobbeltLenketListe.leggInn(40);
        integerDobbeltLenketListe.leggInn(50);
        Integer x = integerDobbeltLenketListe.hent(4);

        // DobbeltLenketListe<String> stringDobbeltLenketListe = new DobbeltLenketListe<>(new String[]{"a", "b", "c"});
        System.out.println("hi");
    }
}

