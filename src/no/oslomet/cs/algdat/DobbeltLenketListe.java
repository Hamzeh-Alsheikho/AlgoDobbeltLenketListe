package no.oslomet.cs.algdat;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;



public class DobbeltLenketListe<T> implements Liste<T> {


    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        //throw new UnsupportedOperationException();
    }
    public DobbeltLenketListe(T[] verdiArray) {
        Node<T> forrige = null;
        Node<T> nyNode = null;

        for(T verdi: verdiArray) {
            antall++;
            nyNode = new Node<>(verdi, forrige, null);

            if (forrige != null) {
                forrige.neste = nyNode;
            } else {
                hode = nyNode;
            }

            forrige = nyNode;
        }
        hale = nyNode;
    }

    public Liste<T> subliste(int fra, int til){

        //throw new UnsupportedOperationException();
        return subliste(0,10);
    }

    @Override
    public int antall() {
     //Den første skal returnere antallet verdier i listen

        /*

        I think this is the code we need to use in antall. Kompendie programkode 3.1.2 b;

        int antall = 0;
         for (T t : this) antall++;
         return antall;

         */
        return 0;

    }

    @Override
    public boolean tom() {
        //  Den andre skal returnere true/false avhengig av om listen er tom eller ikke
        /*

        if (count == 0)
            return true;

        if (count != 0)
            return false;
         */

        return true;

    }

    @Override
    public boolean leggInn(T verdi) {
        //throw new UnsupportedOperationException();
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        //throw new UnsupportedOperationException();

    }

    @Override
    public boolean inneholder(T verdi) {
        //throw new UnsupportedOperationException();
        return inneholder(verdi);
    }

    @Override
    public T hent(int indeks) {
        //throw new UnsupportedOperationException();
        return hent(indeks);
    }

    @Override
    public int indeksTil(T verdi) {
        //throw new UnsupportedOperationException();
        return indeksTil(verdi);
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        //throw new UnsupportedOperationException();
        return oppdater(indeks,nyverdi);
    }

    @Override
    public boolean fjern(T verdi) {
        //throw new UnsupportedOperationException();
        return fjern(verdi);
    }

    @Override
    public T fjern(int indeks) {
        //throw new UnsupportedOperationException();
        return fjern(indeks);
    }

    @Override
    public void nullstill() {
        //throw new UnsupportedOperationException();
        // Alternative 2
       // Vi kalle klassen her
        Node node = new Node(hode, hale,null);
        // Vi kjekke om noden er tom
        if (node.verdi==null){
            return;
        }else {
            //Vi rome listen ved å flytte pekeren videre
            Node<T> p = hode, q = hale;

            while (p != null)
            {
                p = q.neste;
                q.neste = null;
                q.verdi = null;
                q = p;
            }
            hode = hale = null;
            antall = 0;
        }
        System.out.println(node);
        // Alternative 2
        // Vi loper gjennom hele arrya og slutte elementer en og en
        for (Iterator<T> i = iterator(); i.hasNext(); )
        {
            i.next();
            i.remove();
        }
    }

    @Override
    public String toString() {
        //throw new UnsupportedOperationException();
        return toString();
    }

    public String omvendtString() {
        //throw new UnsupportedOperationException();
        return omvendtString();
    }

    @Override
    public Iterator<T> iterator() {
        //throw new UnsupportedOperationException();
        return iterator();
    }

    public Iterator<T> iterator(int indeks) {
        //throw new UnsupportedOperationException();
        return iterator();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            //throw new UnsupportedOperationException();
            return next();
        }

        @Override
        public void remove(){
            //throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        //throw new UnsupportedOperationException();
    }
    
} // class DobbeltLenketListe


