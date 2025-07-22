package myAdapter;

/**
 * Un oggetto che mappa chiavi a valori. Una mappa non può contenere chiavi
 * duplicate; ogni chiave può mappare al massimo un valore. <br>
 * L'interfaccia HMap fornisce tre viste "collection" che permettono di vedere i
 * contenuti di una mappa come un insieme di chiavi, una collezione di valori, o
 * un insieme di mappature chiave-valore.
 * 
 * @see HSet
 * @see HCollection
 */
public interface HMap {
    /**
     * Restituisce il numero di entry in questa mappa.
     * 
     * @return il numero di entry in questa mappa.
     */
    public int size();

    /**
     * Restituisce {@code true} se questa mappa non contiene entry
     * 
     * @return {@code true} se questa mappa non contiene entry.
     */
    public boolean isEmpty();

    /**
     * Restituisce {@code true} se questa mappa contiene una entry per la chiave
     * specificata.
     * 
     * @param key la chiave la cui presenza in questa mappa deve essere testata.
     * @return {@code true} se questa mappa contiene una entry per la chiave
     *         specificata.
     */
    public boolean containsKey(Object key);

    /**
     * Restituisce {@code true} se questa mappa associa una o più chiavi al valore
     * specificato.
     * 
     * @param value il valore la cui presenza in questa mappa deve essere testata.
     * @return {@code true} se questa mappa associa una o più chiavi al valore
     *         specificato.
     */
    public boolean containsValue(Object value);

    /**
     * Restituisce il valore a cui questa mappa associa la chiave specificata, o
     * {@code null} se questa mappa non contiene entry per la chiave.
     * 
     * @param key la chiave il cui valore associato deve essere restituito.
     * @return il valore a cui questa mappa associa la chiave specificata, o
     *         {@code null} se la mappa non contiene entry per la chiave.
     */
    public Object get(Object key);

    /**
     * Associa il valore specificato con la chiave specificata in questa mappa.
     * Se la mappa conteneva precedentemente una entry per questa chiave, il
     * vecchio valore viene sostituito.
     * 
     * @param key   la chiave con cui il valore specificato deve essere associato.
     * @param value il valore da associare con la chiave specificata.
     * @return il valore precedente associato a {@code key}, o {@code null} se non
     *         c'era alcuna entry per {@code key}.
     */
    public Object put(Object key, Object value);

    /**
     * Rimuove la entry per questa chiave da questa mappa se presente.
     * 
     * @param key la chiave la cui entry deve essere rimossa dalla mappa.
     * @return il valore precedente associato a {@code key}, o {@code null} se non
     *         c'era alcuna entry per {@code key}.
     */
    public Object remove(Object key);

    /**
     * Copia tutte le mappature dalla mappa sorgente passata per argomento in questa
     * mappa (this).
     * 
     * @param t la mappa da copiare.
     */
    public void putAll(HMap t);

    /**
     * Rimuove tutte le mappature da questa mappa.
     */
    public void clear();

    /**
     * Restituisce una vista {@code HSet} delle chiavi contenute in questa mappa.
     * Il set è supportato dalla mappa, quindi le modifiche alla mappa si riflettono
     * sul set, e viceversa.
     * 
     * @return una vista set delle chiavi contenute in questa mappa.
     */
    public HSet keySet();

    /**
     * Restituisce una vista {@code HCollection} dei valori contenuti in questa
     * mappa.
     * La collezione è supportata dalla mappa, quindi le modifiche alla mappa si
     * riflettono sulla collezione, e viceversa.
     * 
     * @return una vista collezione dei valori contenuti in questa mappa.
     */
    public HCollection values();

    /**
     * Restituisce una vista {@code HSet} delle mappature contenute in questa mappa.
     * Ogni elemento in questo set è una {@code HMap.HEntry}.
     * Il set è supportato dalla mappa, quindi le modifiche alla mappa si riflettono
     * sul set, e viceversa.
     * 
     * @return una vista set delle mappature contenute in questa mappa.
     */
    public HSet entrySet();

    /**
     * Confronta l'oggetto specificato con questa mappa per verificarne
     * l'uguaglianza.
     * Restituisce {@code true} se l'oggetto dato è anche una mappa e le due mappe
     * rappresentano le stesse mappature.
     * 
     * @param o l'oggetto da confrontare con questa mappa per l'uguaglianza.
     * @return {@code true} se l'oggetto specificato è uguale a questa mappa.
     */
    public boolean equals(Object o);

    /**
     * Restituisce il valore hash code per questa mappa.
     * L'hash code di una mappa è definito come la somma degli hash code di ogni
     * entry nella vista {@code entrySet()} della mappa.
     * 
     * @return il valore hash code per questa mappa.
     */
    public int hashCode();
}
