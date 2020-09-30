package no.oslomet.cs.algdat;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Character[] c = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
        Liste<Character> subliste = liste.subliste(3, 8);

        System.out.println(liste.subliste(0, 1));
        System.out.println(liste.subliste(3, 8)); // [D, E, F, G, H]
        System.out.println(liste.subliste(5, 5)); // []
        System.out.println(liste.subliste(8, liste.antall()));
    }

}


