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
 * Suite di test per la classe {@link myAdapter.MapAdapter.EntrySet}.
 * Questa suite testa la corretta implementazione dei metodi della classe
 * {@code EntrySet}, che implementa l'interfaccia {@link myAdapter.HSet}.
 * I test si concentrano sui metodi di cui è stato fatto l'override specifico
 * in {@code EntrySet}, come {@code contains()}, {@code remove()} e
 * {@code equals()}, dando per scontato il corretto funzionamento dei metodi
 * ereditati. Viene verificata anche la gestione delle eccezioni e la coerenza
 * con la mappa sottostante (backing).
 * <p>
 * <b>Test Case Design</b>
 * <p>
 * La motivazione di questo test case è garantire che la vista delle entry
 * ({@code EntrySet}) di {@code MapAdapter} sia pienamente conforme.
 * Poiché {@code EntrySet} è una vista supportata dalla mappa originale, è
 * cruciale verificare che le operazioni di modifica (es. rimozione) si
 * riflettano correttamente sulla mappa. I test sono stati progettati per
 * coprire casi standard, casi limite (collezioni vuote) e la gestione di
 * argomenti non validi ({@code null} o tipi errati) per garantire robustezza.
 */
public class TestEntrySet {
    private MapAdapter testMap = new MapAdapter();
    private HSet testEntrySet = null;

    /**
     * Questo costruttore è vuoto e serve a soddisfare i requisiti
     * dello strumento Javadoc, evitando warning di documentazione.
     */
    public TestEntrySet() {
        // Costruttore intenzionalmente lasciato vuoto.
    }

