package myAdapter;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

/**
 * Classe che adatta una {@code Hashtable} per conformarsi all'interfaccia
 * {@link HMap}.
 * Questa classe fornisce un'implementazione di mappa basata su una tabella
 * hash, consentendo la memorizzazione di oggetti di qualsiasi tipo (Object).
 * I metodi sono strutturati per essere conformi alle specifiche di una mappa
 * standard.
 * 
 */
public class MapAdapter implements HMap {
    /**
     * La {@code Hashtable} sottostante che funge da struttura dati per questa
     * mappa.
     * È dichiarata come {@code protected} per essere accessibile dalle classi
     * interne.
     */
    protected Hashtable hash;

    /**
     * Costruisce una nuova mappa vuota.
     * Inizializza la {@code Hashtable} interna.
     */
    public MapAdapter() {
        hash = new Hashtable();
    }

    /**
     * Costruisce una nuova mappa contenente le stesse mappature della mappa
     * specificata.
     * Questo è un costruttore di copia.
     * 
     * @param m la mappa da cui copiare le mappature.
     */
    public MapAdapter(HMap m) {
        hash = new Hashtable();
        putAll(m);
    }

    /**
     * Restituisce il numero di mappature chiave-valore in questa mappa.
     * 
     * @return il numero di entry nella mappa.
     */
    @Override
    public int size() {
        return hash.size();
    }

    /**
     * Restituisce {@code true} se questa mappa non contiene mappature
     * chiave-valore.
     * 
     * @return {@code true} se la mappa è vuota, {@code false} altrimenti.
     */
    @Override
    public boolean isEmpty() {
        return hash.isEmpty();
    }

    /**
     * Restituisce {@code true} se questa mappa contiene una mappatura per la chiave
     * specificata.
     * 
     * @param key la chiave la cui presenza in questa mappa deve essere verificata.
     * @return {@code true} se questa mappa contiene una mappatura per la chiave
     *         specificata.
     * @throws NullPointerException se la chiave è {@code null}.
     */
    @Override
    public boolean containsKey(Object key) {
        if (key == null)
            throw new NullPointerException();

        return hash.containsKey(key);
    }

    /**
     * Restituisce {@code true} se questa mappa mappa una o più chiavi al valore
     * specificato.
     * 
     * @param value il valore la cui presenza in questa mappa deve essere
     *              verificata.
     * @return {@code true} se questa mappa mappa una o più chiavi al valore
     *         specificato.
     * @throws NullPointerException se il valore è {@code null}.
     */
    @Override
    public boolean containsValue(Object value) {
        if (value == null)
            throw new NullPointerException();

        return hash.contains(value);
    }

    /**
     * Restituisce il valore a cui è mappata la chiave specificata, o {@code null}
     * se questa mappa non contiene mappature per la chiave.
     * 
     * @param key la chiave il cui valore associato deve essere restituito.
     * @return il valore associato alla chiave, o {@code null} se non esiste alcuna
     *         mappatura.
     * @throws NullPointerException se la chiave è {@code null}.
     */
    @Override
    public Object get(Object key) {
        if (key == null)
            throw new NullPointerException();

        return hash.get(key);
    }

    /**
     * Associa il valore specificato alla chiave specificata in questa mappa.
     * Se la mappa conteneva precedentemente una mappatura per la chiave, il vecchio
     * valore viene sostituito.
     * 
     * @param key   la chiave con cui il valore specificato deve essere associato.
     * @param value il valore da associare alla chiave specificata.
     * @return il valore precedente associato alla chiave, o {@code null} se non
     *         c'era alcuna mappatura per la chiave.
     * @throws NullPointerException se la chiave o il valore sono {@code null}.
     */
    @Override
    public Object put(Object key, Object value) {
        if (key == null || value == null)
            throw new NullPointerException();

        return hash.put(key, value);
    }

    /**
     * Rimuove la mappatura per una chiave da questa mappa, se presente.
     * 
     * @param key la chiave la cui mappatura deve essere rimossa dalla mappa.
     * @return il valore precedente associato alla chiave, o {@code null} se non
     *         c'era alcuna mappatura per la chiave.
     * @throws NullPointerException se la chiave è {@code null}.
     */
    @Override
    public Object remove(Object key) {
        if (key == null)
            throw new NullPointerException();

        return hash.remove(key);
    }

