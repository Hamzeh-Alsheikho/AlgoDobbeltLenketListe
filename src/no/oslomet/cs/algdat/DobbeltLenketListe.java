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

        //new
        private Node(){
            this(null,null,null);
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
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {

        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, true);  // Se Liste, true: indeks = antall er lovlig


        if (indeks == 0 && antall == 0){
            hode = new Node<>(verdi,null,null);
            hale = hode;
            hode.neste = null;
            hode.forrige = null;

        }

        else if(indeks == 0 && antall > 0){
            hode = finnNode(0);
            Node<T> current = hode;
            Node<T> nNode = new Node<>(verdi, null, current);// legges først
            nNode.neste = current;
            current.forrige = nNode;
            hode = nNode;
        }



        else if (indeks == antall)           // ny verdi skal ligge bakerst
        {
            Node<T> nNode = new Node<>(verdi, hale, null);
            if (hale != null) {
                nNode.forrige = hale;
                hale.neste = nNode;
                hale = nNode;
            }


        } else {

            Node<T> prev = null;
            Node<T> current = hode;                 // p flyttes indeks - 1 ganger
            for (int i = 1; i < indeks; i++)
                current = current.neste;
            prev = current;
            current = current.neste;

            Node<T> nNode = new Node<>(verdi, prev, current);
            prev.neste = nNode;
            nNode.neste = current;
            current.forrige = nNode;
            nNode.forrige = prev;


        }
        antall++;
        endringer++;
    }

    @Override // Oppgave4
    public boolean inneholder(T verdi) {

        if(indeksTil(verdi) != -1)
            return true;

        else return false;
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

    //oppgave4
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

    //Oppgave6
    @Override
    public boolean fjern(T verdi) {

        Node<T> q = null, p = null, r = null;

        if (verdi == null)
            return false;          // ingen nullverdier i listen

        if (q == null) return false;// fant ikke verdi

        //Node<T> q = hode, p, r;               // hjelpepekere

        while (q != null)                         // q skal finne verdien t
        {
            if (q.verdi.equals(verdi)) break;       // verdien funnet
            p =
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
        endringer ++;
        return true;                              // vellykket fjerning
    }

    //Oppgave6
    @Override
    public T fjern(int indeks) {

        indeksKontroll(indeks, false);

        T deleteValue = null;
        Node<T> current = null;


        if(antall == 0)
            throw new NoSuchElementException("Liste er tom");

        if (indeks == 0 && antall == 1) {

            deleteValue = finnNode(0).verdi;
            hode = null;
            hale = null;
            //return deleteValue;
        }

        else if (indeks == 0 && antall > 0)

        {
            deleteValue = finnNode(0).verdi;
            current = hode.neste;
            hode = current;
            current = current.neste;
            current.forrige = null;

            //return deleteValue;

        }
        else if (indeks == antall -1 )
        {
            deleteValue = finnNode(antall-1).verdi;
            current = hale.forrige;
            hale.forrige = current.forrige;
            hale.neste = null;
            hale = current;

            //return deleteValue;


        } else {

            Node<T> prev = null;
            current = hode;
            for (int i = 1; i < antall -1 ; i++)
                current = current.neste;
            prev = current.forrige;

            deleteValue = finnNode(indeks).verdi;
            prev.neste = current.neste;
            current.forrige = prev;
        }

        antall--;
        endringer++;
        return deleteValue;
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
        endringer++;
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


        Node<T> p = hale;
        sBackwards.append(p.verdi);

        p = p.forrige;

        while (p != null)  // tar med resten hvis det er noe mer
        {
            sBackwards.append(',').append(' ').append(p.verdi);
            p = p.forrige;
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
        indeksKontroll(indeks, false);
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
            denne = finnNode(indeks);     // p starter på den første i listen
            fjernOK = false;                   // blir sann når next() kalles
            iteratorendringer = endringer;    // teller endringer
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            //throw new UnsupportedOperationException();
            Node<T> p = hode;
            if (iteratorendringer!=endringer){
                throw new ConcurrentModificationException("");
            }
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");
            fjernOK = true;                     // nå kan remove() kalles
            T denneVerdi = denne.verdi;         // tar vare på verdien i p
            denne = denne.neste;    // flytter p til den neste noden

            return denneVerdi;
        }

        @Override
        public void remove() {
            //throw new UnsupportedOperationException();
           /* Node<T> p = denne;

            if (!fjernOK) throw new IllegalStateException("Ulovlig tilstand!");
            if (iteratorendringer!=endringer){
                throw new ConcurrentModificationException("");
            }
            fjernOK = false;                   // remove() kan ikke kalles på nytt
            Node<T> q = hode;                   // hjelpevariabel
            if (hode.neste == denne)           // skal den første fjernes?
            {
                hode = hode.neste;              // den første fjernes
               // Hode piker backover
                hode.forrige=null;
                if (denne == null) hale = null;  // dette var den eneste noden
            }
            else
            {
                Node<T> r = hode;                       // må finne forgjengeren
                                                        // til forgjengeren til p
                while (r.neste.neste != denne) {
                    r = r.neste;                        // flytter r
                }
                q = r.neste;                              // det er q som skal fjernes
                r.neste = denne;                            // "hopper" over q
                if (denne == null) hale = r;             // q var den siste
            }


            q.verdi = null;                // nuller verdien i noden
            q.neste = null;                // nuller nestereferansen

             */
            //Node<T> p = denne;
            Node<T> delete = new Node<T>();
            if (!fjernOK) throw new IllegalStateException("Ulovlig tilstand!");
            if (iteratorendringer!=endringer){
                throw new ConcurrentModificationException("");
            }
            fjernOK = false;
            if (antall==0){
            throw new IllegalArgumentException("Arrays is epmty");
            }
            if (antall==1){
                hode.neste=null;
                hale.forrige=null;

                antall--;
                endringer++;
                iteratorendringer++;
            }
            if (denne==null){
               delete=hale.forrige;
               hale.forrige=delete.forrige;
               delete.forrige.neste=null;

                antall--;
                endringer++;
                iteratorendringer++;
            }
            else if (hode.neste.neste.equals(denne)){
                hode.neste=denne;
                denne.forrige=null;

                antall--;
                endringer++;
                iteratorendringer++;

            } else if (hode.neste.equals(denne)) {
                //throw new IllegalArgumentException("");
            } else {
                delete = denne.forrige;
                delete.forrige.neste=denne;
                denne.forrige = delete.forrige;

                antall--;
                endringer++;
                iteratorendringer++;
            }
        }

    } // class DobbeltLenketListeIterator


    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        //throw new UnsupportedOperationException();
    }


} // class DobbeltLenketListe


