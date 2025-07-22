package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import myAdapter.*;

/**
 * <b>Summary</b>
 * <p>
 * Questa suite di test, o Test Case, verifica il corretto funzionamento della
 * classe che implementa la vista delle chiavi ({@code KeySet}) di una
 * {@link myAdapter.MapAdapter}. L'obiettivo è testare che l'implementazione
 * dell'interfaccia {@link myAdapter.HSet} sia robusta, sicura e conforme alle
 * specifiche del Java Collections Framework (versione 1.4.2).
 * <p>
 * I test si concentrano sui metodi specifici del {@code KeySet}, verificando
 * la gestione di input validi e non validi, la corretta interazione con altre
 * collezioni e la coerenza con la mappa sottostante.
 * <p>
 * <b>Test Case Design</b>
 * <p>
 * La motivazione di questo test case è garantire che la vista delle chiavi,
 * essendo una componente fondamentale dell'interfaccia di una mappa, si
 * comporti in modo prevedibile e corretto. Poiché il {@code KeySet} è una
 * vista "backed" dalla {@code MapAdapter}, è cruciale che ogni operazione di
 * modifica sul set (come la rimozione di una chiave) si rifletta
 * immediatamente e correttamente sulla mappa originale. I test sono stati
 * progettati per coprire sia le funzionalità di base (come {@code contains} e
 * {@code remove}) sia le operazioni più complesse (come {@code removeAll} ed
 * {@code equals}), includendo casi limite e la gestione delle eccezioni.
 */
public class TestKeySet {

    private MapAdapter testMap = new MapAdapter();
    private HSet testKeySet = null;

    /**
     * Questo costruttore è vuoto e serve a soddisfare i requisiti
     * dello strumento Javadoc, evitando warning di documentazione.
     */
    public TestKeySet() {
        // Costruttore intenzionalmente lasciato vuoto.
    }

