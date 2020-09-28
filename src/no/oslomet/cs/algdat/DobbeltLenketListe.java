package no.oslomet.cs.algdat;


////////////////// class DobbeltLenketListe //////////////////////////////


import javax.swing.*;
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
        hode = null;
        hale = null;
        antall = 0;
        endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {

        if ( a== null){
            throw new NullPointerException("Tabellen a er null");
        }

        Node<T> forrige = null;
        Node<T> nyNode = null;

        for(T verdi: a) {
            if (verdi ==null)
                continue;
            antall++;
            nyNode = new Node<>(verdi, forrige, null);

            if (forrige != null) {
                forrige.neste = nyNode;
            } else {
                hode = nyNode;
            }

            forrige = nyNode;
            //endringer
        }
        hale = nyNode;
    }


    public Liste<T> subliste(int fra, int til){

        //throw new UnsupportedOperationException();
        return subliste(0,10);
    }

    @Override
    public int antall() {
        return antall;

    }

    @Override
    public boolean tom() {
        if (antall() == 0){
            return true;}
        else
            return false;
    }

    @Override // Opg 2, b.
    public boolean leggInn(T verdi) {

        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        if (antall == 0){
            hode  = new Node<>(verdi, null,null);
            hode = hale;
        }


        else {
            hale.neste = new Node<>(verdi, hale, null);
            hale = hale.neste;
        }

        antall++;

        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {

        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, true);  // Se Liste, true: indeks = antall er lovlig
        if (indeks == 0)                     // ny verdi skal ligge først
        {
            hode = new Node<>(verdi,null, hode.neste);    // legges først
            if (antall == 0) hale = hode;      // hode og hale går til samme node
        }
        else if (indeks == antall)          // ny verdi skal ligge bakerst
        {
            hale = hale.neste = new Node<>(verdi,hale.forrige, null);// legges bakerst
        }
        else
        {
            Node<T> p = hode;                  // p flyttes indeks - 1 ganger
            for (int i = 1; i < indeks; i++) p = p.neste;

            p.neste = new Node<>(verdi,null, p.neste);  // verdi settes inn i listen
        }

        antall++;                            // listen har fått en ny verdi


        //throw new UnsupportedOperationException();

    }

    @Override // Opg. 3 b.
    public boolean inneholder(T verdi) {

        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks) {
        //throw new UnsupportedOperationException();
        return hent(indeks);
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) return -1;

        Node<T> p = hode;

        for (int indeks = 0; indeks < antall ; indeks++)
        {
            if (p.verdi.equals(verdi)) return indeks;
            p = p.neste;
        }
        return -1;
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

    @Override //Opgav 2.
    public String toString() {

        StringBuilder sForward = new StringBuilder();

        if(tom())
            return "[]";

        sForward.append('[');

        if (!tom())
        {
            Node<T> p = hode;
            sForward.append(p.verdi);

            p = p.neste;

            while (p != null)  // tar med resten hvis det er noe mer
            {
                sForward.append(',').append(' ').append(p.verdi);
                p = p.neste;
            }
        }

        sForward.append(']');

        return sForward.toString();
    }

    public String omvendtString() {

        StringBuilder sBackwards = new StringBuilder();

        if(tom())
            return "[]";

        sBackwards.append('[');

        if (!tom())
        {
            Node<T> q = hale;
            sBackwards.append(q.verdi);

            q = q.forrige;

            while (q != null)  // tar med resten hvis det er noe mer
            {
                sBackwards.append(',').append(' ').append(q.verdi);
                q = q.forrige;

               /* Node<T> temp = null;
                Node<T> current = q;
                while (current != null) {
                    temp = current.forrige;
                    current.forrige = current.neste;
                    current.neste = temp;
                    current = current.forrige;
                }*/


            }
        }

        sBackwards.append(']');

        return sBackwards.toString();

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


