package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import myAdapter.*;

/**
 * <b>Summary</b>
 * <p>
 * Suite di test, o Test Case, dedicata a validare la classe che implementa una
 * singola mappatura (coppia chiave-valore), ovvero l'entry di una mappa, che
 * implementa l'interfaccia {@link myAdapter.HEntry}.
 * <p>
 * L'obiettivo è collaudare la corretta operatività dei metodi di un'istanza di
 * {@code Entry}, focalizzandosi sui metodi di accesso ({@code getKey},
 * {@code getValue}), di modifica ({@code setValue}) e di confronto
 * ({@code equals}, {@code hashCode}).
 * <p>
 * <b>Test Case Design</b>
 * <p>
 * La motivazione di questo test case è garantire che le singole entry, quali
 * unità fondamentali di una mappa, funzionino come specificato dall'interfaccia
 * di {@code java.util.Map.Entry} (versione 1.4.2). È di cruciale importanza
 * verificare il corretto "backing": una modifica effettuata su un'istanza di
 * {@code Entry} tramite il metodo {@code setValue} deve riflettersi
 * immediatamente e correttamente sulla mappa contenitore. I test sono stati
 * progettati per isolare e verificare ogni metodo, coprendo scenari di
 * funzionamento standard, casi limite e la gestione delle eccezioni.
 */
public class TestMapEntry {

    private MapAdapter testMap = new MapAdapter();
    private HSet testEntrySet = testMap.entrySet();

    /**
     * Questo costruttore è vuoto e serve a soddisfare i requisiti
     * dello strumento Javadoc, evitando warning di documentazione.
     */
    public TestMapEntry() {
        // Costruttore intenzionalmente lasciato vuoto.
    }