    /**
     * Prepara l'ambiente di test prima di ogni esecuzione, popolando la mappa
     * la cui vista delle chiavi (KeySet) verrà utilizzata e manipolata nei test.
     */
    @Before
    public void setUp() {
        testMap.put(5, "noce");
        testMap.put(9, "mano");
        testMap.put(1, "sasso");
        testMap.put(0, "pippo");

        testKeySet = testMap.keySet();
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che il metodo {@code contains(Object o)} sollevi una
     * {@code NullPointerException} se l'argomento è {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica che l'implementazione rispetti l'interfaccia delle
     * mappe che non ammettono chiavi nulle. Un tentativo di cercare una chiave
     * {@code null} in un set di chiavi deve fallire con l'eccezione appropriata.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Viene invocato il metodo {@code testKeySet.contains(null)} e ci si attende
     * che l'esecuzione lanci un'eccezione.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code KeySet} ({@code testKeySet}) non vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set e della mappa sottostante rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata al metodo deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testKeySetContainsNull() {
        testKeySet.contains(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il metodo {@code contains(Object o)} su un set popolato e vuoto.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test valida la logica fondamentale del metodo {@code contains()}.
     * L'obiettivo è verificare che il metodo restituisca {@code true} se la chiave
     * specificata è presente nel set e {@code false} in caso contrario, coprendo
     * anche il caso di un set vuoto.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si invoca {@code contains()} con una chiave presente (5), verificando che
     * il risultato sia {@code true}.<br>
     * 2. Si invoca {@code contains()} con una chiave non presente (2), verificando
     * che il risultato sia {@code false}.<br>
     * 3. Si svuota il set tramite {@code clear()}.<br>
     * 4. Si invoca {@code contains()} sul set vuoto, verificando che il risultato
     * sia {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code KeySet} popolata con 4 chiavi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Al termine del test, il {@code KeySet} e la mappa sottostante risultano
     * vuoti.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code contains()} deve restituire {@code true} per la chiave presente e
     * {@code false} per la chiave non presente. Dopo la pulizia, deve restituire
     * {@code false}.
     */
    @Test
    public void testKeySetContains() {
        assertTrue("Se il set contiene l'elemento '5', il metodo ritorna true", testKeySet.contains(5));
        assertFalse("Il set non contiene l'elemento '2'", testKeySet.contains(2));
        testKeySet.clear();
        assertFalse("Il set è vuoto e quindi non contiene nessun oggetto", testKeySet.contains(new Object()));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il metodo {@code iterator()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che l'iteratore restituito sia conforme all'interfaccia
     * {@link HIterator} e interagisca correttamente con il set sottostante. Si
     * verifica la capacità di attraversare tutti gli elementi, di rimuoverli
     * durante l'iterazione e il comportamento corretto su un set vuoto.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che l'oggetto restituito da {@code iterator()} sia un'istanza
     * di {@code HIterator}.<br>
     * 2. Si itera attraverso il set usando un ciclo
     * {@code while (it.hasNext())}.<br>
     * 3. All'interno del ciclo, per ogni chiave, si verifica la sua presenza nel
     * set, la si rimuove con {@code it.remove()}, e si verifica la sua successiva
     * assenza.<br>
     * 4. Al termine del ciclo, si verifica che la dimensione del set sia 0.<br>
     * 5. Si crea un nuovo iteratore sul set vuoto e si verifica che
     * {@code hasNext()} restituisca {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un {@code KeySet} popolato con 4 chiavi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Il {@code KeySet} e la mappa sottostante sono vuoti.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * L'iteratore deve attraversare e rimuovere tutte le chiavi, portando la
     * dimensione del set a 0. Un iteratore su un set vuoto non deve avere
     * elementi.
     */
    @Test
    public void testKeySetIterator() {
        assertTrue(testKeySet.iterator() instanceof HIterator);

        HIterator it = testKeySet.iterator();
        while (it.hasNext()) {
            Object k = it.next();
            assertTrue(testKeySet.contains(k));
            it.remove();
            assertFalse(testKeySet.contains(k));
        }
        assertEquals(0, testKeySet.size());

        it = testKeySet.iterator();
        assertFalse(it.hasNext());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che il metodo {@code remove(Object o)} sollevi una
     * {@code NullPointerException} quando si tenta di rimuovere un oggetto
     * {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Come per {@code contains(null)}, questo test garantisce che l'operazione di
     * rimozione gestisca correttamente gli input nulli, dato che le chiavi nulle
     * non sono permesse.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Viene invocato il metodo {@code testKeySet.remove(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code KeySet} non vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set e della mappa sottostante rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Deve essere sollevata una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testKeySetRemoveNull() {
        testKeySet.remove(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il metodo {@code remove(Object o)} con una chiave presente e non
     * presente.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test valida la logica di {@code remove()} e la sua natura "backed".
     * Verifica che la rimozione di una chiave esistente restituisca {@code true} e
     * modifichi sia il set che la mappa, mentre la rimozione di una chiave non
     * esistente restituisca {@code false} senza causare modifiche.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica la presenza della chiave 5, poi la si rimuove, controllando
     * che {@code remove()} restituisca {@code true}.<br>
     * 2. Si verifica che la chiave 5 non sia più presente nel set.<br>
     * 3. Si tenta di rimuovere una chiave non presente (24), controllando che
     * {@code remove()} restituisca {@code false}.<br>
     * 4. Si verifica che la modifica sia stata propagata alla mappa, controllando
     * che {@code testMap} non contenga più la chiave 5.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un {@code KeySet} popolato che contiene la chiave 5.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La chiave 5 è stata rimossa sia dal set che dalla mappa sottostante.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code remove()} restituisce {@code true} per la chiave rimossa e
     * {@code false} per quella non presente. Il set e la mappa sono
     * coerentemente modificati.
     */
    @Test
    public void testKeySetRemove() {
        assertTrue(testKeySet.contains(5));
        assertTrue(testKeySet.remove(5));
        assertFalse(testKeySet.contains(5));
        assertFalse(testKeySet.remove(24));
        assertFalse(testMap.containsKey(5));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che {@code containsAll(HCollection c)} sollevi una
     * {@code NullPointerException} quando la collezione argomento è {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test verifica che il metodo gestisca correttamente un argomento nullo,
     * come specificato dall'interfaccia {@code HCollection}.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testKeySet.containsAll(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code KeySet} non vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testKeySetContainsAllNull() {
        testKeySet.containsAll(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il metodo {@code containsAll(HCollection c)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test valida la logica di {@code containsAll} in tre scenari: il confronto
     * con una collezione vuota (deve essere {@code true}), con un sottoinsieme
     * proprio ({@code true}), e con un insieme che contiene elementi non presenti
     * ({@code false}).
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che {@code containsAll} con una collezione vuota restituisca
     * {@code true}.<br>
     * 2. Si popola una collezione di supporto con un sottoinsieme delle chiavi di
     * {@code testKeySet} e si verifica che {@code containsAll} restituisca
     * {@code true}.<br>
     * 3. Si aggiunge una nuova chiave (non presente in {@code testKeySet}) alla
     * collezione di supporto.<br>
     * 4. Si verifica che ora {@code containsAll} restituisca {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code KeySet} non vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code true} se tutte le chiavi della collezione
     * data sono presenti nel set, e {@code false} altrimenti.
     */
    @Test
    public void testKeySetContainsAll() {
        MapAdapter tmpMap = new MapAdapter();
        HSet contained = tmpMap.keySet();
        assertTrue(testKeySet.containsAll(contained));

        tmpMap.put(9, "");
        tmpMap.put(5, "");
        assertTrue(testKeySet.containsAll(contained));

        tmpMap.put(64, "");
        assertFalse(testKeySet.containsAll(contained));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che {@code removeAll(HCollection c)} sollevi una
     * {@code NullPointerException} quando si passa una collezione {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test verifica che il metodo di rimozione multipla gestisca correttamente
     * un argomento nullo, come da interfaccia delle collezioni.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Viene invocato il metodo {@code testKeySet.removeAll(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code KeySet} non vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testKeySetRemoveAllNull() {
        testKeySet.removeAll(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa {@code removeAll(HCollection c)} quando il set viene modificato,
     * verificando che il valore di ritorno sia corretto e che lo stato del set sia
     * consistente.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test valida lo scenario in cui {@code removeAll} viene invocato con
     * una collezione che ha elementi in comune con il set di destinazione.
     * L'obiettivo è duplice: primo, verificare che il metodo restituisca
     * {@code true} per indicare che il set è stato modificato; secondo, assicurare
     * che l'operazione di rimozione (differenza insiemistica) sia eseguita
     * correttamente, lasciando nel set di destinazione solo gli elementi non
     * presenti nella collezione argomento.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione di supporto, {@code tmpSet}, che contiene un
     * sottoinsieme delle chiavi di {@code testKeySet} (nello specifico, tutte le
     * chiavi tranne il 5).<br>
     * 2. Si invoca {@code testKeySet.removeAll(tmpSet)} e si verifica che il metodo
     * restituisca {@code true}, poiché il set di destinazione è stato
     * modificato.<br>
     * 3. Si itera su {@code tmpSet} per verificare che nessuna delle sue chiavi
     * ({0, 1, 9}) sia più presente in {@code testKeySet}.<br>
     * 4. Si verifica che l'unica chiave rimasta in {@code testKeySet} sia il 5,
     * ovvero l'elemento che non era presente nella collezione passata a
     * {@code removeAll}.<br>
     * 5. Si controlla che la dimensione finale di {@code testKeySet} sia 1.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un {@code KeySet} ({@code testKeySet}) con 4 chiavi: {0, 1, 5, 9}. Una
     * seconda collezione ({@code tmpSet}) che contiene un sottoinsieme di queste
     * chiavi: {0, 1, 9}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Il {@code testKeySet} contiene solo la chiave 5. La sua dimensione è 1. La
     * mappa sottostante è stata modificata di conseguenza.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code true}. La dimensione del set deve essere 1 e
     * l'unico elemento contenuto deve essere il 5.
     */
    @Test
    public void testKeySetRemoveAllWhenModified() {
        MapAdapter tmpMap = new MapAdapter(testMap);
        HSet tmpSet = tmpMap.keySet();
        tmpSet.remove(5);

        assertTrue(testKeySet.removeAll(tmpSet));

        HIterator it = tmpSet.iterator();
        while (it.hasNext())
            assertFalse(testKeySet.contains(it.next()));

        assertTrue(testKeySet.contains(5));
        assertEquals(1, testKeySet.size());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa {@code removeAll(HCollection c)} quando il set non viene modificato,
     * verificando che il valore di ritorno sia corretto e che lo stato del set
     * rimanga inalterato.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test è fondamentale per validare il comportamento di {@code removeAll}
     * nello scenario in cui non vi è alcuna intersezione tra il set di destinazione
     * e la collezione da rimuovere. Secondo l'interfaccia di {@code Collection}, in
     * questo caso il metodo non deve apportare alcuna modifica e deve restituire
     * {@code false} per segnalare che lo stato del set è rimasto inalterato. Questo
     * garantisce che il metodo non abbia effetti collaterali indesiderati.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione di supporto, {@code tmpSet}, contenente una chiave
     * (15) che non è presente in {@code testKeySet}.<br>
     * 2. Si verifica, per sicurezza, che non ci siano elementi in comune tra i due
     * set.<br>
     * 3. Si invoca {@code testKeySet.removeAll(tmpSet)}.<br>
     * 4. Si asserisce che il valore di ritorno del metodo sia {@code false}.<br>
     * 5. Si verifica che la dimensione e tutti gli elementi originali di
     * {@code testKeySet} siano rimasti invariati dopo la chiamata.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un {@code KeySet} ({@code testKeySet}) con 4 chiavi: {0, 1, 5, 9}. Una
     * seconda collezione ({@code tmpSet}) contenente una chiave disgiunta: {15}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato di {@code testKeySet} (contenuto e dimensione) e della mappa
     * sottostante rimane identico a quello precedente alla chiamata del metodo.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata a {@code removeAll} deve restituire {@code false}. Il
     * {@code testKeySet} deve mantenere tutti i suoi 4 elementi originali.
     */
    @Test
    public void testKeySetRemoveAllWhenNotModified() {
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put(15, "");
        HSet tmpSet = tmpMap.keySet();

        HIterator it = tmpSet.iterator();
        while (it.hasNext())
            assertFalse(testKeySet.contains(it.next()));

        assertFalse(testKeySet.removeAll(tmpSet));
        assertTrue(testKeySet.contains(5) &&
                testKeySet.contains(9) &&
                testKeySet.contains(1) &&
                testKeySet.contains(0));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il metodo {@code equals(Object o)} confrontando set uguali e diversi.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test valida la corretta implementazione dell'interfaccia di
     * {@code equals} per un {@code Set}. L'uguaglianza si basa su classe,
     * dimensione e contenuto. Il test copre casi di uguaglianza, disuguaglianza
     * per dimensione e disuguaglianza per contenuto a parità di dimensione.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea un set {@code tmpSet} identico a {@code testKeySet} e si verifica
     * che {@code equals()} restituisca {@code true}.<br>
     * 2. Si rimuove un elemento da {@code tmpSet} (dimensione diversa) e si
     * verifica che {@code equals()} restituisca {@code false}.<br>
     * 3. Si aggiunge un elemento diverso a {@code tmpSet} (stessa dimensione,
     * contenuto diverso) e si verifica che {@code equals()} restituisca ancora
     * {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un {@code KeySet} popolato.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato di {@code testKeySet} rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code equals()} deve restituire {@code true} per set identici e
     * {@code false} per set con dimensioni o elementi diversi.
     */
    @Test
    public void testKeySetEqualsSet() {
        MapAdapter tmpMap = new MapAdapter(testMap);
        HSet tmpSet = tmpMap.keySet();

        assertEquals(tmpSet.getClass(), testKeySet.getClass());
        assertEquals(testKeySet.size(), tmpSet.size());
        assertTrue(testKeySet.containsAll(tmpSet) && tmpSet.containsAll(testKeySet));
        assertTrue(testKeySet.equals(tmpSet));

        tmpSet.remove(5);

        assertNotEquals(testKeySet.size(), tmpSet.size());
        assertFalse(testKeySet.equals(tmpSet));

        tmpMap.put(6, "noce");
        assertEquals(testKeySet.size(), tmpSet.size());

        assertFalse(testKeySet.containsAll(tmpSet));
        assertFalse(tmpSet.containsAll(testKeySet));

        assertFalse(testKeySet.equals(tmpSet));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che il metodo {@code equals(Object o)} restituisca {@code false} quando
     * un {@code HSet} viene confrontato con oggetti di tipo incompatibile.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * La motivazione di questo test è verificare un requisito fondamentale del
     * metodo dell'interfaccia {@code equals()}: il metodo deve restituire
     * {@code false} se l'oggetto fornito per il confronto non è di un tipo
     * compatibile (in questo caso, un altro {@code HSet}). Per garantire la
     * robustezza del metodo, vengono usati come termini di paragone due tipi di
     * oggetti incompatibili: un'altra interfaccia di collezione
     * ({@code HCollection}) e un oggetto completamente estraneo ({@code String}).
     * Questo isola la logica di controllo del tipo, indipendentemente dal contenuto
     * degli oggetti.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Viene creata una mappa temporanea e indipendente ({@code tmpMap}) e da
     * essa viene ottenuta la sua vista dei valori, una {@code HCollection}.<br>
     * 2. Si invoca {@code testKeySet.equals()} passando come argomento la
     * {@code HCollection} creata. Il risultato atteso è {@code false}, poiché
     * l'implementazione di {@code equals} deve prima verificare la compatibilità
     * dei tipi.<br>
     * 3. Si invoca {@code testKeySet.equals()} passando un oggetto {@code String}
     * come argomento. Anche in questo caso, il risultato atteso è {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code KeySet} ({@code testKeySet}) non vuota, inizializzata
     * dal metodo {@code setUp()}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del {@code testKeySet} e della sua mappa di supporto
     * ({@code testMap}) rimane invariato, poiché il test utilizza oggetti
     * temporanei e separati per il confronto.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Entrambe le chiamate al metodo {@code equals()} devono restituire
     * {@code false}, confermando che il confronto tra tipi incompatibili fallisce
     * come previsto dall'interfaccia.
     */
    @Test
    public void testKeySetEqualsNotSet() {
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("a", 1);
        HCollection tmpCollection = tmpMap.values();

        assertNotEquals(testKeySet.getClass(), tmpCollection.getClass());
        assertFalse(testKeySet.equals(tmpCollection));

        assertFalse(testKeySet.equals("bacco"));
    }
}