package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;
import org.junit.Before;
import myAdapter.*;

/**
 * <b>Summary</b>
 * <p>
 * Suite di test completa per la classe {@link myAdapter.MapAdapter}.
 * Questa suite di test, o Test Case, esamina in modo esaustivo il funzionamento
 * di tutti i metodi pubblici della classe {@code MapAdapter}, la quale
 * implementa l'interfaccia {@link myAdapter.HMap}. I test coprono le
 * operazioni fondamentali (inserimento, rimozione, accesso), i metodi per
 * interrogare lo stato della mappa (size, empty) e la gestione delle viste
 * ({@code keySet}, {@code values}, {@code entrySet}).
 * <p>
 * <b>Test Case Design</b>
 * <p>
 * La motivazione di questo test case è garantire che {@code MapAdapter} sia
 * un'implementazione pienamente conforme e robusta dell'interfaccia
 * {@code HMap}, rispettando l'interfaccia definita da J2SE 1.4.2. Un punto
 * cruciale della progettazione dei test è la verifica del meccanismo di
 * "backing" delle viste: le modifiche apportate a una vista (es. una
 * rimozione dal {@code keySet}) devono riflettersi immediatamente sulla mappa
 * principale, e viceversa. Ogni test è progettato per essere unitario e
 * isolato, utilizzando una fixture pre-popolata creata dal metodo
 * {@code setUp()} per garantire condizioni di partenza note e consistenti.
 */
public class TestMapAdapter {
    private MapAdapter testMap;

    /**
     * Questo costruttore è vuoto e serve a soddisfare i requisiti
     * dello strumento Javadoc, evitando warning di documentazione.
     */
    public TestMapAdapter() {
        // Costruttore intenzionalmente lasciato vuoto.
    }

