package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import myAdapter.HCollection;
import myAdapter.HIterator;
import myAdapter.MapAdapter;

import java.util.NoSuchElementException;

/**
 * <b>Summary</b>
 * <p>
 * Questa suite di test, o Test Case, verifica il corretto funzionamento
 * dell'iteratore restituito dalla vista dei valori ({@code values()}) di
 * {@code MapAdapter}.
 * L'obiettivo è testare l'implementazione dell'interfaccia {@code HIterator}
 * prodotta da {@code ValueCollection}, assicurando che i metodi
 * {@code hasNext()}, {@code next()} e {@code remove()} si comportino come
 * specificato dalla documentazione di J2SE 1.4.2.
 * I test coprono scenari con collezioni popolate e vuote, la corretta
 * gestione delle eccezioni e la coerenza dello stato dell'iteratore durante
 * le operazioni.
 * <p>
 * <b>Test Case Design</b>
 * <p>
 * La motivazione di questo test case è garantire che l'iteratore della
 * collezione di valori sia robusto e pienamente conforme alle specifiche. Dato
 * che la {@code ValueCollection} è una vista "backed" dalla {@code MapAdapter},
 * è cruciale verificare che le operazioni sull'iteratore (in particolare
 * {@code remove()}) si riflettano correttamente sulla mappa sottostante.
 * I test sono stati progettati per coprire i casi d'uso principali: iterazione,
 * rimozione di elementi e gestione di stati illegali (es. {@code remove()}
 * chiamato senza {@code next()}).
 */
public class TestCollectionIterator {

    private MapAdapter testMap = new MapAdapter();
    private HCollection testCollection = null;
    private HIterator testIterator = null;

    /**
     * Questo costruttore è vuoto e serve a soddisfare i requisiti
     * dello strumento Javadoc, evitando warning di documentazione.
     */
    public TestCollectionIterator() {
        // Costruttore intenzionalmente lasciato vuoto.
    }

