package myAdapter;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

/**
 * Rappresenta una vista (view) dei valori contenuti in una {@code MapAdapter}.
 * Questa classe fornisce un'implementazione dell'interfaccia
 * {@link HCollection} per la collezione di valori della mappa.
 * <p>
 * A differenza di un set, una collezione di valori può contenere elementi
 * duplicati. Le modifiche apportate a questa collezione si riflettono sulla
 * mappa originale e viceversa. Ad esempio, se un valore viene rimosso da questa
 * collezione, la corrispondente entry (chiave-valore) viene rimossa dalla mappa
 * di supporto (backing map).
 *
 * @see HCollection
 * @see MapAdapter
 */
public class ValueCollection implements HCollection {
    /**
     * La mappa di supporto (backing map) a cui questa collezione fa riferimento.
     * Le operazioni su questa collezione vengono delegate alla mappa.
     */
    protected MapAdapter parentMap = null;

    /**
     * Costruisce una nuova collezione di valori basata sulla mappa fornita.
     * La collezione creata è una vista "live" dei valori della mappa.
     *
     * @param p la {@code MapAdapter} di supporto da cui creare la collezione di
     *          valori.
     */
    protected ValueCollection(MapAdapter p) {
        this.parentMap = p;
    }

    // ----------------------- METODI PUBBLICI ------------------

    /**
     * Restituisce il numero di valori in questa collezione.
     * Corrisponde alla dimensione della mappa di supporto.
     *
     * @return il numero di valori nella collezione.
     */
    @Override
    public int size() {
        return parentMap.size();
    }

    /**
     * Verifica se questa collezione è vuota.
     *
     * @return {@code true} se la collezione non contiene valori.
     */
    @Override
    public boolean isEmpty() {
        return parentMap.isEmpty();
    }

    /**
     * Verifica se questa collezione contiene il valore specificato.
     * <p>
     * La verifica viene delegata al metodo {@code containsValue} della mappa di
     * supporto.
     *
     * @param o il valore la cui presenza in questa collezione deve essere
     *          verificata.
     * @return {@code true} se questa collezione contiene almeno un'istanza del
     *         valore specificato.
     * @throws NullPointerException se l'oggetto specificato è {@code null}.
     */
    @Override
    public boolean contains(Object o) {
        return parentMap.containsValue(o);
    }

    /**
     * Restituisce un iteratore sugli elementi di questa collezione.
     * Non vi è alcuna garanzia riguardo all'ordine in cui gli elementi vengono
     * restituiti.
     *
     * @return un {@link HIterator} sui valori della collezione.
     */
    @Override
    public HIterator iterator() {
        return new CollectionIterator(parentMap.hash);
    }

    /**
     * Restituisce un array contenente tutti i valori di questa collezione.
     * <p>
     * L'array restituito è una nuova copia e può essere modificato liberamente
     * dal chiamante senza influenzare la collezione originale.
     *
     * @return un array di oggetti contenente tutti i valori della collezione.
     */
    @Override
    public Object[] toArray() {
        HIterator it = iterator();
        Object[] arr = new Object[parentMap.size()];
        for (int i = 0; it.hasNext(); i++)
            arr[i] = it.next();
        return arr;
    }

    /**
     * Restituisce un array contenente tutti i valori di questa collezione; il tipo
     * di runtime dell'array restituito è quello dell'array specificato.
     * <p>
     * Se la collezione può essere contenuta nell'array specificato, viene
     * restituito
     * tale array. Altrimenti, viene allocato un nuovo array con il tipo di runtime
     * dell'array specificato e la dimensione di questa collezione.
     *
     * @param a l'array in cui inserire i valori della collezione, se
     *          sufficientemente grande;
     *          altrimenti, un nuovo array dello stesso tipo di runtime viene
     *          allocato.
     * @return un array contenente i valori della collezione.
     * @throws NullPointerException se l'array specificato è {@code null}.
     * @throws ArrayStoreException  se il tipo di runtime dell'array specificato non
     *                              è
     *                              un supertipo del tipo di runtime di ogni
     *                              elemento in questa collezione.
     */
    @Override
    public Object[] toArray(Object[] a) {
        if (a == null)
            throw new NullPointerException();

        Object[] collectionArray = toArray();
        // Verifica la compatibilità dei tipi prima di copiare.
        for (int i = 0; i < collectionArray.length; i++) {
            if (!a.getClass().getComponentType().isInstance(collectionArray[i]))
                throw new ArrayStoreException();
        }

        if (a.length >= parentMap.size()) {
            // Pulisce l'array (se più grande) e copia gli elementi.
            for (int i = 0; i < a.length; i++)
                a[i] = null;
            System.arraycopy(collectionArray, 0, a, 0, collectionArray.length);
            return a;
        } else {
            // Se l'array è troppo piccolo, ne crea uno nuovo della dimensione giusta.
            return collectionArray;
        }
    }

