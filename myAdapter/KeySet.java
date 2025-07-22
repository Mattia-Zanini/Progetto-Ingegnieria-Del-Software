package myAdapter;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

/**
 * Rappresenta una vista (view) delle chiavi contenute in una
 * {@code MapAdapter}.
 * Questa classe fornisce un'implementazione dell'interfaccia {@link HSet} per
 * l'insieme delle chiavi della mappa.
 * <p>
 * Essendo un "set", non può contenere elementi duplicati. Le modifiche
 * apportate a questo set si riflettono sulla mappa originale e viceversa.
 * Ad esempio, se una chiave viene rimossa da questo set, la corrispondente
 * entry (chiave-valore) viene rimossa dalla mappa di supporto (backing map).
 * <p>
 * Questa classe estende {@link ValueCollection} per ereditare le funzionalità
 * di base di una collezione, ma esegue l'override di alcuni metodi per
 * adattarli al comportamento specifico di un set di chiavi.
 *
 * @see HSet
 * @see MapAdapter
 * @see ValueCollection
 */
public class KeySet extends ValueCollection implements HSet {
    // I dati, come la mappa di riferimento (parentMap), sono ereditati da
    // ValueCollection.

    /**
     * Costruisce un nuovo set di chiavi basato sulla mappa fornita.
     * Il set creato è una vista "live" delle chiavi della mappa.
     *
     * @param p la {@code MapAdapter} di supporto (backing map) da cui creare il set
     *          di chiavi.
     */
    protected KeySet(MapAdapter p) {
        super(p);
    }

    // ---------------------- METODI PUBBLICI ----------------------

    // Il metodo size() è ereditato da ValueCollection e funziona correttamente
    // perché restituisce la dimensione della mappa di supporto.

    // Il metodo isEmpty() è ereditato da ValueCollection e funziona correttamente
    // perché controlla se la mappa di supporto è vuota.

    /**
     * Verifica se questo set contiene la chiave specificata.
     * <p>
     * La verifica viene delegata al metodo {@code containsKey} della mappa di
     * supporto.
     *
     * @param o la chiave la cui presenza in questo set deve essere verificata.
     * @return {@code true} se questo set contiene la chiave specificata.
     * @throws NullPointerException se l'oggetto specificato è {@code null},
     *                              poiché le chiavi non possono essere nulle in una
     *                              {@code Hashtable}.
     */
    @Override
    public boolean contains(Object o) {
        // La chiamata a parentMap.containsKey gestisce la logica, inclusa
        // la generazione di NullPointerException se 'o' è null.
        return parentMap.containsKey(o);
    }

    /**
     * Restituisce un iteratore sulle chiavi contenute in questo set.
     * L'iteratore restituito è di tipo {@link HIterator}.
     *
     * @return un iteratore sulle chiavi del set.
     */
    @Override
    public HIterator iterator() {
        // Crea una nuova istanza dell'iteratore specifico per le chiavi.
        return new SetIterator(parentMap.hash);
    }

    // Il metodo toArray() è ereditato da ValueCollection, ma deve essere adattato
    // per restituire le chiavi invece dei valori.

    // Il metodo toArray(Object[] a) è ereditato da ValueCollection e richiede
    // un adattamento simile a toArray().

    // Il metodo add(Object o) non è supportato perché l'aggiunta di una chiave
    // senza un valore non ha senso in una vista di chiavi di una mappa.
    // L'operazione è gestita in ValueCollection, che lancia
    // UnsupportedOperationException.

    /**
     * Rimuove la chiave specificata da questo set (e quindi dalla mappa di
     * supporto).
     * <p>
     * Se la chiave non è presente nel set, il metodo non esegue alcuna operazione
     * e restituisce {@code false}.
     *
     * @param o la chiave da rimuovere da questo set.
     * @return {@code true} se il set è stato modificato a seguito di questa
     *         operazione.
     * @throws NullPointerException se la chiave specificata è {@code null}.
     */
    @Override
    public boolean remove(Object o) {
        // Le chiavi null non sono ammesse, quindi viene lanciata un'eccezione.
        if (o == null)
            throw new NullPointerException();

        // Se la chiave non è presente, non c'è nulla da rimuovere.
        if (!parentMap.containsKey(o))
            return false;

        // La rimozione viene delegata alla mappa di supporto.
        parentMap.remove(o);
        return true;
    }

    /**
     * Verifica se questo set contiene tutte le chiavi della collezione specificata.
     *
     * @param c la collezione da verificare per l'inclusione in questo set.
     * @return {@code true} se questo set contiene tutte le chiavi della collezione
     *         specificata.
     * @throws NullPointerException se la collezione specificata è {@code null} o
     *                              se contiene elementi {@code null}.
     */
    @Override
    public boolean containsAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();

        boolean found = true;
        HIterator it = c.iterator();
        // Itera su tutti gli elementi della collezione 'c'.
        while (it.hasNext() && found) {
            // Controlla se ogni elemento è presente nella mappa di supporto.
            // L'operatore &= garantisce che 'found' diventi false al primo elemento non
            // trovato.
            found &= parentMap.containsKey(it.next());
        }