    /**
     * Prepara l'ambiente di test prima di ogni esecuzione.
     * Popola la mappa di base e inizializza l'iteratore sulla vista dei suoi
     * valori, garantendo una condizione di partenza nota e consistente per
     * ogni test.
     */
    @Before
    public void setUp() {
        testMap.put(5, "noce");
        testMap.put(9, "mano");
        testMap.put(1, "sasso");
        testMap.put(0, "pippo");

        testCollection = testMap.values();
        testIterator = testCollection.iterator();
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code hasNext()} e della sua
     * non interferenza con la collezione sottostante.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test è progettato per assicurare che il metodo {@code hasNext()}
     * rifletta accuratamente lo stato dell'iteratore senza causare effetti
     * collaterali sulla collezione. La verifica della non-modifica è cruciale per
     * garantire che {@code hasNext()} sia un'operazione di sola lettura.
     * Vengono coperti tre scenari: un iteratore con elementi rimanenti,
     * un iteratore che ha raggiunto la fine e un iteratore su una collezione vuota.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una copia di backup della mappa originale per un confronto
     * finale.<br>
     * 2. Si verifica che {@code hasNext()} su un iteratore appena creato da una
     * collezione popolata restituisca {@code true}.<br>
     * 3. Si esegue un ciclo {@code while(hasNext())} per consumare tutti gli
     * elementi dell'iteratore.<br>
     * 4. Si verifica che una successiva chiamata a {@code hasNext()} restituisca
     * {@code false}.<br>
     * 5. Si crea un iteratore da una collezione vuota e si verifica che
     * {@code hasNext()} restituisca {@code false}.<br>
     * 6. Si confronta la mappa originale con la sua copia di backup per assicurarsi
     * esplicitamente che non sia stata modificata dall'iterazione.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} con 4 elementi e un iteratore ({@code testIterator})
     * creato dalla sua vista {@code values()}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La {@code MapAdapter} e la sua vista {@code HCollection} non sono state
     * modificate in alcun modo.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code hasNext()} deve restituire {@code true} all'inizio, {@code false} dopo
     * aver iterato tutti gli elementi e {@code false} su un iteratore di una
     * collezione vuota. La mappa originale deve rimanere identica alla sua copia di
     * backup.
     */
    @Test
    public void testCollectionIteratorHasNext() {
        // Creo una copia di backup per verificare l'assenza di modifiche
        MapAdapter backupMap = new MapAdapter(testMap);

        // Verifico che l'iteratore della collezione abbia elementi da scorrere
        assertTrue("Mi aspetto che ci siano altri elementi nella collezione", testIterator.hasNext());

        // Consumo l'iteratore
        while (testIterator.hasNext())
            testIterator.next();

        // Ora controllo che non ci siano più elementi
        assertFalse("L'iteratore non dovrebbe avere più elementi da restituire", testIterator.hasNext());

        // Verifico il caso di una collezione vuota
        MapAdapter tmpMap = new MapAdapter();
        testIterator = tmpMap.values().iterator();
        assertFalse("L'iteratore di una collezione vuota non deve avere elementi", testIterator.hasNext());

        // Verifico esplicitamente che la collezione iniziale non sia stata alterata
        assertEquals("La mappa non deve essere stata modificata dall'iterazione", backupMap, testMap);
        assertEquals("La collezione non deve essere stata modificata dall'iterazione", backupMap.values(),
                testCollection);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code next()} su una collezione popolata e del lancio
     * dell'eccezione {@code NoSuchElementException}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test ha il duplice scopo di controllare che {@code next()} restituisca
     * effettivamente gli elementi presenti nella collezione e che, come da
     * interfaccia, lanci una {@code NoSuchElementException} quando l'iterazione è
     * terminata. Questo garantisce che l'iteratore non tenti di accedere a elementi
     * inesistenti.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si itera sulla collezione usando {@code hasNext()} e {@code next()}.<br>
     * 2. Per ogni elemento restituito da {@code next()}, si verifica che sia
     * contenuto
     * nella collezione originale.<br>
     * 3. Si conta il numero di iterazioni e si confronta con la dimensione della
     * collezione.<br>
     * 4. Dopo aver esaurito gli elementi, si invoca {@code next()} un'altra volta
     * per verificare che venga lanciata {@code NoSuchElementException}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} con 4 elementi e un iteratore ({@code testIterator})
     * creato dalla sua {@code values()} collection.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione sottostante non deve essere modificata. L'iteratore si trova
     * alla fine della collezione.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il test ha successo se tutti gli elementi vengono iterati correttamente e se
     * la chiamata finale a {@code next()} solleva {@code NoSuchElementException}.
     */
    @Test(expected = NoSuchElementException.class)
    public void testCollectionIteratorNext() {
        int i = 0;
        // Controllo che ogni elemento fornito
        // dall'iteratore sia contenuto nella collezione
        for (; testIterator.hasNext(); i++) {
            Object nextElem = testIterator.next();
            assertTrue(
                    "L'elemento " + (String) nextElem + " deve appartenere alla collezione",
                    testCollection.contains(nextElem));
        }
        // Il numero di iterazioni deve corrispondere alla dimensione della collezione
        assertEquals(i, testCollection.size());
        testIterator.next(); // Questa chiamata deve lanciare l'eccezione
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
     * della collezione. L'obiettivo è assicurare che la chiamata a {@code remove()}
     * sull'iteratore non solo funzioni, ma modifichi correttamente la collezione
     * sottostante. La verifica si basa sul controllo della presenza dell'elemento
     * rimosso e sulla diminuzione della dimensione della collezione.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si invoca {@code next()} per ottenere un elemento.<br>
     * 2. Si salva la dimensione attuale della collezione.<br>
     * 3. Si invoca {@code remove()} sull'iteratore.<br>
     * 4. Si verifica che l'elemento restituito da {@code next()} non sia più
     * presente
     * nella collezione.<br>
     * 5. Si verifica che la dimensione della collezione sia diminuita di 1.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un iteratore posizionato su una collezione popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * L'ultimo elemento restituito da {@code next()} viene rimosso dalla collezione
     * sottostante e la sua dimensione è ridotta di uno.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * L'elemento rimosso non deve più essere contenuto nella collezione e la
     * dimensione della collezione deve essere {@code size - 1}.
     */
    @Test
    public void testCollectionIteratorRemove() {
        // Salvo l'elemento restituito da next()
        Object nextElem = testIterator.next();

        // Salvo la dimensione della collezione prima della rimozione
        int size = testCollection.size();
        // Verifico che l'iteratore rimuova l'ultimo elemento restituito da next()
        testIterator.remove();
        assertFalse(
                "Controllo che l'elemento " +
                        (String) nextElem +
                        " non appartenga più alla collezione",
                testCollection.contains(nextElem));

        // Controllo che la dimensione della collezione sia diminuita di 1
        assertEquals(size - 1, testCollection.size());
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
     * 1. Si crea un iteratore da una collezione popolata.<br>
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
     * Lo stato della collezione sottostante rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il test ha successo se viene sollevata una {@code IllegalStateException}.
     */
    @Test(expected = IllegalStateException.class)
    public void testCollectionIteratorRemoveWithoutNext() {
        testIterator.remove();
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code remove()} sollevi {@code IllegalStateException} se
     * chiamato due volte consecutivamente.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo scenario valida un altro caso di stato illegale. L'interfaccia
     * dell'iteratore permette una sola chiamata a {@code remove()} per ogni
     * chiamata a {@code next()}. Questo test si assicura che tentare una seconda
     * rimozione illecita venga bloccato correttamente.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si invoca {@code next()} per posizionare l'iteratore su un elemento.<br>
     * 2. Si invoca {@code remove()} una prima volta (operazione lecita).<br>
     * 3. Si invoca {@code remove()} una seconda volta, senza un'altra chiamata a
     * {@code next()}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un iteratore su cui è stato appena chiamato {@code next()} e subito dopo
     * {@code remove()}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione riflette la prima (e unica) rimozione valida.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il test ha successo se la seconda chiamata a {@code remove()} solleva una
     * {@code IllegalStateException}.
     */
    @Test(expected = IllegalStateException.class)
    public void testCollectionIteratorRemoveCalledTwice() {
        testIterator.next(); // Avanza al primo elemento
        testIterator.remove(); // Prima rimozione, lecita
        testIterator.remove(); // Seconda rimozione, illecita: deve lanciare l'eccezione
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica la capacità dell'iteratore di svuotare un'intera collezione.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test combina l'iterazione e la rimozione in un unico ciclo per
     * simulare
     * un caso d'uso comune: filtrare o svuotare una collezione. L'obiettivo è
     * verificare che l'iteratore mantenga uno stato consistente anche con
     * rimozioni ripetute, fino a svuotare completamente la collezione sottostante.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si avvia un ciclo {@code while} che continua finché {@code hasNext()} è
     * {@code true}.<br>
     * 2. All'interno del ciclo, si chiama {@code next()} e subito dopo
     * {@code remove()}.<br>
     * 3. Al termine del ciclo, si verifica che la collezione sia vuota
     * ({@code isEmpty()} deve essere {@code true}), che la sua dimensione sia 0
     * e che {@code hasNext()} restituisca {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una collezione popolata con 4 elementi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione sottostante è vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La collezione deve risultare vuota, la sua dimensione deve essere 0 e
     * {@code hasNext()} deve restituire {@code false}.
     */
    @Test
    public void testCollectionIteratorRemoveAllElements() {
        assertTrue("La collezione non dovrebbe essere vuota all'inizio", testCollection.size() > 0);

        // Rimuove tutti gli elementi usando l'iteratore
        while (testIterator.hasNext()) {
            testIterator.next();
            testIterator.remove();
        }

        assertTrue("La collezione dovrebbe essere vuota dopo la rimozione di tutti gli elementi",
                testCollection.isEmpty());
        assertEquals("La dimensione della collezione dovrebbe essere 0", 0, testCollection.size());
        assertFalse("L'iteratore non dovrebbe avere più elementi", testIterator.hasNext());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code next()} sollevi {@code NoSuchElementException} se
     * chiamato su un iteratore di una collezione vuota.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test è cruciale per garantire il corretto comportamento dell'iteratore
     * nel caso limite di una collezione vuota. Deve essere impossibile ottenere un
     * elemento da una collezione vuota, e il tentativo deve essere segnalato con
     * l'eccezione appropriata, come da interfaccia.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una nuova {@code MapAdapter} vuota.<br>
     * 2. Si ottiene l'iteratore dalla sua vista {@code values()}.<br>
     * 3. Si invoca {@code next()} su questo iteratore.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un iteratore creato da una collezione vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato della collezione (vuota) rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il test ha successo se la chiamata a {@code next()} solleva
     * {@code NoSuchElementException}.
     */
    @Test(expected = NoSuchElementException.class)
    public void testCollectionIteratorNextOnEmptyCollection() {
        // Creo una mappa vuota
        testMap = new MapAdapter();
        // e il relativo iteratore dei valori
        testIterator = testMap.values().iterator();
        // Tento di chiamare il metodo next()
        testIterator.next();
    }
}