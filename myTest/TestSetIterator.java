package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import myAdapter.*;

import java.util.NoSuchElementException;

/**
 * <b>Summary</b>
 * <p>
 * Questa suite di test, o Test Case, valida il comportamento dell'iteratore
 * restituito dalla vista delle chiavi ({@code keySet()}) di una
 * {@link myAdapter.MapAdapter}.
 * L'obiettivo è testare l'implementazione dell'interfaccia
 * {@link myAdapter.HIterator}, assicurando che i metodi {@code hasNext()},
 * {@code next()} e {@code remove()} si comportino come specificato dalla
 * documentazione di J2SE 1.4.2.
 * I test coprono scenari con un set popolato e vuoto, la corretta
 * gestione delle eccezioni e la coerenza dello stato dell'iteratore e della
 * collezione sottostante.
 * <p>
 * <b>Test Case Design</b>
 * <p>
 * La motivazione di questo test case è garantire che l'iteratore della vista
 * delle chiavi sia robusto, pienamente conforme alle specifiche e sicuro.
 * Poiché il {@code KeySet} è una vista "backed" (supportata) dalla
 * {@code MapAdapter}, è cruciale verificare che le operazioni sull'iteratore,
 * in particolare {@code remove()}, si riflettano correttamente sia sul set
 * (la vista) che sulla mappa originale. I test sono stati progettati per
 * coprire i casi d'uso principali, i casi limite (set vuoto) e la gestione di
 * stati illegali (es. chiamare {@code remove()} prima di {@code next()}).
 */
public class TestSetIterator {

    private MapAdapter testMap = new MapAdapter();
    private HSet testKeySet = null;
    private HIterator testIterator = null;

    /**
     * Questo costruttore è vuoto e serve a soddisfare i requisiti
     * dello strumento Javadoc, evitando warning di documentazione.
     */
    public TestSetIterator() {
        // Costruttore intenzionalmente lasciato vuoto.
    }