    /**
     * Operazione non supportata.
     * <p>
     * L'aggiunta di un valore a una vista di valori non è permessa perché
     * richiederebbe la creazione di una chiave associata, che non può essere
     * determinata implicitamente.
     *
     * @param o l'oggetto da aggiungere.
     * @return mai, poiché lancia sempre un'eccezione.
     * @throws UnsupportedOperationException sempre.
     */
    @Override
    public boolean add(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Rimuove una singola istanza del valore specificato da questa collezione.
     * <p>
     * Se il valore è presente più volte, viene rimossa solo la prima occorrenza
     * trovata durante l'iterazione delle chiavi della mappa.
     *
     * @param o il valore da rimuovere da questa collezione.
     * @return {@code true} se la collezione è stata modificata.
     * @throws NullPointerException se il valore specificato è {@code null}.
     */
    @Override
    public boolean remove(Object o) {
        if (o == null)
            throw new NullPointerException();
        if (!parentMap.containsValue(o))
            return false;

        boolean removed = false;
        HIterator it = parentMap.keySet().iterator();
        // Itera sulle chiavi per trovare l'entry con il valore corrispondente.
        while (it.hasNext() && !removed) {
            Object key = it.next();
            if (parentMap.get(key).equals(o)) {
                it.remove(); // Rimuove l'entry tramite l'iteratore del keySet.
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Verifica se questa collezione contiene tutti i valori della collezione
     * specificata.
     *
     * @param c la collezione da verificare per l'inclusione.
     * @return {@code true} se questa collezione contiene tutti i valori di
     *         {@code c}.
     * @throws NullPointerException se la collezione specificata è {@code null} o
     *                              se contiene elementi {@code null}.
     */
    @Override
    public boolean containsAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();

        boolean contains = true;
        HIterator it = c.iterator();
        while (it.hasNext() && contains)
            contains = parentMap.containsValue(it.next());

        return contains;
    }

    /**
     * Operazione non supportata.
     * <p>
     * Simile ad {@code add(Object)}, l'aggiunta di una collezione di valori non è
     * permessa su una vista di valori.
     *
     * @param c la collezione di elementi da aggiungere.
     * @return mai, poiché lancia sempre un'eccezione.
     * @throws UnsupportedOperationException sempre.
     */
    @Override
    public boolean addAll(HCollection c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Rimuove da questa collezione tutti i suoi valori che sono contenuti anche
     * nella collezione specificata.
     *
     * @param c la collezione contenente i valori da rimuovere.
     * @return {@code true} se la collezione è stata modificata.
     * @throws NullPointerException se la collezione specificata è {@code null} o
     *                              se contiene elementi {@code null}.
     */
    @Override
    public boolean removeAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        if (c.size() == 0)
            return false;

        boolean modified = false;
        HIterator it = c.iterator();
        // Per ogni elemento nella collezione 'c'...
        while (it.hasNext()) {
            Object objToRemove = it.next();
            // ...rimuove tutte le sue occorrenze da questa collezione.
            while (remove(objToRemove)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Mantiene in questa collezione solo i valori che sono contenuti anche nella
     * collezione specificata. In altre parole, rimuove da questa collezione tutti
     * i valori che non sono presenti nella collezione specificata.
     *
     * @param c la collezione che definisce quali valori mantenere.
     * @return {@code true} se la collezione è stata modificata.
     * @throws NullPointerException se la collezione specificata è {@code null}.
     */
    @Override
    public boolean retainAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();

        boolean modified = false;
        HIterator it = iterator();
        // Itera su questa collezione.
        while (it.hasNext()) {
            // Se un valore non è contenuto nella collezione 'c'...
            if (!c.contains(it.next())) {
                // ...lo rimuove.
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Rimuove tutti i valori da questa collezione.
     * L'operazione svuota la mappa di supporto.
     */
    @Override
    public void clear() {
        parentMap.clear();
    }

    /**
     * Confronta l'oggetto specificato con questa collezione per verificarne
     * l'uguaglianza.
     * <p>
     * Due collezioni sono considerate uguali se sono entrambe istanze di
     * {@code HCollection} (ma non {@code HSet}), hanno la stessa dimensione e
     * contengono gli stessi elementi (indipendentemente dall'ordine).
     *
     * @param o l'oggetto da confrontare.
     * @return {@code true} se l'oggetto specificato è uguale a questa collezione.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o instanceof HCollection == false || o instanceof HSet == true)
            return false;

        HCollection c = (HCollection) o;
        if (c.size() != size()) // Se non hanno la stessa dimensione allora sicuramente non sono uguali
            return false;

        // Siccome nelle collezioni sono possibili i duplicati devo
        // confrontare la cardinalità di ogni elemento.
        // Per farlo utilizzo una struttura d'appoggio
        Hashtable frequencies = new Hashtable();

        // 1. Conta le frequenze degli elementi in questa collezione.
        HIterator it = iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            Integer count = (Integer) frequencies.get(obj);
            if (count == null) {
                // Primo conteggio dell'oggetto corrente
                frequencies.put(obj, new Integer(1));
            } else {
                frequencies.put(obj, new Integer(count.intValue() + 1));
            }
        }

        // 9. Decremento le frequenze usando gli elementi della collezione 'c'.
        it = c.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            Integer count = (Integer) frequencies.get(obj);
            if (count == null || count.intValue() == 0)
                // Trovato un elemento in 'c' che non c'è oppure uno stesso
                // elemento che però in 'c' si presenta una cardinalità maggiore
                return false;

            frequencies.put(obj, new Integer(count.intValue() - 1));
        }

        return true;
    }

    /**
     * Restituisce l'hash code per questa collezione.
     * L'hash code è calcolato come la somma degli hash code di tutti i suoi valori.
     *
     * @return l'hash code della collezione.
     */
    @Override
    public int hashCode() {
        int hashCode = 0;
        HIterator it = iterator();
        while (it.hasNext())
            hashCode += it.next().hashCode();
        return hashCode;
    }

    // -------------------- CLASSI PRIVATE INTERNE ----------------

    /**
     * Implementazione di {@link HIterator} specifica per la collezione di valori.
     * Questo iteratore permette di scorrere i valori della mappa di supporto
     * e di rimuoverli in modo sicuro durante l'iterazione.
     */
    private class CollectionIterator implements HIterator {
        // Campi privati
        protected Hashtable parent;
        private Enumeration en;
        private boolean canRemove; // Flag per garantire che remove() sia chiamato solo dopo next()
        private Object lastReturned; // Memorizza l'ultimo valore restituito da next()

        /**
         * Costruisce un iteratore basato sulla {@code Hashtable} di supporto.
         *
         * @param h la {@code Hashtable} su cui l'iteratore opererà.
         */
        public CollectionIterator(Hashtable h) {
            this.parent = h;
            // Ottiene un'enumerazione dei valori (elementi) dalla Hashtable.
            this.en = parent.elements();
            this.canRemove = false; // Inizialmente non è possibile rimuovere elementi.
        }

        /**
         * Verifica se ci sono altri valori nell'iterazione.
         *
         * @return {@code true} se l'iteratore ha ancora elementi.
         */
        @Override
        public boolean hasNext() {
            return en.hasMoreElements();
        }

        /**
         * Restituisce il prossimo valore nell'iterazione.
         *
         * @return il prossimo valore nella collezione.
         * @throws NoSuchElementException se non ci sono più elementi nell'iterazione.
         */
        @Override
        public Object next() {
            if (!hasNext())
                throw new NoSuchElementException();

            canRemove = true;
            lastReturned = en.nextElement();
            return lastReturned;
        }

        /**
         * Rimuove dalla mappa di supporto l'ultima entry corrispondente al valore
         * restituito da questo iteratore.
         *
         * @throws IllegalStateException se {@code next()} non è stato ancora chiamato,
         *                               o se {@code remove()} è già stato chiamato dopo
         *                               l'ultima chiamata a {@code next()}.
         */
        @Override
        public void remove() {
            if (!canRemove)
                throw new IllegalStateException(
                        "Il metodo remove() può essere chiamato solo una volta dopo ogni chiamata a next().");

            canRemove = false;
            boolean found = false;
            // Per rimuovere un valore, dobbiamo trovare la chiave corrispondente.
            Enumeration keys = parent.keys();
            while (keys.hasMoreElements() && !found) {
                Object key = keys.nextElement();
                if (parent.get(key).equals(lastReturned)) {
                    parent.remove(key);
                    found = true;
                }
            }
        }
    }

    /**
     * Restituisce una rappresentazione testuale della collezione.
     * La stringa è nel formato "[elemento1, elemento2, ...]".
     *
     * @return una rappresentazione stringa della collezione.
     */
    @Override
    public String toString() {
        HIterator it = iterator();
        if (it.hasNext() == false) {
            return "[]";
        }

        String s = "[";
        while (it.hasNext()) {
            Object o = it.next();
            s += o.toString();

            if (it.hasNext())
                s += ", ";
        }
        s += "]";

        return s;
    }
}
