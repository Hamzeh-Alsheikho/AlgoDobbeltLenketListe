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
        if (antall == 0)  hode = hale = new Node<>(verdi,null, null);
        else hale = hale.neste = new Node<>(verdi,hale, null);

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
        // Alternative 1
        // This elternative is the best in time condtions, 15ms,
        // because it dosen't run throw the array evey single time.
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
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        //throw new UnsupportedOperationException();
        return new DobbeltLenketListeIterator();
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
            //throw new UnsupportedOperationException();

        }

        @Override
        public boolean hasNext(){
            return denne != null;
        }

        @Override
        public T next(){
            //throw new UnsupportedOperationException();
            Node<T> p=null;
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");

            fjernOK = true;            // nå kan remove() kalles
            T denneVerdi = p.verdi;    // tar vare på verdien i p
            p = p.neste;               // flytter p til den neste noden

            return denneVerdi;
        }

        @Override
        public void remove(){
            //throw new UnsupportedOperationException();
            Node<T> p=hode;
            if (!fjernOK) throw new IllegalStateException("Ulovlig tilstand!");

            fjernOK = false;               // remove() kan ikke kalles på nytt
            Node<T> q = hode;              // hjelpevariabel

            if (hode.neste == p)           // skal den første fjernes?
            {
                hode = hode.neste;           // den første fjernes
                if (p == null) hale = null;  // dette var den eneste noden
            }
            else
            {
                Node<T> r = hode;            // må finne forgjengeren
                // til forgjengeren til p
                while (r.neste.neste != p)
                {
                    r = r.neste;               // flytter r
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

      // betingelsesfjerning
   /* public boolean fjernHvis(Predicate<? super T> p)  // betingelsesfjerning
    {
        Objects.requireNonNull(p);                       // kaster unntak

        boolean fjernet = false;
        for (Iterator<T> i = iterator(); i.hasNext(); )  // løkke
        {
            if (p.test(i.next()))                          // betingelsen
            {
                i.remove(); fjernet = true;                  // fjerner
            }
        }
        return fjernet;
} // grensesnitt Beholde

    */

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        //throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


