# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 

# Krav til innlevering

Se oblig-tekst for alle krav. Oppgaver som ikke oppfyller følgende vil ikke få godkjent:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* Ingen debug-utskrifter
* Alle testene som kreves fungerer (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet

# Arbeidsfordeling

Oppgaven er levert av følgende studenter:
* Navn Navnesen, S981737, s981737@oslomet.no
* Maja Åskov Tengstedt, s196074,  s196074@oslomet.no
* Hitomi Schiefloe, s344214, s344214@oslomet.no

Vi har brukt git til å dokumentere arbeidet vårt. Vi har 16 commits totalt, og hver logg-melding beskriver det vi har gjort av endringer.

I oppgaven har vi hatt følgende arbeidsfordeling:
* Maja har hatt hovedansvar for oppgave 1, 2, og 6. 
* Hitomi har hatt hovedansvar for oppgave 3, 4, og 5. 
* Hamzeh har hatt hovedansvar for oppgave 7, 8, 9 og 10. 
 
# Beskrivelse av oppgaveløsning (maks 5 linjer per oppgave)

* Oppgave 1: The biggest challenges in task 1 were to get value with zero not to be counted in a. 
After much reseach it was solved by a single code; 
if (value == null)
continue;
It was also a challenge to find out if NullPointerException could be performed with a common throw exception, or if more code was needed. As an example 3.3.2. in the compendium. 
This was solved by using:
if (a == null) {
throw new NullPointerException ("The table is null");


* Oppgave 2:  The code for String toString() was found from Oppgaver to Avsnitt 3.3.2.2. It is for EnkeltLenketListe but
the logic for Doubbeltlenketliste in ascending order is the same. So we used it for String toString().
About String omvendtString() this is print out list from back to front. EnkeltLenketListe has a link from head to tale and String toString() had Node<T> p = hode; p = p.neste;.
So we thought if we changed Node<T> p = hale; p = p.forrige; in omvendtString, it should work. Then we tried and it worked.
Both method should return "[]" when list has no value. So we put if statement on the top of the code.


* Oppgave 3: Firstly, we need to have some help variable for Node what we wanted to find. Then we had to think several patterns.
1). When Node is the head.
2). When Node is the tail.
3). When Node is smaller than the half of number ( antall / 2 ) in the list, then search from head to the the half of number(antall / 2). 
If antall /2 became decimal, it made a problem later.
So we put (int) antall/2 for judgement number.
4). When Node is bigger than the half of number ( antall / 2 ), the Node would be searched from tail to the half of number.
hent(int indeks) was easy to code, we had already have finnNode() method, so find the node for int index then return value of that index.


* Oppgave 4: 
A goal for public int indeksTil(T verdi) is to find index for value which in the list or not. If value is null, return -1. If  value is not null,
search the value in the list with forløkka. If the value is existing in the list, return index, if not exist in the list return -1.
For public boolean inneholder(T verdi), it has to return true or false if the value in the list or not.
In indeksTil method, if the value is not in the list return -1, this means if indeksTil(verdi) does not return -1, the value exist in the list.
Then boolean inneholder should return true, follows description.


* Oppgave 5: For this oppgave, idea came from Kompedium Programkode 3.3.2 f), but this code was for EnkeltLenketListe, 
so we had to connect variable from tail to head. There were several patterns when a Node added.
1). When the list was blank from before adding
2). When a Node added to head of the list.
3). When a node added to tail of the list.
4). When a node added in the middle of the list.
After adding a Node, numbers of the list should add 1 and changing also increased.
 

* Oppgave 6:
public boolean fjern(T verdi). 
First code was full of mistakes, then we tried to imporove the code. 
But it failed test 6n( removing a node from the middle of the list.) 
We used debug and found the a few problems. 
When recoding node.forrige.nexte, got Nullpointexception. 
The code was made clean and visible. 
The code had to consider for 4 cases. 
1). When a node is remove value and numbers in the list was none. 
2). When a node remove from head in the list. 
3). When a node remove from tail in the list. 
4). When a node remove from the middle of the list. 
For boolean methods return false and true. 
For public T fjern, return value that would be removed.


* Oppgave 7:
Vi defener hode og hale og så løber vi gjennom hele arrayet med while lokke 
og nulstillet nodene, til slutt hode er null
og hale er null og de er like null. Vi opdetere anttal og vi øke endringerene.
 1). p = q.neste;
 2). q.neste = null;
     q.verdi = null;
     q = p;
        }
 3). hode = hale = null;
 4). antall = 0;
     endringer++;


* Oppgave 8:
Denne oppgave er delt i mange deler 
A) koder vi T next methode 
sjekker iteratorendringer
Denne motoden kaster to untakk, ConcurrentModificationException og NoSuchElementException
plus fjenverdien, så har vi tull slutt den nye verdien.
B) koder vi iteratoren
Vi reurener DobbeltLenketListeIterator.
C) lager vi konstruktoren.
Konstrakuten er akkurate det samme som tideglere konstraktur men denne node skal peke på
finnnode -index.
D) koder vi iteratoren for indeks
Vi sjekke om indeksKontroll er fels og vi retunerer indeksen til DobbeltLenketListeIterator
Mest av koden var fra kompendumen.

* Oppgave 9:


* Oppgave 10:





 