    /**
     * Prepara l'ambiente di test prima di ogni esecuzione, popolando la mappa
     * la cui vista delle Entry ({@code EntrySet}) sarà oggetto del test.
     */
    @Before
    public void setUp() {
        // Popolamento della mappa.
        testMap.put(5, "noce");
        testMap.put(9, "mano");
        testMap.put(1, "sasso");
        testMap.put(0, "pippo");

        testEntrySet = testMap.entrySet();
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che il metodo {@code contains(Object o)} sollevi una
     * {@code NullPointerException} se l'argomento fornito è {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che il metodo rispetti l'interfaccia delle collezioni,
     * che prevede il lancio di una {@code NullPointerException} per argomenti nulli
     * se la collezione non li supporta, come in questo caso.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Viene invocato il metodo {@code testEntrySet.contains(null)} e ci si aspetta
     * che l'esecuzione termini con il lancio di un'eccezione.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code EntrySet} ({@code testEntrySet}) non vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata al metodo deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsWithNull() {
        testEntrySet.contains(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che il metodo {@code contains(Object o)} sollevi una
     * {@code ClassCastException} se l'argomento fornito non è un'istanza di
     * {@link myAdapter.HEntry}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica la robustezza e la sicurezza dei tipi del metodo. Il set
     * contiene esclusivamente oggetti di tipo {@code HEntry}, quindi un tentativo
     * di ricerca con un tipo incompatibile deve fallire, come dall'interfaccia.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Viene invocato il metodo {@code testEntrySet.contains()} passando un oggetto
     * di tipo {@code Integer} (168), che non è un'istanza di {@code HEntry}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code EntrySet} ({@code testEntrySet}) non vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata al metodo deve sollevare una {@code ClassCastException}.
     */
    @Test(expected = ClassCastException.class)
    public void testContainsWithInvalidType() {
        testEntrySet.contains(168);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il corretto funzionamento del metodo {@code contains(Object o)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test è progettato per validare la logica fondamentale del metodo
     * {@code contains()}. Verifica che il metodo restituisca {@code true} se
     * l'entry specificata è presente nel set e {@code false} in caso contrario,
     * coprendo scenari con un set popolato e vuoto.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si creano due entry di test, una non presente e una presente nel set.<br>
     * 2. Si invoca {@code contains()} con l'entry non presente, verificando che il
     * risultato sia {@code false}.<br>
     * 3. Si invoca {@code contains()} con l'entry presente, verificando che il
     * risultato sia {@code true}.<br>
     * 4. Si svuota il set tramite {@code clear()}.<br>
     * 5. Si invoca nuovamente {@code contains()} con l'entry che prima era
     * presente, verificando che ora il risultato sia {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code EntrySet} ({@code testEntrySet}) popolata con 4
     * elementi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Al termine del test, il set {@code testEntrySet} risulta vuoto.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code contains()} deve restituire {@code false} per elementi non presenti e
     * {@code true} per elementi presenti. Dopo la pulizia, deve restituire
     * {@code false} per tutti gli elementi.
     */
    @Test
    public void testContains() {
        MapAdapter tmpMap = new MapAdapter();
        MapAdapter tmpMap2 = new MapAdapter();
        tmpMap.put(14, 65);
        tmpMap2.put(5, "noce");

        HSet tmpSet = tmpMap.entrySet();
        HSet tmpSet2 = tmpMap2.entrySet();

        HIterator it = tmpSet.iterator();
        HIterator it2 = tmpSet2.iterator();

        HEntry tmpEntry = (HEntry) it.next();
        HEntry tmpEntry2 = (HEntry) it2.next();

        assertFalse("Il set non dovrebbe contenere la entry [14, 65]", testEntrySet.contains(tmpEntry));
        assertTrue("Il set deve contenere la entry [5, \"noce\"]", testEntrySet.contains(tmpEntry2));

        testEntrySet.clear();

        assertFalse(testEntrySet.contains(tmpEntry2));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il metodo {@code iterator()} su un set di Entry.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test è fondamentale per assicurare che l'iteratore restituito sia
     * conforme all'interfaccia {@link HIterator} e interagisca correttamente con
     * il set sottostante. Si verifica la capacità di attraversare tutti gli
     * elementi, di rimuoverli durante l'iterazione e il comportamento corretto su
     * un set vuoto.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che l'oggetto restituito da {@code iterator()} sia un'istanza
     * di {@code HIterator}.<br>
     * 2. Si itera attraverso il set usando un ciclo
     * {@code while (it.hasNext())}.<br>
     * 3. All'interno del ciclo, per ogni elemento, si verifica la sua presenza nel
     * set, lo si rimuove con {@code it.remove()}, e si verifica la sua successiva
     * assenza.<br>
     * 4. Al termine del ciclo, si verifica che la dimensione del set sia 0.<br>
     * 5. Si crea un nuovo iteratore sul set ormai vuoto e si verifica che
     * {@code hasNext()} restituisca immediatamente {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un {@code EntrySet} popolato con 4 elementi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Il set {@code testEntrySet} è vuoto.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * L'iteratore deve attraversare e rimuovere con successo tutti gli elementi,
     * portando la dimensione del set a 0. Un iteratore su un set vuoto non deve
     * avere elementi.
     */
    @Test
    public void testIterator() {
        assertTrue(testEntrySet.iterator() instanceof HIterator);

        HIterator it = testEntrySet.iterator();
        while (it.hasNext()) {
            Object o = it.next();
            assertTrue(testEntrySet.contains(o));
            it.remove();
            assertFalse(testEntrySet.contains(o));
        }
        assertEquals(0, testEntrySet.size());

        it = testEntrySet.iterator();
        assertFalse(it.hasNext());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che il metodo {@code remove(Object o)} sollevi una
     * {@code NullPointerException} se l'oggetto da rimuovere è {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Simile al test per {@code contains(null)}, questo test garantisce che
     * l'operazione di rimozione gestisca correttamente gli input nulli come
     * richiesto dall'interfaccia delle collezioni.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Viene invocato il metodo {@code testEntrySet.remove(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code EntrySet} non vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Deve essere sollevata una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveWithNull() {
        testEntrySet.remove(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che il metodo {@code remove(Object o)} sollevi una
     * {@code ClassCastException} se l'oggetto fornito non è un'istanza di
     * {@link myAdapter.HEntry}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica la sicurezza dei tipi del metodo {@code remove()}.
     * Poiché il set può contenere solo {@code HEntry}, un tentativo di rimuovere
     * un oggetto di tipo incompatibile deve fallire.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca {@code testEntrySet.remove()} passando un oggetto {@code Integer}
     * (99).
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code EntrySet} non vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Deve essere sollevata una {@code ClassCastException}.
     */
    @Test(expected = ClassCastException.class)
    public void testRemoveWithInvalidType() {
        testEntrySet.remove(99);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il metodo {@code remove(Object o)}, verificando sia la rimozione di
     * un'entry presente sia il tentativo di rimozione di un'entry non presente.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test è cruciale per validare la logica di rimozione e la natura
     * "backed" della vista {@code EntrySet}. L'obiettivo è duplice: primo,
     * assicurare che la rimozione di un'entry esistente abbia successo (restituendo
     * {@code true}) e si propaghi correttamente alla mappa sottostante; secondo,
     * verificare che il tentativo di rimuovere un'entry non esistente fallisca
     * (restituendo {@code false}) senza alterare lo stato del set o della mappa.
     * Questo garantisce che il metodo {@code remove} sia conforme all'interfaccia
     * {@code Set}.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea un'istanza di {@code HEntry} che rappresenta una mappatura
     * sicuramente presente nel set (es. {@code [5, "noce"]}).<br>
     * 2. Si invoca {@code testEntrySet.remove()} con questa entry e si verifica che
     * il metodo restituisca {@code true}.<br>
     * 3. Si verifica che l'entry non sia più contenuta nel set.<br>
     * 4. Si crea una seconda istanza di {@code HEntry} che rappresenta una
     * mappatura non presente nel set (es. {@code [99, "non presente"]}).<br>
     * 5. Si invoca {@code testEntrySet.remove()} con questa seconda entry e si
     * verifica che il metodo restituisca {@code false}.<br>
     * 6. Infine, si verifica che la prima rimozione si sia propagata correttamente
     * alla mappa sottostante, controllando che la chiave 5 non sia più presente e
     * che la dimensione della mappa sia diminuita.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code EntrySet} ({@code testEntrySet}) popolata con 4
     * elementi, inclusa la mappatura {@code [5, "noce"]}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * L'entry {@code [5, "noce"]} è stata rimossa sia dal {@code testEntrySet} che
     * dalla mappa sottostante ({@code testMap}). La dimensione della mappa è 3.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La prima chiamata a {@code remove()} deve restituire {@code true}, mentre la
     * seconda deve restituire {@code false}. Il set non deve più contenere l'entry
     * {@code [5, "noce"]} e la mappa sottostante non deve più contenere la chiave
     * 5.
     */
    @Test
    public void testRemove() {
        // Creo un'entry che è sicuramente presente nel set
        MapAdapter tmp = new MapAdapter();
        tmp.put(5, "noce");
        HEntry presentEntry = (HEntry) tmp.entrySet().iterator().next();

        // Verifico che l'entry sia presente, la rimuovo e verifico il successo
        assertTrue("Il set dovrebbe contenere l'entry [5, \"noce\"]", testEntrySet.contains(presentEntry));
        assertTrue("La rimozione di un'entry presente dovrebbe restituire true", testEntrySet.remove(presentEntry));
        assertFalse("Il set non dovrebbe più contenere l'entry [5, \"noce\"]", testEntrySet.contains(presentEntry));

        // Creo un'entry non presente e tentare di rimuoverla
        tmp = new MapAdapter();
        tmp.put(99, "non presente"); // Una entry che non esiste in testMap
        HEntry notPresentEntry = (HEntry) tmp.entrySet().iterator().next();

        assertFalse("La rimozione di un'entry non presente dovrebbe restituire false",
                testEntrySet.remove(notPresentEntry));

        // Verifico che la rimozione si sia propagata alla mappa sottostante
        assertFalse("La mappa sottostante non dovrebbe più contenere la chiave 5", testMap.containsKey(5));
        assertEquals("La dimensione della mappa dovrebbe essere diminuita a 3", 3, testMap.size());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che il metodo {@code containsAll(HCollection c)} sollevi una
     * {@code NullPointerException} se la collezione fornita è {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test verifica che il metodo gestisca correttamente un argomento nullo,
     * come specificato dall'interfaccia {@code HCollection}.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testEntrySet.containsAll(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code EntrySet} non vuota.
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
    public void testContainsAllWithNullCollection() {
        testEntrySet.containsAll(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che {@code containsAll(HCollection c)} sollevi una
     * {@code ClassCastException} se la collezione fornita contiene elementi
     * che non sono istanze di {@link myAdapter.HEntry}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura la sicurezza dei tipi. Il confronto può avvenire solo
     * con una collezione di elementi compatibili ({@code HEntry}). Si usa la
     * vista dei valori della mappa ({@code testMap.values()}), che contiene
     * {@code String}, per simulare un tipo non valido.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca {@code testEntrySet.containsAll()} passando come argomento
     * {@code testMap.values()}, una collezione di tipo incompatibile.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code EntrySet} non vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code ClassCastException}.
     */
    @Test(expected = ClassCastException.class)
    public void testContainsAllWithInvalidTypeCollection() {
        testEntrySet.containsAll(testMap.values());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il metodo {@code containsAll(HCollection c)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test è disegnato per validare la logica di {@code containsAll} in tre
     * scenari chiave: il confronto con una collezione vuota (deve sempre essere
     * {@code true}), con un sottoinsieme proprio (deve essere {@code true}), e
     * con un insieme che contiene elementi non presenti (deve essere
     * {@code false}).
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che {@code containsAll} con una collezione vuota restituisca
     * {@code true}.<br>
     * 2. Si popola una collezione di supporto con un sottoinsieme degli elementi di
     * {@code testEntrySet} e si verifica che {@code containsAll} restituisca
     * {@code true}.<br>
     * 3. Si aggiunge un nuovo elemento (non presente in {@code testEntrySet}) alla
     * collezione di supporto.<br>
     * 4. Si verifica che ora {@code containsAll} restituisca {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code EntrySet} non vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code true} se tutti gli elementi della
     * collezione data sono presenti nel set, e {@code false} altrimenti.
     */
    @Test
    public void testContainsAll() {
        MapAdapter tmpMap = new MapAdapter();
        HSet tmpSet = tmpMap.entrySet();

        assertTrue(testEntrySet.containsAll(tmpSet));

        tmpMap.put(9, "mano");
        tmpMap.put(5, "noce");
        assertTrue(testEntrySet.containsAll(tmpSet));

        tmpMap.put(64, "");
        assertFalse(testEntrySet.containsAll(tmpSet));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che il metodo {@code removeAll(HCollection c)} sollevi una
     * {@code NullPointerException} se la collezione fornita è {@code null}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test verifica che il metodo gestisca correttamente un argomento nullo, in
     * linea con l'interfaccia {@code HCollection}.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Viene invocato il metodo {@code testEntrySet.removeAll(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code EntrySet} non vuota.
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
    public void testRemoveAllWithNullCollection() {
        testEntrySet.removeAll(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa che {@code removeAll(HCollection c)} sollevi una
     * {@code ClassCastException} se la collezione fornita contiene elementi
     * che non sono istanze di {@link myAdapter.HEntry}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura la sicurezza dei tipi del metodo. Il tentativo di
     * rimuovere elementi da una collezione di tipo incompatibile
     * (in questo caso, una collezione di {@code String}) deve fallire.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca {@code testEntrySet.removeAll()} passando come argomento la vista
     * dei valori {@code testMap.values()}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code EntrySet} non vuota.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del set rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code ClassCastException}.
     */
    @Test(expected = ClassCastException.class)
    public void testRemoveAllWithInvalidTypeCollection() {
        testEntrySet.removeAll(testMap.values());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il metodo {@code removeAll()} quando il set di partenza viene
     * modificato.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test valida lo scenario in cui {@code removeAll} trova e rimuove
     * elementi comuni. L'obiettivo è verificare che il metodo restituisca
     * {@code true} (indicando che il set è stato modificato) e che solo gli
     * elementi specificati vengano effettivamente rimossi.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione di supporto, {@code tmpSet}, contenente un
     * sottoinsieme degli elementi di {@code testEntrySet}.<br>
     * 2. Si invoca {@code testEntrySet.removeAll(tmpSet)} e si verifica che
     * restituisca {@code true}.<br>
     * 3. Si itera su {@code tmpSet} per verificare che nessuno dei suoi elementi
     * sia
     * più presente in {@code testEntrySet}.<br>
     * 4. Si verifica che l'unico elemento rimasto in {@code testEntrySet} sia
     * quello non presente in {@code tmpSet}.<br>
     * 5. Si controlla che la dimensione finale di {@code testEntrySet} sia 1.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un {@code EntrySet} popolato e una seconda collezione che ne condivide
     * alcuni elementi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Il {@code testEntrySet} contiene solo gli elementi che non erano presenti
     * nella collezione passata come argomento.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code true}, la dimensione del set deve ridursi
     * e gli elementi comuni devono essere stati rimossi.
     */
    @Test
    public void testRemoveAllWhenModified() {
        MapAdapter tmpMap = new MapAdapter(testMap);
        HSet tmpSet = tmpMap.entrySet();
        tmpMap.remove(5);

        assertTrue(testEntrySet.removeAll(tmpSet));

        HIterator it = tmpSet.iterator();
        while (it.hasNext()) {
            assertFalse(testEntrySet.contains(it.next()));
        }

        assertEquals(1, testEntrySet.size());
        Object[] o = testEntrySet.toArray();
        HEntry tmpEntry = (HEntry) o[0];

        assertTrue(tmpEntry.getKey().equals(5) && tmpEntry.getValue().equals("noce"));
        assertEquals(1, testEntrySet.size());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il metodo {@code removeAll()} quando il set di partenza non viene
     * modificato.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test copre il caso in cui non ci sono elementi in comune tra il set e
     * la collezione passata come argomento. Si deve verificare che il metodo
     * restituisca {@code false} e che il set originale rimanga inalterato.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione di supporto {@code tmpSet} senza elementi in comune
     * con {@code testEntrySet}.<br>
     * 2. Si salva lo stato di {@code testEntrySet} in un array.<br>
     * 3. Si invoca {@code testEntrySet.removeAll(tmpSet)} e si verifica che il
     * risultato sia {@code false}.<br>
     * 4. Si confronta lo stato attuale del set con quello salvato per assicurarsi
     * che non siano avvenute modifiche.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un {@code EntrySet} popolato e una seconda collezione senza elementi in
     * comune.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del {@code testEntrySet} rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code false} e il set non deve essere modificato.
     */
    @Test
    public void testRemoveAllWhenNotModified() {
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put(15, "");

        HSet tmpSet = tmpMap.entrySet();

        HIterator it = tmpSet.iterator();
        while (it.hasNext())
            assertFalse(testEntrySet.contains(it.next()));

        Object[] o = testEntrySet.toArray();

        assertFalse(testEntrySet.removeAll(tmpSet));
        for (int i = 0; i < o.length; i++)
            assertTrue(testEntrySet.contains(o[i]));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il metodo {@code equals()} confrontando set uguali e diversi.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test è cruciale per verificare la corretta implementazione
     * dell'interfaccia di {@code equals} per un {@code Set}. L'uguaglianza si basa
     * sulla classe, sulla dimensione e sul contenuto, indipendentemente
     * dall'ordine. Il test copre casi di uguaglianza, disuguaglianza per dimensione
     * e disuguaglianza per contenuto a parità di dimensione.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea un set {@code tmpSet} identico a {@code testEntrySet} e si
     * verifica che {@code equals()} restituisca {@code true}.<br>
     * 2. Si rimuove un elemento da {@code tmpSet}, rendendo le dimensioni diverse,
     * e si verifica che {@code equals()} restituisca {@code false}.<br>
     * 3. Si aggiunge un nuovo elemento a {@code tmpSet} per ripristinare la
     * parità di dimensione, ma con contenuto diverso, e si verifica che
     * {@code equals()} restituisca ancora {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un {@code EntrySet} popolato.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato di {@code testEntrySet} rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code equals()} deve restituire {@code true} solo se l'oggetto confrontato
     * è un {@code HSet} con la stessa dimensione e gli stessi elementi.
     */
    @Test
    public void testEqualsWithEqualSets() {
        MapAdapter tmpMap = new MapAdapter(testMap);
        HSet tmpSet = tmpMap.entrySet();

        assertEquals(tmpSet.getClass(), testEntrySet.getClass());
        assertEquals(testEntrySet.size(), tmpSet.size());
        assertTrue(testEntrySet.containsAll(tmpSet) && tmpSet.containsAll(testEntrySet));
        assertTrue(testEntrySet.equals(tmpSet));

        tmpMap.remove(5);

        assertNotEquals(testEntrySet.size(), tmpSet.size());
        assertFalse(testEntrySet.equals(tmpSet));

        tmpMap.put(6, "noce");

        assertEquals(testEntrySet.size(), tmpSet.size());
        assertFalse(testEntrySet.equals(tmpSet));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa il metodo {@code equals()} confrontando un HSet con un oggetto di
     * tipo diverso.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica un requisito fondamentale dell'interfaccia di
     * {@code equals()}: deve restituire {@code false} se l'oggetto di confronto
     * non è dello stesso tipo (o di un tipo compatibile). Viene testato sia con
     * una collezione di tipo diverso ({@code HCollection}) che con un oggetto
     * completamente estraneo ({@code String}).
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una {@code HCollection} che, pur contenendo gli stessi elementi
     * del set, è di classe diversa. Si verifica che {@code equals()} restituisca
     * {@code false}.<br>
     * 2. Si invoca {@code equals()} passando un oggetto di tipo {@code String} e si
     * verifica che il risultato sia {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un {@code EntrySet} popolato.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato del {@code testEntrySet} rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code equals()} deve restituire sempre {@code false} se l'argomento
     * non è un oggetto di tipo {@code HSet}.
     */
    @Test
    public void testEqualsWithDifferentObjects() {
        MapAdapter tmpMap = new MapAdapter();
        Object[] o = testEntrySet.toArray();
        for (int i = 0; i < o.length; i++)
            tmpMap.put(((HEntry) o[i]).getKey(), ((HEntry) o[i]).getValue());

        HCollection tmpCollection = tmpMap.values();

        assertNotEquals(testEntrySet.getClass(), tmpCollection.getClass());
        assertFalse(testEntrySet.equals(tmpCollection));

        assertFalse(testEntrySet.equals("lattuga"));
    }
}