    /**
     * Copia tutte le mappature dalla mappa specificata a questa mappa.
     * Queste mappature sostituiranno qualsiasi mappatura che questa mappa aveva per
     * una qualsiasi delle chiavi
     * attualmente nella mappa specificata.
     * 
     * @param sourceHMap la mappa le cui mappature devono essere memorizzate in
     *                   questa mappa.
     * @throws NullPointerException se la mappa specificata è {@code null} o
     *                              contiene chiavi/valori {@code null}.
     */
    @Override
    public void putAll(HMap sourceHMap) {
        if (sourceHMap == null)
            throw new NullPointerException();

        // Se la mappa specificata è questa stessa mappa, non fare nulla.
        if (sourceHMap.equals(this)) {
            return;
        }

        // Itera sulle chiavi della mappa specificata e aggiungi le entry a questa
        // mappa.
        HIterator it = sourceHMap.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            Object value = sourceHMap.get(key);
            if (key == null || value == null) {
                throw new NullPointerException();
            }
            hash.put(key, value);
        }
    }

    /**
     * Rimuove tutte le mappature da questa mappa.
     * La mappa sarà vuota dopo che questa chiamata ritorna.
     */
    @Override
    public void clear() {
        hash.clear();
    }

    /**
     * Restituisce una vista {@link HSet} delle chiavi contenute in questa mappa.
     * Il set è supportato dalla mappa, quindi le modifiche alla mappa si riflettono
     * nel set e viceversa.
     * 
     * @return una vista set delle chiavi contenute in questa mappa.
     */
    @Override
    public HSet keySet() {
        return new KeySet(this);
    }

    /**
     * Restituisce una vista {@link HCollection} dei valori contenuti in questa
     * mappa.
     * La collezione è supportata dalla mappa, quindi le modifiche alla mappa si
     * riflettono nella collezione e viceversa.
     * 
     * @return una vista collezione dei valori contenuti in questa mappa.
     */
    @Override
    public HCollection values() {
        return new ValueCollection(this);
    }

    /**
     * Restituisce una vista {@link HSet} delle mappature contenute in questa mappa.
     * Ogni elemento in questo set è una {@link HEntry}.
     * 
     * @return una vista set delle mappature contenute in questa mappa.
     */
    @Override
    public HSet entrySet() {
        return new EntrySet(this);
    }

    /**
     * Confronta l'oggetto specificato con questa mappa per l'uguaglianza.
     * Restituisce {@code true} se l'oggetto dato è anche una mappa e le due mappe
     * rappresentano le stesse mappature.
     * 
     * @param o l'oggetto da confrontare con questa mappa per l'uguaglianza.
     * @return {@code true} se l'oggetto specificato è uguale a questa mappa.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HMap)) {
            return false;
        }
        HMap tmp = (HMap) o;
        // Due mappe sono uguali se i loro entrySet sono uguali.
        return entrySet().equals(tmp.entrySet());
    }

    /**
     * Restituisce il codice hash per questa mappa.
     * Il codice hash di una mappa è definito come la somma dei codici hash di ogni
     * entry nel set di entry della mappa.
     * 
     * @return il codice hash per questa mappa.
     */
    @Override
    public int hashCode() {
        return entrySet().hashCode();
    }

    /**
     * Restituisce una rappresentazione testuale di questa mappa.
     * La stringa è nel formato "{chiave1=valore1, chiave2=valore2, ...}".
     *
     * @return una rappresentazione stringa della mappa.
     */
    @Override
    public String toString() {
        HIterator it = entrySet().iterator();
        if (it.hasNext() == false)
            return "{}";

        String s = "{";
        while (it.hasNext()) {
            HEntry entry = (HEntry) it.next();
            s += entry.getKey() + ": " + entry.getValue();
            if (it.hasNext())
                s += ", ";
        }
        s += "}";

        return s;
    }

    // ---------------------- CLASSI PRIVATE ----------------------

    /**
     * Classe privata che rappresenta una singola entry (coppia chiave-valore) in
     * una mappa.
     * <p>
     * In realtà rappresenta una vista su una coppia chiave-valore, ma
     * non memorizza direttamente il valore.
     * Per ottenere o modificare il valore associato a quella chiave, l' Entry deve
     * comunicare con la mappa principale che contiene i dati reali
     * Implementa l'interfaccia {@link HEntry}.
     */
    private class Entry implements HEntry {
        private Object key;
        private MapAdapter parentMap;// serve come "ponte" per permettere all'Entry
                                     // di usare i metodi della MapAdapter

        /**
         * Costruttore che crea una nuova entry con la chiave e la mappa genitore
         * specificate.
         * 
         * @param p la mappa genitore che contiene questa entry.
         * @param k la chiave di questa entry.
         */
        public Entry(MapAdapter p, Object k) {
            parentMap = p;
            key = k;
        }

        /**
         * Restituisce la chiave di questa entry.
         * 
         * @return la chiave.
         */
        @Override
        public Object getKey() {
            return key;
        }

        /**
         * Restituisce il valore di questa entry.
         * 
         * @return il valore.
         */
        @Override
        public Object getValue() {
            return parentMap.get(key);
        }

        /**
         * Sostituisce il valore di questa entry con il valore specificato.
         * 
         * @param value il nuovo valore da memorizzare in questa entry.
         * @return il vecchio valore.
         * @throws NullPointerException se il nuovo valore è {@code null}.
         */
        @Override
        public Object setValue(Object value) {
            if (value == null)
                throw new NullPointerException();

            return parentMap.put(key, value);
        }

        /**
         * Confronta questa entry con un altro oggetto per l'uguaglianza.
         * 
         * @param o l'oggetto da confrontare.
         * @return {@code true} se l'oggetto è un'entry con la stessa chiave e lo stesso
         *         valore.
         */
        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof HEntry == false)
                return false;

            HEntry e = (HEntry) o;
            return getKey().equals(e.getKey()) && getValue().equals(e.getValue());
        }

        /**
         * Restituisce il codice hash per questa entry.
         * 
         * @return il codice hash.
         */
        @Override
        public int hashCode() {
            return (getKey() == null ? 0 : getKey().hashCode()) ^
                    (getValue() == null ? 0 : getValue().hashCode());
        }

        /**
         * Restituisce una rappresentazione testuale di questa entry.
         * Il formato è "chiave=valore".
         *
         * @return una stringa che rappresenta la coppia chiave-valore.
         */
        @Override
        public String toString() {
            return getKey() + ": " + getValue();
        }
    }

    /**
     * Classe privata che fornisce una vista come {@link HSet} delle entry della
     * mappa. Estende {@code ValueCollection} e implementa {@link HSet}.
     */
    private class EntrySet extends ValueCollection implements HSet {

        /**
         * Costruisce un nuovo set di entry per la mappa genitore specificata.
         * 
         * @param parentMap la mappa genitore.
         */
        public EntrySet(MapAdapter parentMap) {
            super(parentMap);
        }

        /**
         * Verifica se questo set contiene l'oggetto specificato.
         * 
         * @param o l'oggetto da cercare.
         * @return {@code true} se l'oggetto è contenuto nel set.
         * @throws NullPointerException se l'oggetto è {@code null}.
         * @throws ClassCastException   se l'oggetto non è di tipo {@code Entry}.
         */
        @Override
        public boolean contains(Object o) {
            if (o == null)
                throw new NullPointerException();
            if (!(o instanceof HEntry)) {
                throw new ClassCastException();
            }
            HEntry tmp = (HEntry) o;
            Object key = tmp.getKey();
            Object value = tmp.getValue();
            return parentMap.containsKey(key) && parentMap.get(key).equals(value);
        }

        /**
         * Restituisce un iteratore su questo set di entry.
         * 
         * @return un {@link HIterator}.
         */
        @Override
        public HIterator iterator() {
            return new EntrySetIterator(parentMap);
        }

        /**
         * Rimuove l'oggetto specificato da questo set.
         * 
         * @param o l'oggetto da rimuovere.
         * @return {@code true} se il set è stato modificato.
         * @throws NullPointerException se l'oggetto è {@code null}.
         * @throws ClassCastException   se l'oggetto non è di tipo {@code Entry}.
         */
        @Override
        public boolean remove(Object o) {
            if (o == null)
                throw new NullPointerException();
            if (o instanceof HEntry == false)
                throw new ClassCastException();

            if (contains(o)) {
                HEntry tmp = (HEntry) o;
                Object key = tmp.getKey();
                return parentMap.remove(key) != null;
            }
            return false;
        }

        /**
         * Verifica se questo set contiene tutti gli elementi della collezione
         * specificata.
         * 
         * @param c la collezione da verificare.
         * @return {@code true} se tutti gli elementi sono contenuti.
         * @throws NullPointerException se la collezione è {@code null} o contiene
         *                              elementi {@code null}.
         * @throws ClassCastException   se un elemento della collezione non è di tipo
         *                              {@code Entry}.
         */
        @Override
        public boolean containsAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();

            HIterator it = c.iterator();
            while (it.hasNext()) {
                Object tmp = it.next();
                if (!(tmp instanceof HEntry)) {
                    throw new ClassCastException();
                }
                if (!contains(tmp)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Rimuove da questo set tutti gli elementi contenuti nella collezione
         * specificata.
         * 
         * @param c la collezione contenente gli elementi da rimuovere.
         * @return {@code true} se il set è stato modificato.
         * @throws NullPointerException se la collezione è {@code null} o contiene
         *                              elementi {@code null}.
         * @throws ClassCastException   se un elemento della collezione non è di tipo
         *                              {@code Entry}.
         */
        @Override
        public boolean removeAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            if (c.isEmpty())
                return false;

            boolean modified = false;
            HIterator it = c.iterator();
            while (it.hasNext()) {
                Object tmp = it.next();
                if (!(tmp instanceof HEntry))
                    throw new ClassCastException();
                if (remove(tmp))
                    modified = true;
            }
            return modified;
        }

        /**
         * Confronta questo set con un altro oggetto per l'uguaglianza.
         * 
         * @param o l'oggetto da confrontare.
         * @return {@code true} se l'oggetto è un set con gli stessi elementi.
         */
        @Override
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof HSet == false)
                return false;

            HSet tmp = (HSet) o;
            return size() == tmp.size() && containsAll(tmp);
        }

        /**
         * Classe privata che implementa un iteratore per il set di entry.
         */
        private class EntrySetIterator implements HIterator {
            protected MapAdapter parentMap;
            private Enumeration en;
            private boolean canRemove; // Flag per controllare se remove() può essere chiamato
            private Entry lastReturned;

            /**
             * Costruisce un nuovo iteratore per il set di entry.
             * 
             * @param p la mappa genitore.
             */
            public EntrySetIterator(MapAdapter p) {
                parentMap = p;
                en = parentMap.hash.keys();
                canRemove = false;
            }

            /**
             * Verifica se ci sono altri elementi nell'iterazione.
             * 
             * @return {@code true} se ci sono altri elementi.
             */
            @Override
            public boolean hasNext() {
                return en.hasMoreElements();
            }

            /**
             * Restituisce il prossimo elemento nell'iterazione.
             * 
             * @return il prossimo elemento.
             * @throws NoSuchElementException se non ci sono altri elementi.
             */
            @Override
            public Object next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                canRemove = true;
                Object key = en.nextElement();
                lastReturned = new Entry(parentMap, key);
                return lastReturned;
            }

            /**
             * Rimuove l'ultimo elemento restituito da {@code next()}.
             * 
             * @throws IllegalStateException se {@code next()} non è stato chiamato o
             *                               {@code remove()} è già stato chiamato.
             */
            @Override
            public void remove() {
                if (canRemove == false)
                    throw new IllegalStateException();

                canRemove = false;
                parentMap.remove(lastReturned.getKey());
            }
        }
    }
}