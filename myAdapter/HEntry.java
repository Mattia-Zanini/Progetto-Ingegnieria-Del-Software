package myAdapter;

/**
 * Rappresenta una coppia chiave-valore all'interno di una mappa.
 * <p>
 * L'unico modo per ottenere un riferimento a un oggetto HEntry è attraverso
 * l'iteratore della vista delle coppie chiave-valore restituita dal metodo
 * {@code entrySet()} di {@link HMap}.
 */
public interface HEntry {
    /**
     * Restituisce la chiave corrispondente a questa coppia.
     * 
     * @return la chiave associata a questa coppia chiave-valore
     */
    public Object getKey();

    /**
     * Restituisce il valore corrispondente a questa coppia.
     * <p>
     * Se la entry è stata rimossa dalla mappa sottostante (tramite l'operazione
     * remove dell'iteratore), i risultati di questa chiamata sono indefiniti.
     * 
     * @return il valore associato a questa coppia chiave-valore
     */
    public Object getValue();

    /**
     * Sostituisce il valore di questa entry con il valore specificato.
     * La modifica si riflette direttamente sulla mappa di supporto.
     * <p>
     * <b>Nota sul Comportamento Specifico:</b> In aderenza alla specifica
     * J2SE 1.4.2, che definisce "indefinito" il comportamento di questo metodo
     * se l'entry è stata rimossa dalla mappa, questa implementazione adotta il
     * seguente comportamento: se {@code setValue()} viene invocato su un'entry
     * la cui mappatura non è più presente nella mappa, la chiamata a
     * {@code put} sottostante reinserirà la coppia chiave-valore nella mappa.
     * Questo comportamento è verificato dalla suite di test.
     *
     * @param v il nuovo valore da memorizzare in questa entry.
     * @return il vecchio valore associato alla chiave, o {@code null} se la
     *         chiave non era presente prima di questa chiamata (caso di
     *         reinserimento).
     * @throws NullPointerException se il nuovo valore è {@code null}.
     */
    public Object setValue(Object v) throws NullPointerException;

    /**
     * Confronta l'oggetto specificato con questa coppia per verificarne
     * l'uguaglianza.
     * <p>
     * Restituisce true se l'oggetto specificato è anch'esso una coppia
     * chiave-valore e le due coppie rappresentano la stessa entry. Due coppie
     * rappresentano la stessa entry se hanno chiavi e valori uguali secondo il
     * metodo equals().
     * 
     * @param o l'oggetto da confrontare con questa coppia chiave-valore
     * @return {@code true} se l'oggetto specificato è uguale a questa coppia
     */
    public boolean equals(Object o);

    /**
     * Calcola e restituisce il codice hash per questa coppia chiave-valore.
     * 
     * @return il codice hash per questa coppia chiave-valore
     */
    public int hashCode();

}
