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
        private T verdi;
        private Node<T> forrige, neste;

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
    private Node<T> hode;
    private Node<T> hale;
    private int antall;
    private int endringer;

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
        if (fra < 0)
            throw new IndexOutOfBoundsException
                    ("fra(" + fra + ") er negativ!");

        if (til > antall)
            throw new IndexOutOfBoundsException
                    ("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)
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

        indeksKontroll(indeks, true);


        if (indeks == 0 && antall == 0){
            hode = new Node<>(verdi,null,null);
            hale = hode;
            hode.neste = null;
            hode.forrige = null;

        }

        else if(indeks == 0 && antall > 0){
            hode = finnNode(0);
            Node<T> current = hode;
            Node<T> nNode = new Node<>(verdi, null, current);
            nNode.neste = current;
            current.forrige = nNode;
            hode = nNode;
        }



        else if (indeks == antall)
        {
            Node<T> nNode = new Node<>(verdi, hale, null);
            if (hale != null) {
                nNode.forrige = hale;
                hale.neste = nNode;
                hale = nNode;
            }


        } else {

            Node<T> prev = null;
            Node<T> current = hode;
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

        if (verdi == null)
            return false;                               // ingen nullverdier i listen

        Node<T> current = hode, nCurrent = hode.neste, helpNum = null;        // hjelpepekere

        while (current != null)                         // q skal finne verdien t
        {
            if (current.verdi.equals(verdi))
                break;                                  // verdien funnet

            helpNum = current;
            current = current.neste;

        }

        if (current == null)
            return false;                               // fant ikke verdi

        else if (current == hode) {
            hode = hode.neste;
            //     hode.forrige = null;
        }

        else helpNum.neste = current.neste;

        if (current == hale) {
            hale = current.forrige;
            hale.neste = null;
        }

        current.verdi = null;                           // nuller verdien til q
        current.neste = null;                           // nuller nestepeker
        current.forrige = null;

        antall--;
        endringer++;// en node mindre i listen

        return true;
    }
    //Oppgave6
    @Override
    public T fjern(int indeks) {

        indeksKontroll(indeks, false);

        T deleteValue = null;
        Node<T> current = null;
        Node<T> prev = null;


        if (antall == 0)
            throw new NoSuchElementException("Liste er tom");


        if (indeks == 0 && antall == 1) {

            deleteValue = finnNode(0).verdi;
            hode = null;
            hale = null;

        } else if (indeks == 0 && antall == 2) {
            deleteValue = finnNode(0).verdi;
            hale = hode;
            hode.forrige = null;
            hale.neste = null;

        } else if (indeks == 0 && antall > 2) {
            deleteValue = finnNode(0).verdi;
            current = hode.neste;
            current.forrige = null;
            hode = current;

        } else if (indeks == antall - 1) {
            deleteValue = finnNode(indeks).verdi;
            prev = hale.forrige;
            hale.forrige = prev.forrige;
            prev.neste = null;
            hale = prev;


        } else {

            current = hode;
            for (int i = 1; i <= indeks; i++)
                current = current.neste;
            prev = current.forrige;

            deleteValue = finnNode(indeks).verdi;

            prev.neste = current.neste;
            current.neste.forrige = prev;
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
            denne = hode;
            fjernOK = false;
            iteratorendringer = endringer;
        }

        private DobbeltLenketListeIterator(int indeks) {
            //throw new UnsupportedOperationException();
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            //throw new UnsupportedOperationException();
            if (iteratorendringer!=endringer){
                throw new ConcurrentModificationException("");
            }
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");
            fjernOK = true;
            T denneVerdi = denne.verdi;
            denne = denne.neste;

            return denneVerdi;
        }

        @Override
        public void remove() {
            //throw new UnsupportedOperationException();
            Node<T> p, q, r;
            if (!fjernOK) throw new IllegalStateException("Ulovlig tilstand!");
            if (endringer!=iteratorendringer){
                throw new ConcurrentModificationException("De er like");
            }
            fjernOK= false;
            if (antall==1){
                hode=hale=null;
            }
            else if (denne==null){
                hale=hale.forrige;
                hale.neste=null;
            }
            else if (denne.forrige==hode){
                hode=denne;
                denne.forrige=null;
            }
            else {
                p= denne.forrige;
                q= p.neste;
                r= p.forrige;
                q.forrige=r;
                r.neste=q;
            }
            antall--;
            endringer++;
            iteratorendringer++;

        }

    } // class DobbeltLenketListeIterator


    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        //throw new UnsupportedOperationException();
    }


} // class DobbeltLenketListe