        return found;
    }

    // Il metodo addAll(HCollection c) non è supportato, come per add(Object o).
    // L'operazione è gestita in ValueCollection.

    /**
     * Rimuove da questo set tutte le sue chiavi che sono contenute anche nella
     * collezione specificata.
     * <p>
     * A seguito di questa operazione, questo set non conterrà alcuna chiave in
     * comune
     * con la collezione specificata.
     *
     * @param c la collezione contenente le chiavi da rimuovere da questo set.
     * @return {@code true} se il set è stato modificato a seguito di questa
     *         operazione.
     * @throws NullPointerException se la collezione specificata è {@code null} o
     *                              se contiene elementi {@code null}.
     */
    @Override
    public boolean removeAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        // Se la collezione 'c' è vuota, non c'è nulla da rimuovere.
        if (c.size() == 0)
            return false;

        HIterator it = c.iterator();
        boolean removed = false;
        // Itera sulla collezione di elementi da rimuovere.
        while (it.hasNext()) {
            Object tmp = it.next();
            // Se la chiave è presente nel set...
            if (parentMap.containsKey(tmp)) {
                // ...la rimuove.
                remove(tmp);
                // Imposta il flag per indicare che il set è stato modificato.
                removed = true;
            }
        }

        return removed;
    }

    // Il metodo retainAll(HCollection c) è ereditato da ValueCollection e richiede
    // un'implementazione specifica per mantenere solo le chiavi presenti anche
    // nella collezione data.

    // Il metodo clear() è ereditato da ValueCollection e funziona correttamente
    // svuotando la mappa di supporto.

    /**
     * Confronta l'oggetto specificato con questo set per verificarne l'uguaglianza.
     * <p>
     * Restituisce {@code true} se e solo se l'oggetto specificato è anch'esso un
     * set,
     * i due set hanno la stessa dimensione e ogni membro del set specificato è
     * contenuto in questo set.
     *
     * @param o l'oggetto da confrontare con questo set.
     * @return {@code true} se l'oggetto specificato è uguale a questo set.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof HSet == false)
            return false;

        HSet tmp = (HSet) o;

        // Due set sono uguali se hanno la stessa dimensione e contengono gli stessi
        // elementi. La chiamata a containsAll verifica la seconda condizione.
        return size() == tmp.size() && containsAll(tmp);
    }

    // Il metodo hashCode() è ereditato da ValueCollection e dovrebbe essere
    // sovrascritto per rispettare l'interfaccia di Object, secondo cui
    // due oggetti uguali devono avere lo stesso hashCode.

    // ---------------------- CLASSI PRIVATE INTERNE ----------------------

    /**
     * Implementazione di {@link HIterator} specifica per il set di chiavi.
     * Questo iteratore permette di scorrere le chiavi della mappa di supporto
     * e di rimuoverle in modo sicuro durante l'iterazione.
     */
    private class SetIterator implements HIterator {
        // Campi privati
        protected Hashtable parent;
        private Enumeration en;
        private boolean canRemove; // Flag per garantire che remove() sia chiamato solo dopo next()
        private Object lastReturned; // Memorizza l'ultimo elemento restituito da next()

        /**
         * Costruisce un iteratore basato sulla {@code Hashtable} di supporto.
         *
         * @param h la {@code Hashtable} su cui l'iteratore opererà.
         */
        public SetIterator(Hashtable h) {
            this.parent = h;
            // Ottiene un'enumerazione delle chiavi dalla Hashtable.
            this.en = parent.keys();
            this.canRemove = false; // Inizialmente non è possibile rimuovere elementi.
        }

        /**
         * Verifica se ci sono altre chiavi nell'iterazione.
         *
         * @return {@code true} se l'iteratore ha ancora elementi.
         */
        @Override
        public boolean hasNext() {
            return en.hasMoreElements();
        }

        /**
         * Restituisce la prossima chiave nell'iterazione.
         *
         * @return la prossima chiave nel set.
         * @throws NoSuchElementException se non ci sono più elementi nell'iterazione.
         */
        @Override
        public Object next() {
            if (!hasNext())
                throw new NoSuchElementException();

            // Abilita la possibilità di chiamare remove() per l'elemento corrente.
            canRemove = true;
            // Salva l'elemento restituito per un'eventuale rimozione.
            lastReturned = en.nextElement();
            return lastReturned;
        }

        /**
         * Rimuove dalla mappa di supporto l'ultima chiave restituita da questo
         * iteratore.
         * Questo metodo può essere chiamato una sola volta per ogni chiamata a
         * {@code next()}.
         *
         * @throws IllegalStateException se il metodo {@code next()} non è stato ancora
         *                               chiamato, o se {@code remove()} è già stato
         *                               chiamato dopo l'ultima
         *                               chiamata a {@code next()}.
         */
        @Override
        public void remove() {
            if (!canRemove)
                throw new IllegalStateException();

            // Disabilita la possibilità di chiamare remove() di nuovo prima della prossima
            // chiamata a next().
            canRemove = false;
            // Rimuove l'elemento dalla Hashtable di supporto usando la chiave salvata.
            parent.remove(lastReturned);
        }
    }
}
