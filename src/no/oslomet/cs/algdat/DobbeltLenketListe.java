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
     *
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

        if (a == null) {
            throw new NullPointerException("Tabellen a er null");
        }

        Node<T> forrige = null;
        Node<T> nyNode = null;

        for (T verdi : a) {
            if (verdi == null)
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

    public static void fratilKontroll(int antall, int fra, int til) {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }


    public Liste<T> subliste(int fra, int til) {

        fratilKontroll(antall, fra, til);

        DobbeltLenketListe<T> sListe = new DobbeltLenketListe<>();


        for (int i = fra; i < til; i++) {
            sListe.leggInn( finnNode(i).verdi );
        }

        return sListe;
    }


    @Override
    public int antall() {
        return antall;

    }

    @Override
    public boolean tom() {
        if (antall() == 0) {
            return true;
        } else
            return false;
    }

    @Override // Opg 2, b.
    public boolean leggInn(T verdi) {

        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        if (antall == 0) {
            hode = new Node<>(verdi, null, null);
            hale = hode;
        } else {
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
            hode = new Node<>(verdi, null, hode);// legges først
        }

        if (antall == 0) {
            hode = new Node<>(verdi, null, null);      // hode og hale går til samme node
            hale = hode;
        } else if (indeks == antall) {          // ny verdi skal ligge bakerst
            hale.neste = new Node<>(verdi, hale, null);// legges bakerst
            hale = hale.neste;
        } else {
            Node<T> p = hode;                  // p flyttes indeks - 1 ganger
            for (int i = 1; i < indeks; i++)
                p = p.neste;

            p.neste = new Node<>(verdi, p.forrige, p.neste);  // verdi settes inn i listen
        }

        antall++;// listen har fått en ny verdi


        //throw new UnsupportedOperationException();

    }

    @Override // Opg. 3 b.
    public boolean inneholder(T verdi) {

        return indeksTil(verdi) != -1;
    }

    //3a
    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    //3a
    private Node<T> finnNode(int indeks) {

        Node<T> current = null;
        
        if (indeks == 0 && antall == 1) {
            current = hode;
            return current;
        }

        if (indeks == antall - 1)
            return hale;

        if (indeks <= (int) antall / 2) {
            current = hode;
            for (int i = 0; i < indeks; i++)
                current = current.neste;

        } else {
            current = hale;
            for (int j = antall - 1; j > indeks; j--)
                current = current.forrige;
        }
        return current;
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) return -1;

        Node<T> p = hode;

        for (int indeks = 0; indeks < antall; indeks++) {
            if (p.verdi.equals(verdi)) return indeks;
            p = p.neste;
        }
        return -1;
    }

    //3a
    @Override
    public T oppdater(int indeks, T nyverdi) {

        Objects.requireNonNull(nyverdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, false);

        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;

        p.verdi = nyverdi;

        endringer++;

        return gammelVerdi;

    }

    @Override
    public boolean fjern(T verdi) {

        if (verdi == null)
            return false;          // ingen nullverdier i listen

        Node<T> q = hode, p = null;               // hjelpepekere

        while (q != null)                         // q skal finne verdien t
        {
            if (q.verdi.equals(verdi)) break;       // verdien funnet
            p = q;
            q = q.neste;                     // p er forgjengeren til q
        }

        if (q == null) return false;// fant ikke verdi

        else if (q == hode)
            hode = hode.neste;    // går forbi q

        else
            p.neste = q.neste;                   // går forbi q

        if (q == hale)
            hale = p;                  // oppdaterer hale

        q.verdi = null;                           // nuller verdien til q
        q.neste = null;                           // nuller nestepeker

        antall--;                                 // en node mindre i listen

        return true;                              // vellykket fjerning
    }

    @Override
    public T fjern(int indeks) {

        indeksKontroll(indeks, false);  // Se Liste, false: indeks = antall er ulovlig

        T temp;                              // hjelpevariabel

        if (indeks == 0)                     // skal første verdi fjernes?
        {
            temp = hode.verdi;                 // tar vare på verdien som skal fjernes
            hode = hode.neste;                 // hode flyttes til neste node
            if (antall == 1) hale = null;      // det var kun en verdi i listen
        } else {
            Node<T> p = finnNode(indeks - 1);  // p er noden foran den som skal fjernes
            Node<T> q = p.neste;                       // q skal fjernes
            Node<T> r = q.neste;
            temp = q.verdi;                    // tar vare på verdien som skal fjernes

            if (q == hale) hale = p;           // q er siste node
            p.neste = r;                        // "hopper over" q
            r.forrige = p;
        }

        antall--;                            // reduserer antallet
        return temp;
    }

    @Override
    public void nullstill() {
        //throw new UnsupportedOperationException();
        Node<T> p = hode, q = hale;

        while (p != null) {
            p = q.neste;
            q.neste = null;
            q.verdi = null;
            q = p;
        }
        hode = hale = null;
        antall = 0;
        // Alternative 1
        // This elternative is the best in time condtions, 15ms,
        // because it dosen't run throw the array evey single time.
    }

    @Override //Opgav 2.
    public String toString() {

        StringBuilder sForward = new StringBuilder();

        if (tom())
            return "[]";

        sForward.append('[');


        Node<T> p = hode;
        sForward.append(p.verdi);

        p = p.neste;

        while (p != null)  // tar med resten hvis det er noe mer
        {
            sForward.append(',').append(' ').append(p.verdi);
            p = p.neste;
        }


        sForward.append(']');

        return sForward.toString();
    }

    public String omvendtString() {

        StringBuilder sBackwards = new StringBuilder();

        if (tom())
            return "[]";

        sBackwards.append('[');

        if (!tom()) {
            Node<T> q = hale;
            sBackwards.append(q.verdi);

            q = q.forrige;

            while (q != null)  // tar med resten hvis det er noe mer
            {
                sBackwards.append(',').append(' ').append(q.verdi);
                q = q.forrige;

            }
        }

        sBackwards.append(']');

        return sBackwards.toString();

    }

    @Override
    public Iterator<T> iterator() {
        //throw new UnsupportedOperationException();
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        //throw new UnsupportedOperationException();
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            //throw new UnsupportedOperationException();
            denne = (Node<T>) hent(indeks);     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            //throw new UnsupportedOperationException();
            Node<T> p = denne;
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");

            fjernOK = true;            // nå kan remove() kalles
            T denneVerdi = p.verdi;    // tar vare på verdien i p
            p = p.neste;               // flytter p til den neste noden

            return denneVerdi;
        }

        @Override
        public void remove() {
            //throw new UnsupportedOperationException();
            Node<T> p = denne;
            if (!fjernOK) throw new IllegalStateException("Ulovlig tilstand!");

            fjernOK = false;               // remove() kan ikke kalles på nytt
            Node<T> q = hode;              // hjelpevariabel

            if (hode.neste == p)           // skal den første fjernes?
            {
                hode = hode.neste;           // den første fjernes
                if (p == null) hale = null;  // dette var den eneste noden
            } else {
                Node<T> r = hode;            // må finne forgjengeren
                // til forgjengeren til p
                while (r.neste.neste != p) {
                    r = r.neste;// flytter r
                }

                q = r.neste;                 // det er q som skal fjernes
                r.neste = p;                 // "hopper" over q
                if (p == null) hale = r;     // q var den siste
            }

            q.verdi = null;                // nuller verdien i noden
            q.neste = null;                // nuller nestereferansen

            antall--;

        }

    } // class DobbeltLenketListeIterator


    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        //throw new UnsupportedOperationException();
    }


} // class DobbeltLenketListe