    /**
     * Inizializza una nuova istanza di {@code MapAdapter} prima dell'esecuzione
     * di ogni test. Questo metodo assicura che ogni test operi su una fixture
     * pulita e isolata, popolando la mappa con un set predefinito di coppie
     * chiave-valore per stabilire uno stato iniziale noto.
     */
    @Before
    public void setUp() {
        testMap = new MapAdapter();
        // Popolo la mappa con dati di test.
        testMap.put(5, "noce");
        testMap.put(9, "mano");
        testMap.put(1, "sasso");
        testMap.put(0, "pippo");
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del costruttore di copia {@code MapAdapter(HMap)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test ha lo scopo di validare che il costruttore di copia crei una nuova
     * mappa che sia una copia conforme all'originale (stesso contenuto e stessa
     * dimensione) ma che sia un'istanza di oggetto distinta e indipendente.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una nuova mappa {@code tmpMap} usando il costruttore di copia
     * con {@code testMap}.<br>
     * 2. Si usa {@code assertEquals} per verificare che le due mappe siano uguali
     * in base al contenuto.<br>
     * 3. Si usa {@code assertNotSame} per verificare che le due mappe non siano
     * la stessa istanza in memoria.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa originale {@code testMap} non viene modificata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La nuova mappa deve essere uguale all'originale secondo {@code equals()},
     * ma non deve essere la stessa istanza.
     */
    @Test
    public void testCopyConstructor() {
        MapAdapter tmpMap = new MapAdapter(testMap);

        // Le due mappe devono essere uguali in termini di contenuto.
        assertEquals("La mappa copiata deve essere uguale all'originale", testMap, tmpMap);

        // Le due mappe non devono essere lo stesso oggetto.
        assertNotSame("La mappa copiata non deve essere la stessa istanza dell'originale", testMap, tmpMap);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica l'indipendenza di una mappa creata con il costruttore di copia.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test è cruciale per garantire che la copia creata non sia una vista
     * "backed", ma una struttura dati completamente separata. Le modifiche
     * (aggiunte o rimozioni) sulla mappa originale non devono avere alcun effetto
     * sulla copia.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una copia {@code tmpMap} di {@code testMap}.<br>
     * 2. Si aggiunge una nuova coppia a {@code testMap}.<br>
     * 3. Si verifica che la dimensione e il contenuto di {@code tmpMap} non siano
     * cambiati.<br>
     * 4. Si rimuove una coppia da {@code testMap}.<br>
     * 5. Si verifica nuovamente che {@code tmpMap} non sia stata alterata e
     * contenga ancora l'elemento rimosso dall'originale.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa originale {@code testMap} viene modificata, ma la copia
     * {@code tmpMap} rimane nel suo stato iniziale.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La mappa copiata deve rimanere invariata nonostante le modifiche apportate
     * alla mappa originale.
     */
    @Test
    public void testCopyIsIndependent() {
        MapAdapter tmpMap = new MapAdapter(testMap);
        assertEquals("La dimensione iniziale della copia deve essere 4", 4, tmpMap.size());

        // Modifico la mappa originale.
        testMap.put(100, "new value");

        // Verifico che la copia non sia stata modificata.
        assertEquals("La dimensione della copia non deve cambiare dopo un'aggiunta all'originale", 4, tmpMap.size());
        assertFalse("La copia non deve contenere il nuovo elemento aggiunto all'originale", tmpMap.containsKey(100));

        // Modifico di nuovo la mappa originale.
        testMap.remove(5);

        // Verifico che la copia non sia stata modificata.
        assertTrue("La copia deve ancora contenere l'elemento rimosso dall'originale", tmpMap.containsKey(5));
        assertEquals("Il valore nella copia deve rimanere invariato", "noce", tmpMap.get(5));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code size()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test è progettato per assicurare che il metodo {@code size()} rifletta
     * accuratamente il numero di mappature presenti nella mappa in diverse fasi
     * del suo ciclo di vita: dopo l'inizializzazione, dopo una rimozione e dopo
     * lo svuotamento completo.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che la dimensione iniziale sia 4.<br>
     * 2. Si rimuove un elemento dalla mappa.<br>
     * 3. Si verifica che la dimensione sia diminuita a 3.<br>
     * 4. Si svuota la mappa con {@code clear()}.<br>
     * 5. Si verifica che la dimensione finale sia 0.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} inizializzata con 4 elementi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code size()} deve restituire 4, 3 e 0 nei rispettivi passaggi
     * del test.
     */
    @Test
    public void testMapSize() {
        // Verifica della dimensione iniziale.
        assertEquals("La dimensione iniziale dovrebbe essere 4", 4, testMap.size());

        // Rimuove un elemento e controlla nuovamente la dimensione.
        testMap.remove(5);
        assertEquals("La dimensione dopo la rimozione dovrebbe essere 3", 3, testMap.size());

        // Svuota la mappa e verifica la dimensione finale.
        testMap.clear();
        assertEquals("La dimensione dopo lo svuotamento dovrebbe essere 0", 0, testMap.size());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code isEmpty()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che {@code isEmpty()} restituisca il valore booleano
     * corretto a seconda che la mappa contenga o meno elementi, validando il suo
     * stato dopo l'inizializzazione e dopo lo svuotamento.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che {@code isEmpty()} restituisca {@code false} su una mappa
     * appena popolata.<br>
     * 2. Si svuota la mappa con {@code clear()}.<br>
     * 3. Si verifica che {@code isEmpty()} restituisca {@code true} sulla mappa
     * vuota.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} inizializzata con 4 elementi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code isEmpty()} deve restituire {@code false} prima di
     * {@code clear()} e {@code true} dopo.
     */
    @Test
    public void testIsEmpty() {
        // Verifica su una mappa non vuota.
        assertFalse("La mappa popolata non dovrebbe essere vuota", testMap.isEmpty());
        assertEquals("La dimensione dovrebbe essere 4", 4, testMap.size());

        // Svuota la mappa.
        testMap.clear();

        // Verifica su una mappa vuota.
        assertTrue("La mappa dopo clear() dovrebbe essere vuota", testMap.isEmpty());
        assertEquals("La dimensione dopo clear() dovrebbe essere 0", 0, testMap.size());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code containsKey(Object)} sollevi {@code NullPointerException}
     * con una chiave {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che l'implementazione rispetti l'interfaccia che vieta
     * chiavi nulle, lanciando l'eccezione appropriata per prevenire stati
     * inconsistenti.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testMap.containsKey(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsKeyWithNull() {
        testMap.containsKey(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica il corretto funzionamento di {@code containsKey(Object)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test valida la capacità del metodo di identificare correttamente la
     * presenza o l'assenza di una chiave, coprendo i casi di chiave presente,
     * chiave assente e ricerca in una mappa vuota.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si cerca una chiave esistente (0) e si verifica che il risultato sia
     * {@code true}.<br>
     * 2. Si cerca una chiave non esistente (10) e si verifica che il risultato
     * sia {@code false}.<br>
     * 3. Si svuota la mappa e si cerca una delle chiavi originali, verificando
     * che il risultato sia {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code true} per la chiave presente, e
     * {@code false} negli altri due casi.
     */
    @Test
    public void testContainsKey() {
        // Caso: chiave presente.
        assertTrue("Dovrebbe trovare una chiave esistente", testMap.containsKey(0));

        // Caso: chiave non presente.
        assertFalse("Non dovrebbe trovare una chiave non esistente", testMap.containsKey(10));

        // Caso: mappa svuotata.
        testMap.clear();
        assertFalse("Non dovrebbe trovare alcuna chiave in una mappa vuota", testMap.containsKey(0));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code containsValue(Object)} sollevi
     * {@code NullPointerException} con un valore {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che l'implementazione rispetti l'interfaccia che vieta
     * valori nulli, lanciando un'eccezione per segnalare un uso non valido del
     * metodo.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testMap.containsValue(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsValueWithNull() {
        testMap.containsValue(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica il corretto funzionamento di {@code containsValue(Object)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test valida la capacità del metodo di cercare un valore all'interno
     * della mappa. Copre i casi di valore presente, valore assente e ricerca in
     * una mappa vuota.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si cerca un valore esistente ("sasso") e si verifica che il risultato
     * sia {@code true}.<br>
     * 2. Si cerca un valore non esistente e si verifica che il risultato sia
     * {@code false}.<br>
     * 3. Si svuota la mappa e si cerca uno dei valori originali, verificando che
     * il risultato sia {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code true} per il valore presente, e
     * {@code false} negli altri due casi.
     */
    @Test
    public void testContainsValue() {
        // Caso: valore presente.
        assertTrue("Dovrebbe trovare un valore esistente", testMap.containsValue("sasso"));

        // Caso: valore non presente.
        assertFalse("Non dovrebbe trovare un valore non esistente", testMap.containsValue("non_presente"));

        // Caso: mappa svuotata.
        testMap.clear();
        assertFalse("Non dovrebbe trovare alcun valore in una mappa vuota", testMap.containsValue("sasso"));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code get(Object)} sollevi {@code NullPointerException} con
     * una chiave {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test garantisce che, in linea con il divieto di chiavi nulle, qualsiasi
     * tentativo di accedere a una mappatura tramite una chiave {@code null} venga
     * bloccato con un'eccezione.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testMap.get(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testGetWithNull() {
        testMap.get(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica il corretto funzionamento del metodo {@code get(Object)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test valida la funzione principale del metodo {@code get}, ovvero
     * recuperare il valore associato a una chiave. Vengono testati tre scenari:
     * recupero di un valore esistente, recupero dopo un aggiornamento e tentativo
     * di recupero di una chiave non esistente.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si recupera il valore per una chiave esistente (0) e si verifica che sia
     * quello corretto ("pippo").<br>
     * 2. Si aggiorna il valore per la chiave 0 usando {@code put}.<br>
     * 3. Si invoca di nuovo {@code get(0)} e si verifica che restituisca il nuovo
     * valore ("fragile").<br>
     * 4. Si invoca {@code get} con una chiave non esistente e si verifica che il
     * risultato sia {@code null}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa contiene un valore aggiornato per la chiave 0.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code get} deve restituire il valore corretto, il valore
     * aggiornato e {@code null} nei rispettivi passaggi.
     */
    @Test
    public void testGet() {
        // Recupero di un valore esistente.
        assertEquals("Dovrebbe restituire il valore corretto per la chiave 0", "pippo", testMap.get(0));

        // Recupero del valore dopo un aggiornamento.
        testMap.put(0, "fragile");
        assertEquals("Dovrebbe restituire il nuovo valore dopo l'aggiornamento", "fragile", testMap.get(0));

        // Recupero tramite una chiave non esistente.
        assertEquals("Dovrebbe restituire null per una chiave non esistente", null, testMap.get("chiave_inesistente"));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica il comportamento del metodo {@code put(Object, Object)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test valida i due scenari principali del metodo {@code put}:
     * l'inserimento di una nuova coppia chiave-valore e l'aggiornamento di una
     * chiave esistente. L'obiettivo è verificare che il valore di ritorno sia
     * conforme all'interfaccia ({@code null} per un nuovo inserimento, il vecchio
     * valore per un aggiornamento).
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si inserisce una nuova coppia (chiave 67) e si verifica che
     * {@code put} restituisca {@code null}.<br>
     * 2. Si aggiorna il valore per una chiave esistente (0) e si verifica che
     * {@code put} restituisca il valore precedente ("pippo").<br>
     * 3. Si verifica che il valore associato alla chiave 0 sia stato
     * effettivamente aggiornato.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa contiene una nuova coppia (67, "pippo") e un valore aggiornato
     * per la chiave 0.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code put} deve restituire {@code null} per il nuovo
     * inserimento e il vecchio valore per l'aggiornamento.
     */
    @Test
    public void testPut() {
        // Verifico che la chiave 67 non esista prima dell'inserimento.
        assertFalse("La chiave non dovrebbe esistere prima del put", testMap.containsKey(67));

        // Inserisco una nuova coppia e verifico che il valore di ritorno sia null.
        assertEquals("Put di una nuova chiave deve restituire null", null, testMap.put(67, "pippo"));

        // Verifico che la mappa contenga già la chiave 0 con il valore "pippo".
        assertTrue("La chiave 0 dovrebbe esistere", testMap.containsKey(0));
        assertEquals("Il valore per la chiave 0 dovrebbe essere 'pippo'", "pippo", testMap.get(0));

        // Aggiorno la chiave 0 e verifico che put restituisca il valore precedente.
        assertEquals("Put di una chiave esistente deve restituire il vecchio valore", "pippo", testMap.put(0, "devil"));

        // Verifico che il valore associato alla chiave 0
        // sia stato correttamente aggiornato.
        assertEquals("Il nuovo valore per la chiave 0 dovrebbe essere 'devil'", "devil", testMap.get(0));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code put(Object, Object)} lanci {@code NullPointerException}
     * con un valore {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che l'implementazione non permetta l'inserimento di
     * valori nulli, come da interfaccia.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca {@code testMap.put(66, null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Deve essere sollevata una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testPutWithNullValue() {
        testMap.put(66, null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code put(Object, Object)} lanci {@code NullPointerException}
     * con una chiave {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che l'implementazione non permetta l'inserimento di
     * chiavi nulle, come da interfaccia.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca {@code testMap.put(null, "settordici")}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Deve essere sollevata una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testPutWithNullKey() {
        testMap.put(null, "settordici");
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code put(Object, Object)} lanci {@code NullPointerException}
     * con chiave e valore {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test copre il caso limite in cui sia la chiave che il valore sono
     * nulli, assicurando che la validazione dell'input sia robusta.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca {@code testMap.put(null, null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Deve essere sollevata una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testPutWithNullKeyAndValue() {
        testMap.put(null, null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code putAll(HMap)} lanci {@code NullPointerException} con una
     * mappa {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che il metodo gestisca correttamente un argomento nullo,
     * prevenendo errori e garantendo la robustezza del metodo.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testMap.putAll(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Deve essere sollevata una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testPutAllWithNullMap() {
        testMap.putAll(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica di {@code putAll(HMap)} con una mappa che contiene sia chiavi in
     * comune che chiavi nuove.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test è progettato per validare lo scenario misto di {@code putAll}, in
     * cui la mappa
     * argomento contiene alcune chiavi già presenti nella mappa di destinazione
     * (che verranno aggiornate)
     * e alcune chiavi nuove (che verranno aggiunte). L'obiettivo è assicurare che
     * il metodo gestisca
     * correttamente sia l'aggiornamento dei valori esistenti sia l'inserimento di
     * nuove mappature,
     * e che la dimensione finale della mappa rifletta accuratamente solo l'aggiunta
     * degli elementi nuovi.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una mappa {@code mapToAdd} contenente una chiave in comune con
     * {@code testMap} (chiave 1) e una chiave nuova (chiave 3).<br>
     * 2. Si invoca {@code testMap.putAll(mapToAdd)}.<br>
     * 3. Si verifica che la dimensione finale di {@code testMap} sia aumentata solo
     * di 1 (da 4 a 5), poiché una chiave è stata aggiornata e una è stata
     * aggiunta.<br>
     * 4. Si verifica che il valore associato alla chiave in comune (1) sia stato
     * sovrascritto con il nuovo valore ("I").<br>
     * 5. Si verifica che la nuova mappatura (chiave 3) sia stata correttamente
     * aggiunta alla mappa.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} ({@code testMap}) inizializzata con 4 elementi. Una
     * seconda {@code MapAdapter} ({@code mapToAdd})
     * contenente 2 elementi, di cui uno con una chiave in comune con
     * {@code testMap} e uno con una chiave nuova.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * {@code testMap} contiene le mappature originali, il valore per la chiave in
     * comune è stato aggiornato e la nuova mappatura è stata aggiunta. La sua
     * dimensione è 5.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La dimensione della mappa di destinazione deve essere 5. Il valore per la
     * chiave 1 deve essere "I". La mappa deve contenere la chiave 3 con il valore
     * "III".
     */
    @Test
    public void testPutAllWithCommonAndUncommonKeys() {
        MapAdapter mapToAdd = new MapAdapter();
        mapToAdd.put(1, "I"); // Chiave in comune, valore diverso
        mapToAdd.put(3, "III"); // Chiave nuova

        testMap.putAll(mapToAdd);

        // La dimensione deve aumentare solo di 1 (da 4 a 5)
        assertEquals(
                "La dimensione dovrebbe essere 5 dopo l'aggiunta di una chiave nuova e l'aggiornamento di una esistente",
                5, testMap.size());

        // Verifico che il valore per la chiave in comune (1) sia stato aggiornato
        assertEquals("Il valore della chiave in comune dovrebbe essere aggiornato", "I", testMap.get(1));

        // Verifico che la nuova chiave (3) sia stata aggiunta correttamente
        assertTrue("La mappa dovrebbe contenere la nuova chiave", testMap.containsKey(3));
        assertEquals("Il valore della chiave nuova dovrebbe essere corretto", "III", testMap.get(3));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica di {@code putAll(HMap)} con chiavi in comune.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test valida lo scenario in cui {@code putAll} deve sia aggiungere
     * nuove mappature sia aggiornare quelle esistenti per le chiavi in comune.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una mappa {@code mapToAdd} con una chiave nuova (2) e una in
     * comune (9) con un valore diverso.<br>
     * 2. Si invoca {@code testMap.putAll(mapToAdd)}.<br>
     * 3. Si verifica che la dimensione sia aumentata solo di 1.<br>
     * 4. Si verifica che il valore per la chiave in comune (9) sia stato
     * aggiornato.<br>
     * 5. Si verifica che la nuova chiave (2) sia stata aggiunta.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Due mappe popolate con una chiave in comune.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * {@code testMap} contiene le nuove mappature e i valori aggiornati.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * I valori per le chiavi comuni devono essere sovrascritti e le nuove
     * coppie inserite.
     */
    @Test
    public void testPutAllWithCommonKeys() {
        MapAdapter mapToAdd = new MapAdapter();
        mapToAdd.put(2, "pollo"); // Chiave nuova
        mapToAdd.put(9, "patate"); // Chiave in comune, valore diverso

        testMap.putAll(mapToAdd);

        // La dimensione deve aumentare solo di 1 (per via della nuova chiave).
        assertEquals("La dimensione dovrebbe essere 5", 5, testMap.size());
        // Il valore per la chiave 9 deve risultare aggiornato.
        assertEquals("Il valore della chiave in comune dovrebbe essere aggiornato", "patate", testMap.get(9));
        // La nuova chiave deve essere presente.
        assertTrue("La nuova chiave dovrebbe essere presente", testMap.containsKey(1));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code putAll(HMap)} sovrascriva correttamente i valori
     * esistenti.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test si concentra sul comportamento di sovrascrittura di
     * {@code putAll} quando la mappa argomento contiene solo chiavi già presenti
     * nella mappa di destinazione. La dimensione non deve cambiare, ma i valori sì.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una mappa {@code tmpMap} con un sottoinsieme delle chiavi di
     * {@code testMap} ma con valori diversi.<br>
     * 2. Si invoca {@code testMap.putAll(tmpMap)}.<br>
     * 3. Si verifica che la dimensione di {@code testMap} non sia cambiata.<br>
     * 4. Si verifica che i valori per le chiavi comuni siano stati aggiornati in
     * {@code testMap}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Due mappe popolate che condividono alcune chiavi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * {@code testMap} ha la stessa dimensione ma valori aggiornati per le chiavi
     * comuni.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La dimensione della mappa di destinazione deve rimanere invariata, mentre
     * i valori per le chiavi condivise devono essere sovrascritti.
     */
    @Test
    public void testPutAllOverwritesValues() {
        // Creo una nuova mappa e la popolo con valori diversi
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put(5, 0);
        tmpMap.put(1, 0);
        tmpMap.put(0, 0);

        // Controllo che la dimensione iniziale di testMap sia 4
        assertTrue(testMap.size() == 4);

        // Chiamo il metodo putAll.
        testMap.putAll(tmpMap);

        // Controllo che la dimensione di testMap sia ancora 4
        assertTrue(testMap.size() == 4); // quindi la dimensione non è cambiata

        // Controllo che testMap contenga gli elementi aggiornati da tmpMap
        assertTrue(testMap.containsKey(5) && testMap.get(5).equals(0));
        assertTrue(testMap.containsKey(1) && testMap.get(1).equals(0));
        assertTrue(testMap.containsKey(0) && testMap.get(0).equals(0));

        // Controllo che i valori originali per le chiavi
        // 0, 5 e 1 siano stati sovrascritti
        assertNotEquals("pippo", testMap.get(0));
        assertNotEquals("noce", testMap.get(5));
        assertNotEquals("sasso", testMap.get(1));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica {@code putAll(HMap)} con una mappa identica.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che l'aggiunta di una mappa a una sua copia identica
     * non produca cambiamenti di stato, comportandosi come un'operazione nulla
     * in termini di contenuto.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una mappa {@code tmpMap} identica a {@code testMap}.<br>
     * 2. Si invoca {@code testMap.putAll(tmpMap)}.<br>
     * 3. Si verifica che la dimensione e il contenuto di {@code testMap} siano
     * rimasti invariati.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Due mappe identiche.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa {@code testMap} rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La mappa di destinazione non deve subire alcuna modifica.
     */
    @Test
    public void testPutAllWithIdenticalMap() {
        // Creo una nuova mappa identica a testMap.
        MapAdapter tmpMap = new MapAdapter(testMap);

        // Chiamo il metodo putAll.
        testMap.putAll(tmpMap);

        // Controllo che la dimensione di testMap sia rimasta 4
        assertTrue(testMap.size() == 4); // quindi la dimensione non è cambiata

        // Controllo che testMap contenga ancora tutti gli elementi originali.
        assertEquals("pippo", testMap.get(0));
        assertEquals("noce", testMap.get(5));
        assertEquals("sasso", testMap.get(1));
        assertEquals("mano", testMap.get(9));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che l'invocazione di {@code putAll(this)} sia sicura.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test copre il caso limite in cui una mappa viene aggiunta a se
     * stessa. L'operazione non deve causare eccezioni (es.
     * {@code ConcurrentModificationException}) né alterare lo stato della mappa.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si salva lo stato originale della mappa.<br>
     * 2. Si invoca {@code testMap.putAll(testMap)}.<br>
     * 3. Si verifica che la dimensione e il contenuto della mappa siano rimasti
     * identici allo stato originale.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Lo stato della mappa non deve cambiare dopo l'invocazione di
     * {@code putAll(this)}.
     */
    @Test
    public void testPutAllWithSelf() {
        MapAdapter originalState = new MapAdapter(testMap);
        int originalSize = testMap.size();

        testMap.putAll(testMap);

        assertEquals("La dimensione non deve cambiare dopo putAll(this)", originalSize, testMap.size());
        assertEquals("Il contenuto della mappa non deve cambiare dopo putAll(this)", originalState, testMap);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code remove(Object)} sollevi {@code NullPointerException}
     * con una chiave {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test garantisce che, in coerenza con il divieto di chiavi nulle, un
     * tentativo di rimozione tramite chiave {@code null} venga bloccato.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testMap.remove(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveWithNull() {
        testMap.remove(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica il corretto funzionamento del metodo {@code remove(Object)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test valida i due scenari di {@code remove}: la rimozione di una
     * chiave esistente e il tentativo di rimozione di una chiave non esistente.
     * L'obiettivo è verificare che il valore restituito sia corretto (il valore
     * rimosso o {@code null}) e che la dimensione della mappa venga aggiornata di
     * conseguenza.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si rimuove una chiave esistente (1) e si verifica che il valore
     * restituito sia quello corretto ("sasso").<br>
     * 2. Si verifica che la dimensione della mappa sia diminuita.<br>
     * 3. Si tenta di rimuovere una chiave non esistente (100) e si verifica che
     * il valore restituito sia {@code null}.<br>
     * 4. Si verifica che la dimensione della mappa non sia cambiata.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa non contiene più la coppia con chiave 1.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code remove} deve restituire il valore corretto per la chiave
     * esistente e {@code null} per quella non esistente. La dimensione della
     * mappa deve diminuire solo nel primo caso.
     */
    @Test
    public void testRemove() {
        // Rimuovo una chiave esistente e verifico il valore restituito.
        assertEquals("La rimozione di una chiave esistente dovrebbe restituire il suo valore", "sasso",
                testMap.remove(1));
        // Verifico che la dimensione sia diminuita.
        assertEquals("La dimensione dovrebbe essere 3 dopo la rimozione", 3, testMap.size());

        // Tento di rimuovere una chiave non esistente.
        assertEquals("La rimozione di una chiave non esistente dovrebbe restituire null", null, testMap.remove(100));
        // Verifico che la dimensione non sia cambiata.
        assertEquals("La dimensione non dovrebbe cambiare se la chiave non esiste", 3, testMap.size());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code clear()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test valida che {@code clear()} svuoti una mappa popolata e che
     * un'invocazione successiva su una mappa già vuota non causi errori e la
     * lasci vuota.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si invoca {@code clear()} su una mappa popolata.<br>
     * 2. Si verifica che la dimensione sia 0 e che la mappa sia vuota.<br>
     * 3. Si invoca nuovamente {@code clear()}.<br>
     * 4. Si verifica che la mappa rimanga vuota e con dimensione 0.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La mappa deve essere vuota e avere dimensione 0 dopo la prima chiamata a
     * {@code clear()}.
     */
    @Test
    public void testClear() {
        // Controllo che la mappa non sia vuota inizialmente.
        assertTrue(testMap.size() > 0);
        // Svuoto la mappa.
        testMap.clear();
        // Controllo che la mappa abbia dimensione 0 e sia priva di mappature.
        assertTrue(testMap.size() == 0);
        // Controllo che una chiamata a clear() su una mappa già vuota non alteri le sue
        // dimensioni.
        testMap.clear();
        assertTrue(testMap.isEmpty() && testMap.size() == 0);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code equals(Object)} tra mappe uguali e diverse.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test valida che il metodo {@code equals} funzioni come da interfaccia: due
     * mappe sono uguali se e solo se i loro {@code entrySet} sono uguali. Vengono
     * confrontate due mappe identiche e due mappe con contenuto diverso.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una mappa {@code tmpMap} identica a {@code testMap}.<br>
     * 2. Si verifica che le due mappe risultino uguali.<br>
     * 3. Si modifica {@code tmpMap} aggiungendo un elemento.<br>
     * 4. Si verifica che ora le due mappe non risultino più uguali.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * {@code testMap} rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code equals} deve restituire {@code true} per mappe con le stesse
     * mappature e {@code false} altrimenti.
     */
    @Test
    public void testEqualsForEqualMaps() {
        // Creo una mappa di supporto per il confronto.
        MapAdapter tmpMap = new MapAdapter(testMap);

        // Controllo che entrambe siano istanze di HMap.
        assertTrue(tmpMap instanceof HMap && testMap instanceof HMap);
        // Controllo che le due mappe abbiano lo stesso entrySet.
        assertEquals(tmpMap.entrySet(), testMap.entrySet());
        // Controllo che le due mappe risultino uguali.
        assertEquals(testMap, tmpMap);

        // Modifico la seconda mappa.
        tmpMap.put(3, "florida");

        // Controllo che le mappe non siano più uguali, dato che l'entrySet è diverso.
        assertNotEquals(tmpMap.entrySet(), testMap.entrySet());
        assertNotEquals(testMap, tmpMap);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code equals(Object)} con un oggetto di tipo diverso.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che {@code equals} restituisca sempre {@code false}
     * quando l'oggetto di confronto non è un'istanza di {@code HMap}, come
     * previsto dall'interfaccia di {@code equals}.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si confronta la mappa con il suo {@code entrySet} e si verifica che il
     * risultato sia {@code false}.<br>
     * 2. Si confronta la mappa con un oggetto non-collezione (un
     * {@code Integer}) e si verifica che il risultato sia {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code equals} deve restituire {@code false} in entrambi i casi.
     */
    @Test
    public void testEqualsForDifferentObjects() {
        // Creo un oggetto di tipo diverso (HSet).
        HSet set = testMap.entrySet();
        // Controllo l'uguaglianza con un oggetto di tipo diverso, mi aspetto false.
        assertNotEquals(testMap, set);

        // Controllo che la mappa non sia uguale a un intero.
        assertFalse(testMap.equals(5));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica della proprietà riflessiva di {@code equals(Object)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Un test di base per garantire che un'istanza di mappa sia sempre uguale a
     * se stessa ({@code x.equals(x)} deve restituire {@code true}), sia quando è
     * popolata che quando è vuota.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che {@code testMap.equals(testMap)} sia {@code true}.<br>
     * 2. Si svuota la mappa.<br>
     * 3. Si verifica nuovamente che {@code testMap.equals(testMap)} sia
     * {@code true}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Entrambe le chiamate a {@code equals} devono restituire {@code true}.
     */
    @Test
    public void testEqualsWithSelf() {
        // Test su mappa popolata
        assertTrue("Una mappa deve essere uguale a se stessa", testMap.equals(testMap));

        testMap.clear();
        assertTrue("Una mappa vuota deve essere uguale a se stessa", testMap.equals(testMap));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code hashCode()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che l'implementazione di {@code hashCode} rispetti
     * l'interfaccia generale: se due oggetti sono uguali secondo {@code equals},
     * allora devono avere lo stesso {@code hashCode}. Viene anche verificato che
     * l'hashCode di una mappa sia la somma degli hashCode delle sue entry.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una mappa {@code tmpMap} identica a {@code testMap}.<br>
     * 2. Si verifica che {@code testMap.hashCode()} sia uguale a
     * {@code testMap.entrySet().hashCode()}.<br>
     * 3. Si verifica che {@code testMap} e {@code tmpMap} siano uguali e che i
     * loro hashCode siano identici.<br>
     * 4. Si svuota {@code testMap} e si verifica che il suo hashCode sia 0.<br>
     * 5. Si verifica che l'hashCode della mappa svuotata sia diverso da quello
     * della mappa piena.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * {@code testMap} risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Due mappe uguali devono avere lo stesso hashCode. L'hashCode di una mappa
     * vuota deve essere 0.
     */
    @Test
    public void testHashCode() {
        // Creo una seconda mappa identica per il confronto.
        MapAdapter tmpMap = new MapAdapter(testMap);
        // Verifico che l'hashCode della mappa sia uguale a quello del suo entrySet.
        HSet tmpSet = testMap.entrySet();
        assertEquals(tmpSet.hashCode(), testMap.hashCode());

        // Controllo che le due mappe identiche siano uguali.
        assertEquals(testMap, tmpMap);
        // Controllo che l'hashCode delle due mappe uguali sia lo stesso.
        assertEquals(testMap.hashCode(), tmpMap.hashCode());

        testMap.clear();
        // L'hashCode di una mappa vuota deve essere 0.
        assertTrue(testMap.hashCode() == 0);

        // Controllo che l'hashCode della mappa
        // svuotata sia diverso da quello della mappa piena.
        assertFalse(testMap.hashCode() == tmpMap.hashCode());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che il metodo {@code keySet()} restituisca una vista corretta.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che l'oggetto restituito da {@code keySet()} sia
     * un'istanza di {@code HSet} e che contenga esattamente tutte e sole le
     * chiavi della mappa originale.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che {@code testMap.keySet()} sia un'istanza di
     * {@code HSet}.<br>
     * 2. Si itera sul set di chiavi e si verifica che ogni elemento sia una
     * chiave valida nella mappa.<br>
     * 3. Si conta il numero di elementi nel set e si verifica che corrisponda
     * alla dimensione della mappa.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa e la vista rimangono invariate.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire un {@code HSet} contenente tutte le chiavi della
     * mappa.
     */
    @Test
    public void testKeySetInstance() {
        assertTrue(testMap.keySet() instanceof HSet);

        HSet s = testMap.keySet();
        HIterator it = s.iterator();
        int i = 0;
        while (it.hasNext()) {
            // Controllo che ogni elemento del set sia effettivamente una chiave della
            // mappa.
            assertTrue(testMap.containsKey(it.next()));
            i += 1;
        }
        assertTrue(testMap.size() == i);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica della rimozione tramite l'iteratore della vista {@code keySet()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Test fondamentale per il meccanismo di "backing". L'obiettivo è
     * assicurare che una chiamata a {@code remove()} sull'iteratore del
     * {@code keySet} provochi la rimozione della mappatura corrispondente dalla
     * mappa principale.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene l'iteratore dal {@code keySet}.<br>
     * 2. Si avanza l'iteratore con {@code next()} per ottenere una chiave.<br>
     * 3. Si invoca {@code remove()} sull'iteratore.<br>
     * 4. Si verifica che la chiave sia stata rimossa dalla mappa originale.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa con almeno un elemento.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa non contiene più la coppia chiave-valore corrispondente alla
     * chiave rimossa.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La rimozione tramite iteratore del set deve propagarsi alla mappa, che
     * deve risultare modificata.
     */
    @Test
    public void testKeySetIteratorRemove() {
        // Creo una mappa e aggiungo un solo elemento.
        testMap = new MapAdapter();
        testMap.put("prezzemolo", 1);

        // Creo la vista delle chiavi e il relativo iteratore.
        HSet set = testMap.keySet();
        HIterator it = set.iterator();

        // Controllo che l'elemento ottenuto dall'iteratore sia presente nella mappa.
        Object tmp = it.next();
        assertTrue(testMap.containsKey(tmp));

        // Rimuovo l'elemento tramite l'iteratore del set.
        it.remove();

        // Controllo che la chiave non sia più presente nella mappa.
        assertFalse(testMap.containsKey(tmp));
        assertTrue(testMap.isEmpty());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica della rimozione tramite il metodo {@code remove()} della vista
     * {@code keySet()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Test per il "backing". Si assicura che una chiamata a {@code remove()}
     * direttamente sul {@code HSet} delle chiavi rimuova la mappatura dalla mappa
     * principale.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene il {@code keySet} dalla mappa.<br>
     * 2. Si sceglie una chiave da rimuovere (es. 5).<br>
     * 3. Si invoca {@code set.remove(5)}.<br>
     * 4. Si verifica che la chiave 5 non sia più presente nella mappa originale.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa contenente la chiave da rimuovere.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa non contiene più la coppia con la chiave rimossa.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata a {@code remove()} sul set deve modificare la mappa
     * sottostante.
     */
    @Test
    public void testKeySetRemove() {
        // Creo una mappa con un solo elemento.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("prezzemolo", 1);

        // Creo la vista delle chiavi (keySet).
        HSet set = tmpMap.keySet();

        // Controllo che la chiave "prezzemolo" sia presente sia nella mappa che nel
        // set.
        assertTrue(set.contains("prezzemolo"));
        assertTrue(tmpMap.containsKey("prezzemolo"));

        // Rimuovo l'elemento direttamente dal set.
        set.remove("prezzemolo");

        // Controllo che la chiave "prezzemolo" sia stata rimossa anche dalla mappa.
        assertFalse(tmpMap.containsKey("prezzemolo"));
        assertTrue(tmpMap.isEmpty());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code removeAll()} sulla vista {@code keySet()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test verifica che l'operazione di rimozione massiva sul {@code keySet}
     * si propaghi correttamente, rimuovendo tutte le mappature corrispondenti
     * dalla mappa sottostante.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione di chiavi da rimuovere.<br>
     * 2. Si ottiene il {@code keySet} dalla mappa.<br>
     * 3. Si invoca {@code removeAll()} sul set con la collezione creata.<br>
     * 4. Si verifica che le chiavi corrispondenti siano state rimosse dalla
     * mappa, mentre le altre siano rimaste.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa popolata e una collezione di chiavi da rimuovere.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa non contiene più le coppie con le chiavi rimosse.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La mappa deve contenere solo le mappature le cui chiavi non erano nella
     * collezione da rimuovere.
     */
    @Test
    public void testKeySetRemoveAll() {
        // Creo una mappa di test con alcuni elementi.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put(0, 45);
        tmpMap.put("prezzemolo", 14);

        // Creo la vista delle chiavi
        HSet set = tmpMap.keySet();

        // Controllo che le chiavi "prezzemolo" e 0 siano presenti nella mappa
        assertTrue(tmpMap.containsKey(0));
        assertTrue(set.contains(0));
        assertTrue(tmpMap.containsKey("prezzemolo"));
        assertTrue(set.contains("prezzemolo"));

        // Rimuovo dal set una collezione di chiavi
        HSet toRemove = testMap.keySet();
        set.removeAll(toRemove);

        // Controllo che la chiave 0 non sia più nella mappa, mentre "prezzemolo" sì
        assertFalse(tmpMap.containsKey(0));
        assertTrue(tmpMap.containsKey("prezzemolo"));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code retainAll()} sulla vista {@code keySet()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica che l'operazione {@code retainAll} sul {@code keySet}
     * mantenga nella mappa solo le mappature le cui chiavi sono presenti anche
     * nella collezione specificata, rimuovendo tutte le altre.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione di chiavi da mantenere.<br>
     * 2. Si ottiene il {@code keySet} dalla mappa.<br>
     * 3. Si invoca {@code retainAll()} sul set con la collezione creata.<br>
     * 4. Si verifica che nella mappa siano rimaste solo le chiavi specificate.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa popolata e una collezione di chiavi da mantenere.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa contiene solo le coppie con le chiavi mantenute.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La mappa deve essere modificata per contenere solo gli elementi le cui
     * chiavi sono presenti nella collezione argomento.
     */
    @Test
    public void testKeySetRetainAll() {
        // Creo una mappa di test con alcuni elementi.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("prezzemolo", 1);
        tmpMap.put(0, 65);

        // Creo la vista delle chiavi.
        HSet set = tmpMap.keySet();

        // Controllo che le chiavi "prezzemolo" e 0 siano presenti nella mappa e nel
        // set.
        assertTrue(tmpMap.containsKey("prezzemolo"));
        assertTrue(set.contains("prezzemolo"));
        assertTrue(tmpMap.containsKey(0));
        assertTrue(set.contains(0));

        // Mantengo nel set solo gli elementi presenti anche in un'altra collezione.
        HSet toRetain = testMap.keySet();
        set.retainAll(toRetain);

        // Controllo che la chiave "prezzemolo" non sia più nella mappa, mentre 0 sì.
        assertFalse(tmpMap.containsKey("prezzemolo"));
        assertTrue(tmpMap.containsKey(0));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code clear()} sulla vista {@code keySet()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che la chiamata a {@code clear()} sul {@code keySet}
     * svuoti completamente la mappa sottostante, dimostrando il corretto
     * "backing".
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene il {@code keySet} dalla mappa.<br>
     * 2. Si invoca {@code clear()} sul set.<br>
     * 3. Si verifica che la mappa originale sia diventata vuota.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa è vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La mappa sottostante deve risultare vuota dopo la chiamata a
     * {@code clear()} sulla sua vista delle chiavi.
     */
    @Test
    public void testKeySetClear() {
        // Creo una mappa di test con alcuni elementi.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("prezzemolo", 1);
        tmpMap.put(0, 65);

        // Creo la vista delle chiavi.
        HSet set = tmpMap.keySet();

        // Controllo che le chiavi "prezzemolo" e 0 siano presenti nella mappa.
        assertTrue(tmpMap.containsKey("prezzemolo"));
        assertTrue(set.contains("prezzemolo"));
        assertTrue(tmpMap.containsKey(0));
        assertTrue(set.contains(0));

        // Svuoto il set.
        set.clear();

        // Controllo che le chiavi "prezzemolo" e 0 non siano più presenti nella mappa.
        assertFalse(tmpMap.containsKey("prezzemolo"));
        assertFalse(tmpMap.containsKey(0));
        assertTrue(tmpMap.isEmpty());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che le modifiche alla mappa si riflettano sulla vista
     * {@code keySet()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica il "backing" nella direzione opposta: dalla mappa
     * alla vista. Si assicura che il contenuto del {@code keySet} si aggiorni
     * dinamicamente quando vengono effettuate modifiche direttamente sulla mappa.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene il {@code keySet} dalla mappa popolata.<br>
     * 2. Si svuota la mappa e si verifica che anche il set risulti vuoto.<br>
     * 3. Si aggiunge un elemento alla mappa e si verifica che la sua chiave
     * appaia nel set.<br>
     * 4. Si rimuove l'elemento dalla mappa e si verifica che la sua chiave
     * scompaia dal set.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Sia la mappa che il set risultano vuoti.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il set di chiavi deve riflettere dinamicamente tutte le modifiche (clear,
     * put, remove) apportate alla mappa sottostante.
     */
    @Test
    public void testMapChangeReflectsOnKeySet() {
        // Creo la vista delle chiavi di testMap e mi assicuro che non sia vuota.
        HSet set = testMap.keySet();
        assertFalse(set.isEmpty());

        // Svuoto la mappa e controllo che anche il set risulti vuoto.
        testMap.clear();
        assertTrue(set.isEmpty());

        // Aggiungo un elemento a testMap e controllo che appaia anche nel set.
        testMap.put(9, 11);
        assertTrue(set.contains(9));

        // Rimuovo un elemento dalla mappa e controllo che sia rimosso anche dal set.
        testMap.remove(9);
        assertFalse(set.contains(9));

        // Controllo che sia la mappa che il set siano ora vuoti.
        assertTrue(set.isEmpty());
        assertTrue(testMap.isEmpty());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che il metodo {@code values()} restituisca una vista corretta.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che l'oggetto restituito da {@code values()} sia
     * un'istanza di {@code HCollection} e che contenga tutti e soli i valori
     * della mappa originale.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che {@code testMap.values()} sia un'istanza di
     * {@code HCollection}.<br>
     * 2. Si itera sulla collezione di valori e si verifica che ogni elemento sia
     * un valore valido nella mappa.<br>
     * 3. Si conta il numero di elementi e si verifica che corrisponda alla
     * dimensione della mappa.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa e la vista rimangono invariate.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire una {@code HCollection} contenente tutti i valori
     * della mappa.
     */
    @Test
    public void testValuesInstance() {
        assertTrue(testMap.values() instanceof HCollection);

        HCollection s = testMap.values();
        HIterator it = s.iterator();
        int i = 0;
        while (it.hasNext()) {
            // Controllo che tutti gli elementi della collezione siano valori della mappa.
            assertTrue(testMap.containsValue(it.next()));
            i += 1;
        }
        assertTrue(testMap.size() == i);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica della rimozione tramite l'iteratore della vista {@code values()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Test cruciale per il "backing". L'obiettivo è assicurare che una chiamata
     * a {@code remove()} sull'iteratore della collezione di valori provochi la
     * rimozione della mappatura corrispondente dalla mappa principale.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene l'iteratore dalla collezione dei valori.<br>
     * 2. Si avanza l'iteratore con {@code next()} e si invoca {@code remove()}.<br>
     * 3. Si verifica che la dimensione della mappa sia diminuita e che l'elemento
     * rimosso non sia più presente.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa con almeno un elemento.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa ha una dimensione ridotta di uno.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La rimozione tramite iteratore della collezione deve propagarsi alla
     * mappa, che deve risultare modificata.
     */
    @Test
    public void testValuesIteratorRemove() {
        // Creo una mappa e aggiungo un elemento.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("prezzemolo", 1);

        // Creo la vista dei valori e il suo iteratore.
        HCollection col = tmpMap.values();
        HIterator it = col.iterator();

        // Controllo che il valore ottenuto dall'iteratore sia presente nella mappa.
        Object tmp = it.next();
        assertTrue(tmpMap.containsValue(tmp));

        // Rimuovo il valore tramite l'iteratore della collezione.
        it.remove();

        // Controllo che il valore non sia più presente nella mappa.
        assertFalse(tmpMap.containsValue(tmp));
        assertTrue(tmpMap.isEmpty());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica della rimozione tramite il metodo {@code remove()} della vista
     * {@code values()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Test per il "backing". Si assicura che una chiamata a {@code remove()}
     * direttamente sulla collezione di valori rimuova la prima mappatura
     * corrispondente dalla mappa principale.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene la collezione di valori dalla mappa.<br>
     * 2. Si sceglie un valore da rimuovere (es. "noce").<br>
     * 3. Si invoca {@code col.remove("noce")}.<br>
     * 4. Si verifica che il valore "noce" non sia più presente nella mappa.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa contenente il valore da rimuovere.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa non contiene più la coppia con il valore rimosso.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata a {@code remove()} sulla collezione deve modificare la mappa
     * sottostante.
     */
    @Test
    public void testValuesRemove() {
        // Creo una mappa con un singolo elemento.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("prezzemolo", 1);

        // Creo la vista dei valori (values).
        HCollection col = tmpMap.values();

        // Controllo che il valore 1 sia presente nella mappa.
        assertTrue(tmpMap.containsValue(1));
        assertTrue(col.contains(1));

        // Rimuovo il valore direttamente dalla collezione.
        col.remove(1);

        // Controllo che il valore 1 sia stato rimosso anche dalla mappa.
        assertFalse(tmpMap.containsValue(1));
        assertTrue(tmpMap.isEmpty());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code removeAll()} sulla vista {@code values()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test verifica che l'operazione di rimozione massiva sulla vista dei
     * valori si propaghi correttamente, rimuovendo tutte le mappature
     * corrispondenti dalla mappa sottostante.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione di valori da rimuovere.<br>
     * 2. Si ottiene la vista dei valori dalla mappa.<br>
     * 3. Si invoca {@code removeAll()} sulla vista con la collezione creata.<br>
     * 4. Si verifica che i valori corrispondenti siano stati rimossi dalla
     * mappa.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa popolata e una collezione di valori da rimuovere.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa non contiene più le coppie con i valori rimosse.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La mappa deve contenere solo le mappature i cui valori non erano nella
     * collezione da rimuovere.
     */
    @Test
    public void testValuesRemoveAll() {
        // Creo una mappa di test con alcuni elementi.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("prezzemolo", "noce");
        tmpMap.put(0, 65);

        // Creo la vista dei valori.
        HCollection col = tmpMap.values();

        // Controllo che i valori 65 e "noce" siano presenti nella mappa.
        assertTrue(tmpMap.containsValue("noce"));
        assertTrue(col.contains("noce"));
        assertTrue(tmpMap.containsValue(65));
        assertTrue(col.contains(65));

        // Rimuovo dalla vista una collezione di valori.
        HCollection toRemove = testMap.values();
        col.removeAll(toRemove);

        // Controllo che il valore "noce" non sia più nella mappa, mentre 65 sì.
        assertTrue(tmpMap.containsValue(65));
        assertFalse(tmpMap.containsValue("noce"));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code retainAll()} sulla vista {@code values()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica che {@code retainAll} sulla vista dei valori
     * mantenga nella mappa solo le mappature i cui valori sono presenti nella
     * collezione specificata.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione di valori da mantenere.<br>
     * 2. Si ottiene la vista dei valori dalla mappa.<br>
     * 3. Si invoca {@code retainAll()} sulla vista con la collezione creata.<br>
     * 4. Si verifica che nella mappa siano rimasti solo i valori specificati.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa popolata e una collezione di valori da mantenere.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa contiene solo le coppie con i valori mantenuti.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La mappa deve essere modificata per contenere solo gli elementi i cui
     * valori sono presenti nella collezione argomento.
     */
    @Test
    public void testValuesRetainAll() {
        // Creo una mappa di test con alcuni elementi.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("prezzemolo", "mano");
        tmpMap.put(0, 65);

        // Creo la vista dei valori.
        HCollection col = tmpMap.values();

        // Controllo che i valori "mano" e 65 siano presenti nella mappa.
        assertTrue(tmpMap.containsValue("mano"));
        assertTrue(col.contains("mano"));
        assertTrue(tmpMap.containsValue(65));
        assertTrue(col.contains(65));

        // Mantengo nella vista solo i valori presenti anche in un'altra collezione.
        HCollection toRetain = testMap.values();
        col.retainAll(toRetain);

        // Controllo che il valore 65 non sia più nella mappa, mentre "mano" sì.
        assertFalse(tmpMap.containsValue(65));
        assertTrue(tmpMap.containsValue("mano"));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code clear()} sulla vista {@code values()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che la chiamata a {@code clear()} sulla vista dei valori
     * svuoti completamente la mappa sottostante.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene la vista dei valori dalla mappa.<br>
     * 2. Si invoca {@code clear()} sulla collezione.<br>
     * 3. Si verifica che la mappa originale sia diventata vuota.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa è vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La mappa sottostante deve risultare vuota dopo la chiamata a
     * {@code clear()} sulla sua vista dei valori.
     */
    @Test
    public void testValuesClear() {
        // Creo una mappa di test con alcuni elementi.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("prezzemolo", 1);
        tmpMap.put(0, 65);
        // Creo la vista dei valori.
        HCollection col = tmpMap.values();
        // Controllo che i valori 1 e 65 siano presenti nella mappa.
        assertTrue(tmpMap.containsValue(1));
        assertTrue(tmpMap.containsValue(65));
        // Svuoto la collezione di valori.
        col.clear();
        // Controllo che i valori 1 e 65 non siano più presenti nella mappa.
        assertFalse(tmpMap.containsValue(1));
        assertFalse(tmpMap.containsValue(65));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che le modifiche alla mappa si riflettano sulla vista
     * {@code values()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica il "backing" dalla mappa alla vista dei valori. Si
     * assicura che il contenuto della collezione {@code values} si aggiorni
     * dinamicamente in risposta a modifiche sulla mappa.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene la vista dei valori dalla mappa popolata.<br>
     * 2. Si svuota la mappa e si verifica che la vista risulti vuota.<br>
     * 3. Si aggiunge un elemento alla mappa e si verifica che il suo valore
     * appaia nella vista.<br>
     * 4. Si rimuove l'elemento e si verifica che il valore scompaia dalla vista.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Sia la mappa che la vista risultano vuote.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La vista dei valori deve riflettere dinamicamente tutte le modifiche
     * apportate alla mappa.
     */
    @Test
    public void testMapChangeReflectsOnValues() {
        // Creo la vista dei valori di testMap e mi assicuro che non sia vuota.
        HCollection col = testMap.values();
        assertFalse(col.isEmpty());

        // Svuoto la mappa e controllo che anche la vista dei valori risulti vuota.
        testMap.clear();
        assertTrue(col.isEmpty());

        // Aggiungo un elemento a testMap e controllo che il valore appaia nella vista.
        testMap.put(9, 11);
        assertTrue(col.contains(11));

        // Rimuovo un elemento dalla mappa e controllo che il valore sia rimosso dalla
        // vista.
        testMap.remove(9);
        assertFalse(col.contains(11));

        // Controllo che sia la mappa che la vista siano ora vuote.
        assertTrue(testMap.isEmpty());
        assertTrue(col.isEmpty());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che il metodo {@code entrySet()} restituisca una vista corretta.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che l'oggetto restituito da {@code entrySet()} sia un
     * {@code HSet} e che contenga le entry corrette, dove ogni entry riflette una
     * mappatura chiave-valore della mappa.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che {@code testMap.entrySet()} sia un'istanza di
     * {@code HSet}.<br>
     * 2. Si itera sul set di entry e si verifica che per ogni entry, la chiave e
     * il valore corrispondano a una mappatura valida nella mappa.<br>
     * 3. Si verifica che il numero di entry corrisponda alla dimensione della
     * mappa.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa e la vista rimangono invariate.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire un {@code HSet} contenente tutte le entry della
     * mappa.
     */
    @Test
    public void testEntrySetInstance() {
        assertTrue(testMap.entrySet() instanceof HSet);

        HSet s = testMap.entrySet();
        HIterator it = s.iterator();
        int i = 0;
        while (it.hasNext()) {
            HEntry tmpEntry = (HEntry) it.next();
            // Controllo che ogni elemento del set sia una coppia (entry) valida della
            // mappa.
            assertTrue(testMap.containsKey(tmpEntry.getKey())
                    && testMap.get(tmpEntry.getKey()).equals(tmpEntry.getValue()));
            i += 1;
        }
        assertTrue(testMap.size() == i);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica della rimozione tramite l'iteratore della vista {@code entrySet()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Test di "backing" che assicura che {@code remove()} sull'iteratore del
     * {@code entrySet} rimuova la mappatura corrispondente dalla mappa
     * principale.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene l'iteratore dall'{@code entrySet}.<br>
     * 2. Si ottiene un'entry con {@code next()} e la si rimuove con
     * {@code remove()}.<br>
     * 3. Si verifica che la mappa non contenga più la mappatura rimossa.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa con almeno un elemento.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa ha una dimensione ridotta di uno.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La rimozione tramite iteratore del set deve propagarsi alla mappa.
     */
    @Test
    public void testEntrySetIteratorRemove() {
        // Creo una mappa e aggiungo un elemento.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("prezzemolo", 1);

        // Creo la vista delle entry e il suo iteratore.
        HSet tmpSet = tmpMap.entrySet();
        HIterator it = tmpSet.iterator();

        // Controllo che la entry ottenuta dall'iteratore sia presente nella mappa.
        HEntry tmp = (HEntry) it.next();
        assertTrue(tmpMap.containsKey(tmp.getKey()) && tmpMap.get(tmp.getKey()).equals(tmp.getValue()));

        // Rimuovo la entry tramite l'iteratore.
        it.remove();

        // Controllo che la entry non sia più presente nella mappa.
        assertFalse(tmpMap.containsKey(tmp.getKey()) && tmpMap.get(tmp.getKey()).equals(tmp.getValue()));
        assertTrue(tmpMap.isEmpty());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica della rimozione tramite il metodo {@code remove()} della vista
     * {@code entrySet()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Test di "backing" che assicura che {@code remove()} sull'{@code entrySet}
     * rimuova la mappatura corrispondente dalla mappa principale.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene l'{@code entrySet} e un'entry da esso.<br>
     * 2. Si invoca {@code remove()} sul set con l'entry ottenuta.<br>
     * 3. Si verifica che la mappa non contenga più la mappatura rimossa.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa contenente l'entry da rimuovere.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa non contiene più la coppia chiave-valore rimossa.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata a {@code remove()} sul set deve modificare la mappa
     * sottostante.
     */
    @Test
    public void testEntrySetRemove() {
        // Creo una mappa con un singolo elemento.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("prezzemolo", 1);

        // Creo la vista delle entry e il suo iteratore.
        HSet tmpSet = tmpMap.entrySet();
        HIterator it = tmpSet.iterator();

        // Controllo che la coppia (entry) sia presente nella mappa.
        HEntry tmp = (HEntry) it.next();
        assertTrue(tmpMap.containsKey(tmp.getKey()) && tmpMap.get(tmp.getKey()).equals(tmp.getValue()));

        // Rimuovo la entry direttamente dal set.
        tmpSet.remove(tmp);

        // Controllo che la entry sia stata rimossa anche dalla mappa.
        assertFalse(tmpMap.containsKey(tmp.getKey()) && tmpMap.get(tmp.getKey()).equals(tmp.getValue()));
        assertTrue(tmpMap.isEmpty());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code removeAll()} sulla vista {@code entrySet()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test verifica che la rimozione massiva sul {@code entrySet} si propaghi
     * alla mappa, rimuovendo tutte le mappature corrispondenti.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione di entry da rimuovere.<br>
     * 2. Si invoca {@code removeAll()} sull'{@code entrySet} con la collezione
     * creata.<br>
     * 3. Si verifica che le entry corrispondenti siano state rimosse dalla
     * mappa.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa popolata e una collezione di entry da rimuovere.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa non contiene più le coppie corrispondenti alle entry rimosse.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La mappa deve contenere solo le mappature che non erano nella collezione
     * da rimuovere.
     */
    @Test
    public void testEntrySetRemoveAll() {
        // Creo una mappa di test con alcuni elementi.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put(5, "noce");
        tmpMap.put(0, 65);

        // Creo la vista delle entry e il suo iteratore.
        HSet tmpSet = tmpMap.entrySet();
        HIterator it = tmpSet.iterator();

        // Controllo che le entry siano presenti nella mappa.
        HEntry tmp1 = (HEntry) it.next();
        assertTrue(tmpMap.containsKey(tmp1.getKey()) && tmpMap.get(tmp1.getKey()).equals(tmp1.getValue()));
        HEntry tmp2 = (HEntry) it.next();
        assertTrue(tmpMap.containsKey(tmp2.getKey()) && tmpMap.get(tmp2.getKey()).equals(tmp2.getValue()));

        // Rimuovo dalla vista una collezione di entry.
        HSet toRemove = testMap.entrySet();
        tmpSet.removeAll(toRemove);

        // Controllo che la entry comune sia stata rimossa, mentre l'altra no.
        assertTrue(tmpMap.get(0).equals(65));
        assertFalse(tmpMap.containsKey(5) && tmpMap.get(5).equals("noce"));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code retainAll()} sulla vista {@code entrySet()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica che {@code retainAll} sull'{@code entrySet} mantenga
     * nella mappa solo le mappature presenti anche nella collezione specificata.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione di entry da mantenere.<br>
     * 2. Si invoca {@code retainAll()} sull'{@code entrySet} con la collezione
     * creata.<br>
     * 3. Si verifica che nella mappa siano rimaste solo le entry specificate.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa popolata e una collezione di entry da mantenere.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa contiene solo le coppie corrispondenti alle entry mantenute.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La mappa deve essere modificata per contenere solo le entry presenti nella
     * collezione argomento.
     */
    @Test
    public void testEntrySetRetainAll() {
        // Creo una mappa di test con alcuni elementi.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("prezzemolo", "mano");
        tmpMap.put(0, "pippo");

        // Creo la vista delle entry e il suo iteratore.
        HSet tmpSet = tmpMap.entrySet();
        HIterator it = tmpSet.iterator();

        // Controllo che le entry siano presenti nella mappa.
        HEntry tmp1 = (HEntry) it.next();
        assertTrue(tmpMap.containsKey(tmp1.getKey()) && tmpMap.get(tmp1.getKey()).equals(tmp1.getValue()));
        HEntry tmp2 = (HEntry) it.next();
        assertTrue(tmpMap.containsKey(tmp2.getKey()) && tmpMap.get(tmp2.getKey()).equals(tmp2.getValue()));

        // Mantengo nella vista solo le entry presenti anche in un'altra collezione.
        HSet toRetain = testMap.entrySet();
        tmpSet.retainAll(toRetain);

        // Controllo che la entry non comune sia stata rimossa, mentre quella comune sia
        // rimasta.
        assertTrue(tmpMap.containsValue("pippo") && tmpMap.containsKey(0));
        assertFalse(tmpMap.containsValue("mano") && tmpMap.containsKey("prezzemolo"));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code clear()} sulla vista {@code entrySet()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che la chiamata a {@code clear()} sull'{@code entrySet}
     * svuoti completamente la mappa sottostante.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene l'{@code entrySet} dalla mappa.<br>
     * 2. Si invoca {@code clear()} sul set.<br>
     * 3. Si verifica che la mappa originale sia diventata vuota.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una mappa popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa è vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La mappa sottostante deve risultare vuota dopo la chiamata a
     * {@code clear()} sulla sua vista delle entry.
     */
    @Test
    public void testEntrySetClear() {
        // Creo una mappa di test con alcuni elementi.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("prezzemolo", "mano");
        tmpMap.put(0, "pippo");

        // Creo la vista delle entry e il suo iteratore.
        HSet tmpSet = tmpMap.entrySet();
        HIterator it = tmpSet.iterator();

        // Controllo che le entry siano presenti nella mappa.
        HEntry tmp1 = (HEntry) it.next();
        assertTrue(tmpMap.containsKey(tmp1.getKey()) && tmpMap.get(tmp1.getKey()).equals(tmp1.getValue()));
        HEntry tmp2 = (HEntry) it.next();
        assertTrue(tmpMap.containsKey(tmp2.getKey()) && tmpMap.get(tmp2.getKey()).equals(tmp2.getValue()));

        // Svuoto la vista delle entry.
        tmpSet.clear();
        // Controllo che le entry non siano più presenti nella mappa.
        assertFalse(tmpMap.containsKey(tmp1.getKey()) && tmpMap.get(tmp1.getKey()).equals(tmp1.getValue()));
        assertFalse(tmpMap.containsKey(tmp2.getKey()) && tmpMap.get(tmp2.getKey()).equals(tmp2.getValue()));
        assertTrue(tmpMap.isEmpty());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che le modifiche alla mappa si riflettano sulla vista
     * {@code entrySet()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica il "backing" dalla mappa all'{@code entrySet}. Si
     * assicura che il contenuto del set si aggiorni dinamicamente in risposta a
     * modifiche sulla mappa.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene l'{@code entrySet} dalla mappa popolata.<br>
     * 2. Si svuota la mappa e si verifica che il set risulti vuoto.<br>
     * 3. Si aggiunge un elemento alla mappa e si verifica che la entry
     * corrispondente appaia nel set.<br>
     * 4. Si rimuove l'elemento e si verifica che la entry scompaia dal set.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code MapAdapter} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Sia la mappa che il set risultano vuoti.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * L'{@code entrySet} deve riflettere dinamicamente tutte le modifiche
     * (clear, put, remove) apportate alla mappa.
     */
    @Test
    public void testMapChangeReflectsOnEntrySet() {
        // Creo la vista delle entry di testMap e mi assicuro che non sia vuota.
        HSet tmpSet = testMap.entrySet();
        assertFalse(tmpSet.isEmpty());

        // Svuoto la mappa e controllo che anche la vista delle entry risulti vuota.
        testMap.clear();
        assertTrue(tmpSet.isEmpty());

        // Aggiungo una entry a testMap e controllo che appaia nella vista.
        testMap.put(9, 11);
        HIterator it = tmpSet.iterator();
        HEntry entry = (HEntry) it.next();
        assertTrue(entry.getKey().equals(9) && entry.getValue().equals(11));

        // Rimuovo una entry dalla mappa e controllo che sia rimossa anche dalla vista.
        testMap.remove(9);
        assertFalse(tmpSet.contains(entry));

        // Controllo che sia la mappa che la vista siano ora vuote.
        assertTrue(testMap.isEmpty());
        assertTrue(tmpSet.isEmpty());
    }
}