    /**
     * Prepara l'ambiente di test per ogni singolo caso di prova.
     * Popola la mappa di riferimento da cui si otterranno le istanze di
     * {@code Entry} da testare.
     */
    @Before
    public void setUp() {
        // Inizializzazione e popolamento della mappa
        testMap.put(5, "noce");
        testMap.put(9, "mano");
        testMap.put(1, "sasso");
        testMap.put(0, "pippo");
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code getKey()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che il metodo restituisca correttamente la chiave
     * associata a un'entry senza alterare lo stato dell'entry stessa o della
     * mappa sottostante. È un test fondamentale per garantire l'accesso in sola
     * lettura alla componente chiave della mappatura.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene un'istanza di {@code HEntry} da una mappa di test.<br>
     * 2. Si invoca il metodo {@code getKey()} sull'entry.<br>
     * 3. Si verifica che la chiave restituita sia quella attesa.<br>
     * 4. Si controlla che lo stato della mappa non sia stato modificato
     * dall'operazione.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code HEntry} ottenuta da una mappa popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato dell'entry e della mappa sottostante rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code getKey()} deve restituire la chiave corretta associata
     * all'entry.
     */
    @Test
    public void testEntryGetKey() {
        // Aggiunta di un elemento noto per il test
        MapAdapter testMap = new MapAdapter();
        testMap.put(5, "");
        testEntrySet = testMap.entrySet();
        // Ottenimento dell'oggetto Entry tramite l'iteratore
        HIterator it = testEntrySet.iterator();
        HEntry tmpEntry = (HEntry) it.next();
        // Verifica che la chiave dell'Entry sia il valore atteso (5)
        assertEquals(5, tmpEntry.getKey());

        // Controllo che la dimensione della mappa sia ancora 1
        assertEquals(1, testMap.size());
        // Controllo che la mappa contenga ancora la coppia [5, ""]
        assertTrue(testMap.containsKey(5) && testMap.get(5).equals(""));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code getValue()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che il metodo restituisca correttamente il valore
     * associato a un'entry senza alterarne lo stato. È il test speculare a
     * {@code testEntryGetKey} per la componente valore della mappatura.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene un'istanza di {@code HEntry} da una mappa di test.<br>
     * 2. Si invoca il metodo {@code getValue()} sull'entry.<br>
     * 3. Si verifica che il valore restituito sia quello atteso.<br>
     * 4. Si controlla che lo stato della mappa non sia stato modificato.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code HEntry} ottenuta da una mappa popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato dell'entry e della mappa sottostante rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code getValue()} deve restituire il valore corretto associato
     * all'entry.
     */
    @Test
    public void testEntryGetValue() {
        // Creazione di una nuova mappa con un elemento
        testMap = new MapAdapter();
        testMap.put(5, "pinguino");
        testEntrySet = testMap.entrySet();
        // Uso dell'iteratore per recuperare l'oggetto Entry
        HIterator it = testEntrySet.iterator();
        HEntry tmpEntry = (HEntry) it.next();

        // Controllo che il valore dell'Entry sia "pinguino"
        assertEquals("pinguino", tmpEntry.getValue());

        // Verifica che la dimensione della mappa sia rimasta 1
        assertEquals(1, testMap.size());
        // Verifica che la mappa contenga ancora la coppia [5, "pinguino"]
        assertTrue(testMap.containsKey(5) && testMap.get(5).equals("pinguino"));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa {@code getValue()} dopo la rimozione dell'entry dalla mappa.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica un caso limite importante: il comportamento di
     * un'istanza di {@code Entry} dopo che la mappatura corrispondente è stata
     * rimossa dalla mappa. L'entry diventa "invalida" e ci si aspetta che
     * {@code getValue()} restituisca {@code null}.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene un'istanza di {@code HEntry} tramite un iteratore.<br>
     * 2. Si rimuove la mappatura dalla mappa usando {@code iterator.remove()}.<br>
     * 3. Si invoca {@code getValue()} sull'istanza di entry precedentemente
     * ottenuta.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code HEntry} valida e l'iteratore che l'ha generata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa sottostante non contiene più l'entry. L'istanza di {@code Entry}
     * è "staccata" dalla mappa.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code getValue()} sull'entry rimossa deve restituire {@code null}.
     */
    @Test
    public void testGetValueAfterRemove() {
        // Creazione di una nuova mappa con un elemento
        testMap = new MapAdapter();
        testMap.put(5, "sedano");
        testEntrySet = testMap.entrySet();

        // Uso dell'iteratore per ottenere l'oggetto Entry
        HIterator it = testEntrySet.iterator();
        HEntry tmpEntry = (HEntry) it.next();

        // Rimozione della mappatura tramite l'iteratore
        it.remove();

        // Verifica che il metodo getValue() sull'Entry ora restituisca null
        assertEquals(null, tmpEntry.getValue());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code setValue()} sollevi {@code NullPointerException} se si
     * tenta di impostare un valore nullo.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che l'implementazione rispetti l'interfaccia che
     * vieta valori nulli, lanciando un'eccezione per segnalare un uso non
     * valido del metodo e prevenire stati inconsistenti.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene un'istanza di {@code HEntry}.<br>
     * 2. Si invoca {@code setValue(null)} su di essa.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code HEntry} valida.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato dell'entry e della mappa sottostante rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata a {@code setValue(null)} deve sollevare una
     * {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testSetValueWithNull() {
        HIterator it = testEntrySet.iterator();
        HEntry tmpEntry = (HEntry) it.next();
        tmpEntry.setValue(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code setValue()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo è un test cruciale per il meccanismo di "backing". L'obiettivo è
     * assicurare che {@code setValue} non solo aggiorni il valore all'interno
     * dell'oggetto entry, ma che la modifica si propaghi correttamente alla mappa
     * sottostante. Si verifica anche che il metodo restituisca il vecchio valore,
     * come da interfaccia.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene un'istanza di {@code HEntry}.<br>
     * 2. Si invoca {@code setValue()} con un nuovo valore.<br>
     * 3. Si verifica che il valore restituito da {@code setValue} sia il valore
     * *precedente*.<br>
     * 4. Si invoca {@code getValue()} sulla stessa entry per verificare che ora
     * contenga il *nuovo* valore.<br>
     * 5. Si interroga direttamente la mappa per assicurarsi che il valore
     * associato alla chiave sia stato aggiornato.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code HEntry} valida.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Il valore dell'entry e della mappatura corrispondente nella mappa sono
     * stati aggiornati.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code setValue} deve restituire il vecchio valore, e la modifica deve
     * essere visibile sia sull'entry che sulla mappa.
     */
    @Test
    public void testEntrySetValue() {
        // Creazione di una nuova mappa e inserimento di un elemento
        testMap = new MapAdapter();
        testMap.put(5, "pinguino");
        testEntrySet = testMap.entrySet();
        // Ottenimento dell'oggetto Entry tramite l'iteratore
        HIterator it = testEntrySet.iterator();
        HEntry tmpEntry = (HEntry) it.next();

        // Controllo che il valore iniziale sia "pinguino"
        assertEquals("pinguino", tmpEntry.getValue());

        // Modifica del valore dell'entry, verificando che il vecchio valore venga
        // restituito
        assertEquals("pinguino", tmpEntry.setValue("economy+"));

        // Controllo che il nuovo valore dell'Entry sia "economy+"
        assertEquals("economy+", tmpEntry.getValue());

        // Controllo che anche la mappa sia stata aggiornata di conseguenza
        assertTrue(testMap.containsKey(5) && testMap.get(5).equals("economy+"));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code equals(Object)} tra due oggetti {@code HEntry}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test valida che {@code equals} rispetti l'interfaccia {@code Map.Entry}:
     * due entry sono uguali se e solo se hanno la stessa chiave e lo stesso valore.
     * Per garantire una copertura completa, vengono testati sistematicamente
     * quattro scenari distinti: entry identiche, entry con la stessa chiave ma
     * valore diverso, entry con chiave diversa ma stesso valore, e infine entry con
     * chiavi e valori entrambi diversi. Questo approccio assicura che ogni
     * condizione del metodo equals sia verificata.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Vengono create due mappe identiche e si estraggono le prime entry da
     * ciascuna. Si verifica che le due entry siano uguali tramite
     * {@code assertTrue(entry1.equals(entry2))}.<br>
     * 2. Il valore della prima entry viene modificato. Si verifica che le entry
     * non siano più uguali, controllando che
     * {@code assertFalse(entry1.equals(entry2))}
     * restituisca true.<br>
     * 3. La prima entry viene fatta avanzare all'elemento successivo del suo set,
     * risultando in una entry con chiave e valore diversi rispetto alla seconda.
     * Si verifica nuovamente che le entry non siano uguali.<br>
     * 4. Il valore della prima entry (ora con chiave diversa) viene impostato
     * uguale al valore della seconda. Si verifica che, nonostante i valori
     * identici, le entry non siano uguali a causa delle chiavi diverse.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Due istanze di {@code MapAdapter} popolate con gli stessi dati.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato delle entry e delle mappe di supporto viene alterato durante il
     * test per creare i vari scenari di confronto necessari.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code equals} deve restituire {@code true} solo nel primo
     * scenario (entry identiche) e {@code false} in tutti gli altri tre scenari
     * di confronto.
     */
    @Test
    public void testEntryEquals() {
        // Creazione di una mappa di supporto, inizialmente
        // identica a testMap, e del suo EntrySet
        MapAdapter tmpMap = new MapAdapter(testMap);
        HSet tmpSet = tmpMap.entrySet();

        // Creazione di due iteratori per le due viste identiche
        HIterator itSet = tmpSet.iterator();
        HIterator itEntry = testEntrySet.iterator();

        // Estrazione delle due entry da confrontare
        HEntry tmpEntry1 = (HEntry) itSet.next();
        HEntry tmpEntry2 = (HEntry) itEntry.next();

        // Confronto delle due Entry appena ottenute
        assertEquals(tmpEntry1.getKey(), tmpEntry2.getKey());
        assertEquals(tmpEntry1.getValue(), tmpEntry2.getValue());
        assertTrue(tmpEntry1.equals(tmpEntry2));

        // Modifica del valore di tmpEntry1 per renderla diversa da tmpEntry2
        tmpEntry1.setValue(999);

        // Nuovo confronto: stessa chiave ma valore diverso, ci si aspetta false
        assertEquals(tmpEntry1.getKey(), tmpEntry2.getKey());
        assertNotEquals(tmpEntry1.getValue(), tmpEntry2.getValue());
        assertFalse(tmpEntry1.equals(tmpEntry2));

        // Si fa puntare tmpEntry1 all'Entry successiva, cambiando così la sua chiave
        tmpEntry1 = (HEntry) itSet.next();

        // Nuovo confronto: chiavi e valori diversi, ci si aspetta false
        assertNotEquals(tmpEntry1.getKey(), tmpEntry2.getKey());
        assertNotEquals(tmpEntry1.getValue(), tmpEntry2.getValue());
        assertFalse(tmpEntry1.equals(tmpEntry2));

        // Si imposta il valore di tmpEntry1 uguale a quello di tmpEntry2
        tmpEntry1.setValue(tmpEntry2.getValue());
        // Nuovo confronto: stesso valore ma chiave diversa, ci si aspetta false
        assertNotEquals(tmpEntry1.getKey(), tmpEntry2.getKey());
        assertEquals(tmpEntry1.getValue(), tmpEntry2.getValue());
        assertFalse(tmpEntry1.equals(tmpEntry2));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica il comportamento del metodo {@code setValue()} quando invocato su
     * un'istanza di {@code HEntry} la cui mappatura corrispondente è stata
     * precedentemente rimossa dalla mappa di supporto.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * La specifica J2SE 1.4.2 lascia "indefinito" il comportamento di
     * {@code setValue()} su un'entry rimossa. Questo test è stato quindi
     * progettato per validare e documentare il comportamento specifico scelto per
     * questa implementazione. L'obiettivo è confermare che, data l'implementazione
     * che si affida incondizionatamente a {@code parentMap.put()}, una chiamata a
     * {@code setValue()} su un'entry "staccata" di fatto la "resuscita",
     * reinserendola nella mappa sottostante. Questo test garantisce che tale
     * comportamento sia riproducibile e documentato, come richiesto dalle
     * direttive del progetto.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene un'istanza di {@code HEntry} (es. quella con chiave 9) dalla
     * mappa di test.<br>
     * 2. Si rimuove la mappatura corrispondente direttamente dalla mappa (usando
     * {@code testMap.remove(9)}), "staccando" di fatto l'istanza dell'entry.<br>
     * 3. Si verifica che la chiave non sia più presente nella mappa.<br>
     * 4. Si invoca {@code setValue()} sull'istanza di entry precedentemente
     * ottenuta, passandole un nuovo valore.<br>
     * 5. Si verifica che la chiave 9 sia stata reinserita nella mappa e che il suo
     * valore associato sia quello nuovo, passato a {@code setValue()}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code HEntry} valida, ottenuta da una mappa popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La mappa sottostante viene prima modificata tramite la rimozione della
     * chiave, e poi nuovamente modificata dal reinserimento della coppia con il
     * nuovo valore, causato dalla chiamata a {@code setValue()} sull'entry
     * staccata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata a {@code setValue()} sull'entry staccata deve reinserire la
     * coppia chiave-valore nella mappa sottostante. La mappa deve quindi contenere
     * la chiave dell'entry con il nuovo valore specificato.
     */
    @Test
    public void testSetValueOnDetachedEntry() {
        // Otteniamo un'entry, ad esempio quella con chiave 9
        HIterator it = testEntrySet.iterator();
        HEntry entry = null;
        while (it.hasNext()) {
            HEntry current = (HEntry) it.next();
            if (current.getKey().equals(9)) {
                entry = current;
                break;
            }
        }

        // Assicuriamoci di aver trovato l'entry giusta
        assertEquals(9, entry.getKey());
        assertEquals("mano", entry.getValue());

        // Rimuoviamo la mappatura direttamente dalla mappa per "staccare" l'entry
        assertTrue(testMap.containsKey(9));
        testMap.remove(9);
        assertFalse("La mappa non dovrebbe più contenere la chiave 9", testMap.containsKey(9));

        // Chiamiamo setValue() sull'entry staccata
        entry.setValue("nuovo valore");

        // Verifichiamo che l'entry sia stata reinserita nella mappa.
        assertTrue("La mappa dovrebbe di nuovo contenere la chiave 9", testMap.containsKey(9));
        assertEquals("Il valore associato alla chiave 9 dovrebbe essere quello nuovo", "nuovo valore",
                testMap.get(9));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del metodo {@code equals(Object)} con un oggetto di tipo diverso.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che {@code equals} sia robusto e restituisca sempre
     * {@code false} quando l'oggetto di confronto non è un'istanza di
     * {@code HEntry}, come previsto dall'interfaccia generale del metodo
     * {@code equals}.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene un'istanza di {@code HEntry}.<br>
     * 2. La si confronta con un oggetto di tipo diverso (in questo caso, una
     * mappa).
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code HEntry}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Nessuna modifica di stato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata a {@code equals} deve restituire {@code false}.
     */
    @Test
    public void testEqualsWithWrongType() {
        HIterator it = testEntrySet.iterator();
        HEntry tmpEntry = (HEntry) it.next();
        // Confronto tra l'Entry e la mappa, il risultato atteso è false
        assertFalse(tmpEntry.equals(testMap));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code hashCode()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test valida che l'implementazione di {@code hashCode} rispetti
     * l'interfaccia:<br>
     * 1) l'hashCode di un'entry deve essere calcolato come
     * {@code key.hashCode() ^ value.hashCode()};<br>
     * 2) se due entry sono uguali secondo {@code equals}, devono avere lo stesso
     * hashCode;<br>
     * 3) se il valore di un'entry cambia, anche il suo hashCode deve cambiare.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si calcola l'hashCode atteso per un'entry e si verifica che corrisponda a
     * quello restituito dal metodo.<br>
     * 2. Si modifica il valore dell'entry con {@code setValue} e si verifica che
     * l'hashCode sia cambiato.<br>
     * 3. Si crea una seconda entry uguale alla prima (modificata) e si verifica
     * che i loro hashCode siano identici.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Un'istanza di {@code HEntry}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Il valore dell'entry viene modificato durante il test.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * L'hashCode calcolato deve essere corretto e coerente con il metodo
     * {@code equals}.
     */
    @Test
    public void testEntryHashCode() {
        // Ottenimento dell'oggetto Entry tramite l'iteratore
        HIterator it = testEntrySet.iterator();
        HEntry tmpEntry1 = (HEntry) it.next();
        // Verifica che l'hashCode dell'Entry sia l'OR esclusivo (XOR) della mappatura
        int keyHash = tmpEntry1.getKey().hashCode();
        int valueHash = tmpEntry1.getValue().hashCode();
        assertEquals(valueHash ^ keyHash, tmpEntry1.hashCode());

        // Memorizzazione dell'hashCode attuale prima di modificare l'Entry
        int entry1Hash = tmpEntry1.hashCode();
        tmpEntry1.setValue("Africa");

        // Controllo che l'hashCode sia stato aggiornato
        assertNotEquals(entry1Hash, tmpEntry1.hashCode());

        // Verifica che due Entry uguali condividano lo stesso hashCode
        // Per ottenere un'altra istanza della stessa entry, la recupero di nuovo
        MapAdapter mapCopy = new MapAdapter();
        mapCopy.put(tmpEntry1.getKey(), tmpEntry1.getValue());
        HEntry tmpEntry2 = (HEntry) mapCopy.entrySet().iterator().next();

        assertEquals(tmpEntry2, tmpEntry1);
        assertEquals(tmpEntry2.hashCode(), tmpEntry1.hashCode());
    }
}