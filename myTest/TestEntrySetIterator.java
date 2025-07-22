package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import myAdapter.HEntry;
import myAdapter.HIterator;
import myAdapter.HSet;
import myAdapter.MapAdapter;

import java.util.NoSuchElementException;

/**
 * <b>Summary</b>
 * <p>
 * Questa suite di test, o Test Case, è dedicata alla validazione dell'iteratore
 * restituito dalla vista delle entry ({@code entrySet()}) di una
 * {@link myAdapter.MapAdapter}.
 * L'obiettivo è testare l'implementazione dell'interfaccia
 * {@link myAdapter.HIterator}, assicurando che i metodi {@code hasNext()},
 * {@code next()} e {@code remove()} si comportino come specificato dalla
 * documentazione di J2SE 1.4.2.
 * I test coprono scenari con un set popolato e vuoto, la corretta gestione
 * delle eccezioni e la coerenza dello stato dell'iteratore e della collezione
 * sottostante.
 * <p>
 * <b>Test Case Design</b>
 * <p>
 * La motivazione di questo test case è garantire che l'iteratore della vista
 * delle entry sia robusto, pienamente conforme alle specifiche e sicuro.
 * Poiché l'{@code EntrySet} è una vista "backed" (supportata) dalla
 * {@code MapAdapter}, è cruciale verificare che le operazioni sull'iteratore,
 * in particolare {@code remove()}, si riflettano correttamente sia sul set
 * (la vista) che sulla mappa originale. I test sono stati progettati per
 * coprire i casi d'uso principali, i casi limite (set vuoto) e la gestione
 * di stati illegali (es. chiamare {@code remove()} prima di {@code next()}).
 */
public class TestEntrySetIterator {
    private MapAdapter testMap = new MapAdapter();
    private HSet testEntrySet = null;
    private HIterator testIterator = null;

    /**
     * Questo costruttore è vuoto e serve a soddisfare i requisiti
     * dello strumento Javadoc, evitando warning di documentazione.
     */
    public TestEntrySetIterator() {
        // Costruttore intenzionalmente lasciato vuoto.
    }

    /**
     * Prepara l'ambiente di test prima di ogni singolo caso di prova.
     * Popola la mappa di riferimento e inizializza l'iteratore sulla sua vista
     * delle entry, garantendo uno stato di partenza noto e consistente.
     */
    @Before
    public void setUp() {
        // Popolo la mappa con dati di prova
        testMap.put(5, "noce");
        testMap.put(9, "mano");
        testMap.put(1, "sasso");
        testMap.put(0, "pippo");

        testEntrySet = testMap.entrySet();
        testIterator = testEntrySet.iterator();
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
     * 1. Si crea una copia di backup della mappa per un confronto successivo.<br>
     * 2. Si verifica che {@code hasNext()} su un iteratore appena creato da un set
     * popolato restituisca {@code true}.<br>
     * 3. Si esegue un ciclo {@code while(hasNext())} per consumare tutti gli
     * elementi.<br>
     * 4. Si verifica che una successiva chiamata a {@code hasNext()} restituisca
     * {@code false}.<br>
     * 5. Si crea un iteratore da un {@code EntrySet} vuoto e si verifica che
     * {@code hasNext()} su di esso restituisca {@code false}.<br>
     * 6. Si confronta lo stato della mappa e del set con la copia di backup per
     * assicurarsi esplicitamente che non siano stati modificati.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} con 4 elementi e un iteratore ({@code testIterator})
     * creato dal suo {@code entrySet()}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La {@code MapAdapter} e l'{@code HSet} non devono essere stati modificati.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code hasNext()} deve restituire {@code true} all'inizio, {@code false} dopo
     * aver iterato tutti gli elementi e {@code false} su un iteratore di un set
     * vuoto. Lo stato del set e della mappa originali deve rimanere invariato.
     */
    @Test
    public void testEntrySetIteratorHasNext() {
        // Creo una copia di backup per verificare l'assenza di modifiche
        MapAdapter backupMap = new MapAdapter(testMap);

        // Verifico che l'iteratore abbia elementi
        assertTrue("L'iteratore dovrebbe avere elementi", testIterator.hasNext());

        // Consumo tutti gli elementi
        while (testIterator.hasNext())
            testIterator.next();

        // Verifico che l'iteratore sia giunto alla fine
        assertFalse("L'iteratore non dovrebbe più avere elementi", testIterator.hasNext());

        // Verifico il comportamento con un iteratore di un set vuoto
        MapAdapter tmpMap = new MapAdapter();
        testIterator = tmpMap.entrySet().iterator();
        assertFalse("L'iteratore di un set vuoto non dovrebbe avere elementi", testIterator.hasNext());

        // Verifico esplicitamente che la mappa e il set non siano stati modificati
        assertEquals("La mappa non deve essere modificata dall'iterazione", backupMap, testMap);
        assertEquals("Il set di entry non deve essere modificato dall'iterazione", backupMap.entrySet(), testEntrySet);
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
     * effettivamente gli elementi presenti nel set e che, come dall'interfaccia,
     * lanci una {@link NoSuchElementException} quando l'iterazione è terminata.
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
     * creato dal suo {@code entrySet()}.
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
    public void testEntrySetIteratorNext() {
        int size = 0;
        for (; testIterator.hasNext(); size++) {
            HEntry nextElem = (HEntry) testIterator.next();
            assertTrue(testEntrySet.contains(nextElem));
        }

        assertEquals(size, testEntrySet.size());
        testIterator.next();
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
     * l'eccezione appropriata, come da interfaccia.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una nuova {@code MapAdapter} vuota.<br>
     * 2. Si ottiene l'iteratore dalla sua vista {@code entrySet()}.<br>
     * 3. Si invoca {@code next()} su questo iteratore.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un iteratore creato da un {@code EntrySet} vuoto.
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
    public void testEntrySetIteratorNextOnEmptyMap() {
        testMap = new MapAdapter();
        testIterator = testMap.entrySet().iterator();
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
     * {@code entrySet}. L'obiettivo è assicurare che la chiamata a {@code remove()}
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
    public void testEntrySetIteratorRemove() {
        HEntry nextEntry = (HEntry) testIterator.next();
        int size = testEntrySet.size();
        testIterator.remove();
        assertFalse(testEntrySet.contains(nextEntry));
        assertEquals(size - 1, testEntrySet.size());
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
     * 1. Si crea un iteratore da un set popolato.<br>
     * 2. Senza mai chiamare {@code next()}, si invoca immediatamente il metodo
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
    public void testEntrySetIteratorRemoveWhenNotCalledNext() {
        testIterator.remove();
    }
}