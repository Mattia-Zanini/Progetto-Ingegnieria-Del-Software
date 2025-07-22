package myAdapter;

import java.util.NoSuchElementException;

/**
 * Interfaccia che definisce un iteratore per attraversare gli elementi di una
 * collezione.
 * <p>
 * Un iteratore permette di accedere sequenzialmente agli elementi di una
 * collezione senza esporre la struttura interna della collezione stessa.
 * Fornisce inoltre la possibilità di rimuovere elementi durante l'iterazione.
 */
public interface HIterator {
    /**
     * Verifica se l'iterazione ha ulteriori elementi da percorrere.
     * <p>
     * Questo metodo restituisce {@code true} se una chiamata a {@code next()}
     * restituirebbe un elemento anziché lanciare un'eccezione.
     * 
     * @return {@code true} se l'iterazione ha ulteriori elementi, {@code false}
     *         altrimenti
     */
    public boolean hasNext();

    /**
     * Restituisce il prossimo elemento nell'iterazione.
     * <p>
     * Questo metodo avanza il cursore dell'iteratore e restituisce l'elemento
     * su cui il cursore si posiziona.
     * 
     * @return il prossimo elemento della collezione
     * @throws NoSuchElementException se l'iterazione non ha ulteriori elementi
     */
    public Object next() throws NoSuchElementException;

    /**
     * Rimuove dalla collezione l'ultimo elemento restituito dall'iteratore.
     * <p>
     * Questo metodo può essere chiamato solo una volta per ogni chiamata a
     * {@code next()}.
     * Il comportamento dell'iteratore non è definito se la collezione viene
     * modificata durante l'iterazione in modo diverso da questa chiamata.
     * 
     * @throws IllegalStateException se il metodo {@code next()} non è stato ancora
     *                               chiamato, oppure se il metodo {@code remove()}
     *                               è già stato chiamato dopo l'ultima chiamata a
     *                               {@code next()}
     */
    public void remove() throws IllegalStateException;

}
