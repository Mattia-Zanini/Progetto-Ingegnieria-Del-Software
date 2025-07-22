package myAdapter;

/**
 * L'interfaccia radice nella gerarchia delle collezioni. Una collezione
 * rappresenta un gruppo di oggetti, noti come suoi elementi.
 * 
 */
public interface HCollection {
    /**
     * Restituisce il numero di elementi in questa collezione.
     * 
     * @return il numero di elementi in questa collezione
     */
    public int size();

    /**
     * Restituisce {@code true} se questa collezione non contiene elementi.
     * 
     * @return {@code true} se questa collezione non contiene elementi
     */
    public boolean isEmpty();

    /**
     * Restituisce {@code true} se questa collezione contiene l'elemento
     * specificato.
     * 
     * @param o elemento la cui presenza in questa collezione deve essere verificata
     * @return {@code true} se questa collezione contiene l'elemento specificato
     * @throws ClassCastException   se il tipo dell'elemento specificato è
     *                              incompatibile con questa collezione
     * @throws NullPointerException se l'elemento specificato è nullo e questa
     *                              collezione non ammette elementi nulli
     * 
     */
    public boolean contains(Object o);

    /**
     * Restituisce un iteratore sugli elementi di questa collezione. Non ci sono
     * garanzie riguardo all'ordine in cui gli elementi vengono restituiti.
     * 
     * @return un {@code HIterator} sugli elementi di questa collezione
     */
    public HIterator iterator();

    /**
     * Restituisce un array contenente tutti gli elementi di questa collezione.
     * <p>
     * L'array restituito non contiene nessun riferimento agli elementi della
     * collezione. Il chiamante è quindi libero di modificare l'array restituito.
     * 
     * @return un array contenente tutti gli elementi di questa collezione
     */
    public Object[] toArray();

    /**
     * Restituisce un array contenente tutti gli elementi di questa collezione;
     * Se tutti gli elementi della collezione possono stare all'interno dell'array
     * passato al metodo, allora la collezione viene restituita lì dentro.
     * Altrimenti, viene allocato un nuovo array con la dimensione di questa
     * collezione.
     * 
     * @param a l'array in cui memorizzare gli elementi della collezione, se è
     *          abbastanza grande; altrimenti, un nuovo array dello stesso tipo di
     *          runtime viene allocato per questo scopo.
     * @return un array contenente gli elementi della collezione
     * @throws ArrayStoreException  se il tipo di runtime dell'array specificato non
     *                              è un supertipo del tipo di runtime di ogni
     *                              elemento in questa collezione
     * @throws NullPointerException se l'array specificato è nullo
     */
    public Object[] toArray(Object[] a);

    /**
     * Assicura che questa collezione contenga l'elemento specificato.
     * Restituisce true se questa collezione è cambiata come risultato della
     * chiamata.
     * 
     * @param o elemento la cui presenza in questa collezione deve essere assicurata
     * @return true se questa collezione è cambiata come risultato della chiamata
     * @throws UnsupportedOperationException se l'operazione add non è supportata da
     *                                       questa collezione
     */
    public boolean add(Object o);

    /**
     * Rimuove una singola istanza dell'elemento specificato da questa collezione,
     * se presente.
     * 
     * @param o elemento da rimuovere da questa collezione, se presente
     * @return true se un elemento è stato rimosso come risultato di questa chiamata
     * @throws ClassCastException   se il tipo dell'elemento specificato è
     *                              incompatibile con questa collezione
     * 
     * @throws NullPointerException se l'elemento specificato è nullo e
     *                              questa collezione non ammette elementi
     *                              nulli
     */
    public boolean remove(Object o);

    /**
     * Restituisce {@code true} se questa collezione contiene tutti gli elementi
     * della collezione specificata.
     * 
     * @param c collezione da verificare per il contenimento in questa collezione
     * @return {@code true} se questa collezione contiene tutti gli elementi della
     *         collezione specificata
     * @throws ClassCastException   se i tipi di uno o più elementi nella collezione
     *                              specificata sono incompatibili con questa
     *                              collezione
     * 
     * @throws NullPointerException se la collezione specificata contiene uno o più
     *                              elementi nulli e questa collezione non ammette
     *                              elementi nulli , o se la collezione specificata
     *                              è nulla
     * @see #contains(Object)
     */
    public boolean containsAll(HCollection c);

    /**
     * Aggiunge tutti gli elementi della collezione specificata a questa collezione.
     * Il comportamento di questa operazione non è definito se la collezione
     * specificata viene modificata mentre l'operazione è in corso. (Ciò implica che
     * il comportamento di questa chiamata non è definito se la collezione
     * specificata è questa collezione, e questa collezione non è vuota).
     * 
     * @param c collezione contenente elementi da aggiungere a questa collezione
     * @return {@code true} se questa collezione è cambiata come risultato della
     *         chiamata
     * @throws UnsupportedOperationException se l'operazione addAll non è supportata
     *                                       da questa collezione
     * @see #add(Object)
     */
    public boolean addAll(HCollection c);

    /**
     * Rimuove tutti gli elementi di questa collezione che sono contenuti anche
     * nella collezione specificata.
     * Dopo il ritorno di questa chiamata, questa collezione non conterrà elementi
     * in comune con la collezione specificata.
     * 
     * @param c collezione contenente elementi da rimuovere da questa collezione
     * @return {@code true} se questa collezione è cambiata come risultato della
     *         chiamata
     * @throws ClassCastException   se i tipi di uno o più elementi in
     *                              questa collezione sono incompatibili
     *                              con la collezione specificata
     * 
     * @throws NullPointerException se questa collezione contiene uno o più
     *                              elementi nulli e la collezione
     *                              specificata non supporta la loro
     *                              rimozione , o se la
     *                              collezione specificata è nulla
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean removeAll(HCollection c);

    /**
     * Mantiene solo gli elementi in questa collezione che sono contenuti nella
     * collezione specificata.
     * In altre parole, rimuove da questa collezione tutti i suoi elementi che non
     * sono contenuti nella collezione specificata.
     * 
     * @param c collezione contenente elementi da mantenere in questa collezione
     * @return {@code true} se questa collezione è cambiata come risultato della
     *         chiamata
     * @throws ClassCastException   se i tipi di uno o più elementi in
     *                              questa collezione sono incompatibili
     *                              con la collezione specificata
     * 
     * @throws NullPointerException se questa collezione contiene uno o più
     *                              elementi nulli e la collezione
     *                              specificata non li ammette ,
     *                              o se la collezione specificata è nulla
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean retainAll(HCollection c);

    /**
     * Rimuove tutti gli elementi da questa collezione.
     * La collezione sarà vuota dopo il ritorno di questo metodo.
     */
    public void clear();

    // Confronto e hashing

    /**
     * Confronta l'oggetto specificato con la collezione passata al metodo
     * collezione per l'uguaglianza.
     * 
     * @param o oggetto da confrontare per l'uguaglianza con questa collezione
     * @return {@code true} se l'oggetto specificato è uguale a questa collezione
     */
    public boolean equals(Object o);

    /**
     * Restituisce il valore hash code per questa collezione.
     * Il valore dell'hash code è la somma degli hash code di tutti i suoi elementi
     * 
     * @return il valore hash code per questa collezione
     */
    public int hashCode();
}