    /**
     * Prepara l'ambiente di test prima di ogni esecuzione.
     * Inizializza e popola la mappa sulla cui vista delle chiavi (KeySet)
     * opererà l'iteratore.
     */
    @Before
    public void setUp() {
        // Inserimento di elementi nella mappa
        testMap.put(5, "noce");
        testMap.put(9, "mano");
        testMap.put(1, "sasso");
        testMap.put(0, "pippo");

        testKeySet = testMap.keySet();
        testIterator = testKeySet.iterator();
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code hasNext()}
     * dell'iteratore.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test è progettato per assicurare che {@code hasNext()} rifletta
     * accuratamente la presenza di ulteriori elementi senza alterare lo stato
     * dell'iteratore o della collezione. Vengono coperti tre scenari: un iteratore
     * con elementi rimanenti, un iteratore che ha raggiunto la fine e un iteratore
     * su un set vuoto.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che {@code hasNext()} su un iteratore appena creato da un set
     * popolato restituisca {@code true}.<br>
     * 2. Si esegue un ciclo {@code while(hasNext())} per consumare tutti gli
     * elementi.<br>
     * 3. Si verifica che una successiva chiamata a {@code hasNext()} restituisca
     * {@code false}.<br>
     * 4. Si crea un iteratore da un {@code KeySet} vuoto.<br>
     * 5. Si verifica che {@code hasNext()} su questo iteratore restituisca
     * {@code false}.<br>
     * 6. Si controlla che il set originale non sia stato modificato.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} con 4 elementi e un iteratore ({@code testIterator})
     * creato dal suo {@code keySet()}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La {@code MapAdapter} e l'{@code HSet} non devono essere stati modificati.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code hasNext()} deve restituire {@code true} all'inizio, {@code false} dopo
     * aver iterato tutti gli elementi e {@code false} su un iteratore di un set
     * vuoto. La dimensione e il contenuto del set originale devono rimanere
     * invariati.
     */
    @Test
    public void testHasNext() {
        // Mi assicuro che l'iteratore del KeySet abbia altri elementi
        assertTrue("Mi assicuro che ci siano altri elementi nel set", testIterator.hasNext());

        while (testIterator.hasNext())
            testIterator.next();
        // Verifico che non ci siano più elementi da scorrere nel set
        assertFalse("Controllo che non ci siano piu' elementi nell'iteratore di testKeySet", testIterator.hasNext());

        // Preparo una mappa temporanea vuota
        MapAdapter tmpMap = new MapAdapter();
        // Controllo se l'iteratore della vista di una mappa vuota ha elementi
        testIterator = tmpMap.keySet().iterator();
        // Il risultato dovrebbe essere falso
        assertFalse(testIterator.hasNext());

        // Controllo che il set di test contenga ancora tutti gli elementi di partenza
        assertEquals(4, testKeySet.size());
        assertTrue(testKeySet.contains(5) &&
                testKeySet.contains(9) &&
                testKeySet.contains(1) &&
                testKeySet.contains(0));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code next()} su un set popolato e del lancio
     * dell'eccezione {@code NoSuchElementException}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test ha il duplice scopo di controllare che {@code next()} restituisca
     * effettivamente gli elementi presenti nel set e che lanci una
     * {@link NoSuchElementException} quando l'iterazione è terminata.
     * Questo garantisce che l'iteratore non tenti di accedere a elementi
     * inesistenti.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si itera sul set usando {@code hasNext()} e {@code next()}.<br>
     * 2. Per ogni elemento restituito da {@code next()}, si verifica che sia
     * contenuto nel set originale.<br>
     * 3. Si conta il numero di iterazioni e si confronta con la dimensione del
     * set.<br>
     * 4. Dopo aver esaurito gli elementi, si invoca {@code next()} un'altra volta
     * per verificare che venga lanciata {@code NoSuchElementException}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} con 4 elementi e un iteratore ({@code testIterator})
     * creato dal suo {@code keySet()}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Il set sottostante non deve essere modificato. L'iteratore si trova alla
     * fine della collezione.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il test ha successo se tutti gli elementi vengono iterati correttamente e se
     * la chiamata finale a {@code next()} solleva {@code NoSuchElementException}.
     */
    @Test(expected = NoSuchElementException.class)
    public void testNextOnPopulatedSet() {
        int i = 0;
        // Verifico che gli elementi forniti dall'iteratore siano nel set
        for (; testIterator.hasNext(); i++) {
            Object nextElem = testIterator.next();
            assertTrue("Controllo che l'elemento " + (Integer) nextElem + " appartiene al set",
                    testKeySet.contains(nextElem));
        }

        // Controllo che il contatore i sia uguale alla dimensione del set
        assertEquals(i, testKeySet.size());
        testIterator.next(); // Questa chiamata deve sollevare l'eccezione
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code next()} sollevi {@code NoSuchElementException} se
     * chiamato su un iteratore di un set vuoto.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test è cruciale per garantire il corretto comportamento dell'iteratore
     * nel caso limite di un set vuoto. Deve essere impossibile ottenere un
     * elemento da un set vuoto, e il tentativo deve essere segnalato con
     * l'eccezione appropriata.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una nuova {@code MapAdapter} vuota.<br>
     * 2. Si ottiene l'iteratore dalla sua vista {@code keySet()}.<br>
     * 3. Si invoca {@code next()} su questo iteratore.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un iteratore creato da un {@code KeySet} vuoto.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set (vuoto) rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il test ha successo se la chiamata a {@code next()} solleva
     * {@link NoSuchElementException}.
     */
    @Test(expected = NoSuchElementException.class)
    public void testNextOnEmptySet() {
        // Creo una nuova mappa vuota
        testMap = new MapAdapter();
        // e il rispettivo iteratore
        testIterator = testMap.keySet().iterator();
        // Tento di usare il metodo next()
        testIterator.next();
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica della corretta rimozione di un elemento tramite il metodo
     * {@code remove()}
     * dell'iteratore.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test è fondamentale per validare la natura "backed" della vista
     * {@code KeySet}. L'obiettivo è assicurare che la chiamata a {@code remove()}
     * sull'iteratore non solo funzioni, ma modifichi correttamente sia il set che
     * la mappa sottostante.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si invoca {@code next()} per ottenere un elemento.<br>
     * 2. Si salva la dimensione attuale del set.<br>
     * 3. Si invoca {@code remove()} sull'iteratore.<br>
     * 4. Si verifica che l'elemento restituito da {@code next()} non sia più
     * presente nel set.<br>
     * 5. Si verifica che la dimensione del set sia diminuita di 1.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un iteratore posizionato su un set popolato.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * L'ultimo elemento restituito da {@code next()} viene rimosso dal set
     * sottostante e la sua dimensione è ridotta di uno.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * L'elemento rimosso non deve più essere contenuto nel set e la
     * dimensione del set deve essere {@code size - 1}.
     */
    @Test
    public void testRemoveAfterNext() {
        // Salvo l'elemento restituito da next()
        Object nextElem = testIterator.next();

        // Salvo la dimensione del testKeySet
        int size = testKeySet.size();

        // Verifico che l'iteratore elimini dal KeySet l'ultimo elemento ottenuto
        testIterator.remove();
        assertFalse("Controllo che l'elemento " + (Integer) nextElem + " non appartenga piu' al set",
                testKeySet.contains(nextElem));

        // Controllo che la dimensione del set si sia ridotta di 1
        assertEquals(size - 1, testKeySet.size());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code remove()} sollevi {@code IllegalStateException} se
     * chiamato prima di {@code next()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica la gestione di uno stato illegale dell'iteratore.
     * Secondo l'interfaccia di {@code Iterator}, {@code remove()} può essere
     * chiamato solo dopo una chiamata a {@code next()}. Questo test assicura che
     * l'implementazione rispetti tale vincolo, prevenendo rimozioni non valide.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Senza mai chiamare {@code next()}, si invoca immediatamente il metodo
     * {@code remove()}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un iteratore appena creato, su cui non è ancora stato chiamato
     * {@code next()}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set sottostante rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il test ha successo se viene sollevata una {@link IllegalStateException}.
     */
    @Test(expected = IllegalStateException.class)
    public void testRemove() {
        testIterator.remove();
    }
}