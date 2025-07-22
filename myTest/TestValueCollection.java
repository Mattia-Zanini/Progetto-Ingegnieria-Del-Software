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
 * Suite di test, o Test Case, dedicata alla classe che implementa la vista dei
 * valori di una mappa ({@code ValueCollection}), la quale implementa
 * l'interfaccia {@link myAdapter.HCollection}.
 * <p>
 * L'obiettivo è verificare la corretta funzionalità di tutti i metodi
 * pubblici della classe {@code ValueCollection}, inclusi quelli per
 * l'interrogazione ({@code size}, {@code contains}), la modifica
 * ({@code remove}, {@code clear}, {@code removeAll}, {@code retainAll}),
 * la conversione ({@code toArray}) e l'iterazione. Vengono testate anche le
 * operazioni non supportate ({@code add}, {@code addAll}).
 * <p>
 * <b>Test Case Design</b>
 * <p>
 * La motivazione di questo test case è garantire che la vista dei valori sia
 * un'implementazione robusta e pienamente conforme all'interfaccia
 * {@code java.util.Collection} (versione 1.4.2). Un aspetto fondamentale della
 * progettazione dei test è la verifica del meccanismo di "backing": le
 * modifiche effettuate sulla {@code ValueCollection} (es. una rimozione) devono
 * riflettersi immediatamente e correttamente sulla mappa ({@code MapAdapter})
 * originale. I test coprono scenari di funzionamento standard, casi limite
 * (collezioni vuote, valori duplicati), gestione delle eccezioni e interazione
 * con altre collezioni.
 */
public class TestValueCollection {
    private MapAdapter testMap = new MapAdapter();
    private HCollection testCollection = null;

    /**
     * Questo costruttore è vuoto e serve a soddisfare i requisiti
     * dello strumento Javadoc, evitando warning di documentazione.
     */
    public TestValueCollection() {
        // Costruttore intenzionalmente lasciato vuoto.
    }

    /**
     * Prepara l'ambiente di test prima di ogni esecuzione. Popola la mappa di test
     * con alcuni elementi per creare la collezione di valori che verrà usata nei
     * test.
     */
    @Before
    public void setUp() {
        // Popola la mappa.
        testMap.put(5, "noce");
        testMap.put(9, "mano");
        testMap.put(1, "sasso");
        testMap.put(0, "pippo");

        testCollection = testMap.values();
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code size()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test è progettato per assicurare che il metodo {@code size()} rifletta
     * accuratamente il numero di valori presenti nella collezione in diverse fasi
     * del suo ciclo di vita: dopo l'inizializzazione e dopo lo svuotamento
     * completo.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che la dimensione iniziale sia 4 e corrisponda a quella
     * della mappa sottostante.<br>
     * 2. Si svuota la collezione con {@code clear()}.<br>
     * 3. Si verifica che la dimensione finale sia 0.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} ottenuta da una mappa con 4 elementi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code size()} deve restituire 4 all'inizio e 0 dopo la chiamata
     * a {@code clear()}.
     */
    @Test
    public void testSize() {
        // Si verifica che la collezione di valori abbia
        // la stessa dimensione della mappa.
        assertEquals(
                "Il numero di elementi nella collezione deve essere uguale al numero di elementi nella mappa",
                testCollection.size(), testMap.size());
        assertEquals(4, testCollection.size());

        // Si svuota la collezione.
        testCollection.clear();

        // Si controlla che la dimensione della collezione sia ora 0.
        assertTrue(testCollection.size() == 0);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code isEmpty()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che {@code isEmpty()} restituisca il valore booleano
     * corretto a seconda che la collezione contenga o meno elementi, validando il
     * suo stato dopo l'inizializzazione e dopo lo svuotamento.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che {@code isEmpty()} restituisca {@code false} su una
     * collezione appena popolata.<br>
     * 2. Si svuota la collezione con {@code clear()}.<br>
     * 3. Si verifica che {@code isEmpty()} restituisca {@code true} sulla
     * collezione vuota.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code isEmpty()} deve restituire {@code false} prima di
     * {@code clear()} e {@code true} dopo.
     */
    @Test
    public void testIsEmpty() {
        // Si verifica che la collezione non sia vuota, avendo dimensione 4.
        assertFalse("La collezione non dovrebbe essere vuota", testCollection.isEmpty());
        assertEquals(4, testCollection.size());

        // Si svuota la collezione.
        testCollection.clear();

        // Si verifica che la collezione sia ora vuota, con dimensione 0.
        assertTrue("La collezione dovrebbe essere vuota", testCollection.isEmpty());
        assertEquals(0, testCollection.size());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code contains(null)} sollevi una
     * {@code NullPointerException}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che l'implementazione rispetti l'interfaccia, che vieta
     * valori nulli, lanciando un'eccezione per segnalare un uso non valido del
     * metodo.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testCollection.contains(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsWithNull() {
        // Si testa il comportamento con un argomento null, attendendosi una
        // NullPointerException.
        testCollection.contains(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code contains()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test valida la capacità del metodo di identificare la presenza o
     * l'assenza di un valore, coprendo i casi di valore presente, valore assente
     * e ricerca in una collezione vuota.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si cerca un valore esistente ("mano") e si verifica che il risultato sia
     * {@code true}.<br>
     * 2. Si cerca un valore non esistente ("squid") e si verifica che il risultato
     * sia {@code false}.<br>
     * 3. Si svuota la collezione e si cerca un oggetto qualsiasi, verificando che
     * il risultato sia {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code true} per il valore presente e
     * {@code false} negli altri due casi.
     */
    @Test
    public void testContains() {
        // Si controlla che l'elemento "mano" sia presente nella collezione di valori.
        assertTrue("La collezione dovrebbe contenere l'elemento \"mano\"", testCollection.contains("mano"));

        // Si verifica che un elemento non presente ("squid") non venga trovato.
        assertFalse("La collezione non dovrebbe contenere l'elemento \"squid\"", testCollection.contains("squid"));

        // Si svuota la collezione.
        testCollection.clear();
        // Si verifica che, su una collezione vuota, il metodo restituisca sempre false.
        assertFalse("Una collezione vuota non può contenere alcun oggetto", testCollection.contains(new Object()));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code containsAll(null)} sollevi {@code NullPointerException}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura la corretta gestione di input non validi per il metodo di
     * verifica di contenimento massivo.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca {@code testCollection.containsAll(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testContainsAllWithNull() {
        testCollection.containsAll(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del funzionamento del metodo {@code containsAll(HCollection)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test valida la logica di {@code containsAll} in tre scenari: confronto
     * con una collezione vuota (deve essere {@code true}), con un sottoinsieme
     * proprio ({@code true}), e con un insieme che contiene elementi non presenti
     * ({@code false}).
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che {@code containsAll} con una collezione vuota restituisca
     * {@code true}.<br>
     * 2. Si popola una collezione di supporto con un sottoinsieme dei valori e si
     * verifica che {@code containsAll} restituisca {@code true}.<br>
     * 3. Si aggiunge un nuovo valore alla collezione di supporto e si verifica che
     * ora {@code containsAll} restituisca {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code true} se la collezione contiene tutti gli
     * elementi della collezione argomento, {@code false} altrimenti.
     */
    @Test
    public void testContainsAll() {
        // Si crea una nuova mappa e la relativa collezione.
        MapAdapter tmpMap = new MapAdapter();
        HCollection tmpCollection = tmpMap.values();

        // Si controlla che testCollection contenga la collezione vuota 'tmpCollection'.
        assertTrue(testCollection.containsAll(tmpCollection));

        // Si popola la seconda mappa con valori presenti nella prima.
        tmpMap.put(9, "noce");
        tmpMap.put(5, "pippo");
        // Si controlla che testCollection contenga tutti i valori di 'tmpCollection'.
        assertTrue(testCollection.containsAll(tmpCollection));

        // Si aggiunge a tmpMap un elemento che non appartiene a testMap.
        tmpMap.put(64, "low");
        // Ora 'tmpCollection' ha un elemento non presente in testCollection.

        // Si controlla che testCollection non contenga più tutti gli elementi di
        // 'tmpCollection'.
        assertFalse(testCollection.containsAll(tmpCollection));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che il metodo {@code iterator()} restituisca un iteratore
     * funzionante.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che l'iteratore restituito sia conforme all'interfaccia
     * {@link HIterator} e interagisca correttamente con la collezione
     * sottostante, in particolare per l'operazione di rimozione, che deve
     * propagarsi alla mappa.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si verifica che l'oggetto restituito sia un'istanza di
     * {@code HIterator}.<br>
     * 2. Si itera attraverso la collezione; per ogni elemento, si verifica la sua
     * presenza, lo si rimuove con {@code it.remove()}, e si verifica la sua
     * successiva assenza.<br>
     * 3. Al termine del ciclo, si verifica che la dimensione della collezione sia
     * 0.<br>
     * 4. Si crea un nuovo iteratore sulla collezione vuota e si verifica che
     * {@code hasNext()} sia {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione e la mappa sottostante sono vuote.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * L'iteratore deve attraversare e rimuovere tutti gli elementi, portando la
     * dimensione della collezione a 0.
     */
    @Test
    public void testIterator() {
        // Si assicura che l'oggetto restituito sia un'istanza di HIterator.
        assertTrue(testCollection.iterator() instanceof HIterator);

        // Si controlla il funzionamento dell'iteratore.
        HIterator it = testCollection.iterator();
        while (it.hasNext()) {
            Object tmp = it.next();
            assertTrue(testCollection.contains(tmp));
            it.remove();
            assertFalse(testCollection.contains(tmp));
        }
        assertTrue(testCollection.size() == 0);

        // Ora la collezione è vuota; si verifica
        // che un nuovo iteratore non abbia elementi successivi.
        it = testCollection.iterator();
        assertFalse(it.hasNext());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code toArray()} converta correttamente la collezione in
     * array.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test valida la capacità del metodo di creare un array contenente
     * tutti gli elementi della collezione, rispettando la dimensione e il
     * contenuto. Viene testato sia su una collezione popolata che su una vuota.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si invoca {@code toArray()} e si verifica che l'array restituito abbia la
     * stessa dimensione della collezione.<br>
     * 2. Si itera sull'array e si verifica che ogni suo elemento sia contenuto
     * nella collezione originale.<br>
     * 3. Si svuota la collezione e si invoca di nuovo {@code toArray()},
     * verificando che l'array restituito abbia lunghezza 0.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * L'array restituito deve riflettere fedelmente il contenuto e la dimensione
     * della collezione in entrambi gli scenari.
     */
    @Test
    public void testToArray() {
        Object[] tmp = testCollection.toArray();
        // Si controlla che la collezione e l'array abbiano la stessa dimensione.
        assertEquals(testCollection.size(), testCollection.toArray().length);
        // Si controlla che la collezione e l'array abbiano gli stessi elementi.
        for (int i = 0; i < tmp.length; i++) {
            assertTrue(testCollection.contains(tmp[i]));
        }

        // Si svuota la collezione.
        testCollection.clear();
        // Si controlla che la dimensione dell'array restituito da toArray() sia 0.
        assertTrue(testCollection.toArray().length == 0);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code toArray(Object[] a)} sollevi
     * {@code NullPointerException} con un array nullo.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che il metodo gestisca correttamente un argomento nullo,
     * come previsto dall'interfaccia.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testCollection.toArray(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testToArrayWithNull() {
        testCollection.toArray(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che l'array restituito da {@code toArray()} sia una copia sicura.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che la modifica dell'array restituito da
     * {@code toArray()} non influenzi la collezione originale, confermando che il
     * metodo crea una copia degli elementi e non restituisce riferimenti interni.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si ottiene un array dalla collezione.<br>
     * 2. Si modifica un elemento all'interno dell'array.<br>
     * 3. Si verifica che la collezione originale non contenga il nuovo valore
     * modificato.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La collezione non deve essere alterata dalla modifica dell'array.
     */
    @Test
    public void testToArrayDoesNotReturnReference() {
        // Si controlla che l'array non abbia riferimenti agli elementi della
        // collezione.
        Object[] tmp = testCollection.toArray();
        // Si cambia il valore del primo elemento di a.
        tmp[0] = "leopardo";
        // La collezione testCollection non deve essere stata modificata.
        assertFalse(testCollection.contains(tmp[0]));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code toArray(Object[] a)} sollevi {@code ArrayStoreException}
     * con un tipo di array incompatibile.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test valida la sicurezza dei tipi del metodo, assicurando che
     * l'operazione fallisca se si tenta di inserire gli elementi della
     * collezione ({@code String}) in un array di tipo non compatibile (es.
     * {@code Integer}).
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca {@code toArray} passando un array di {@code Integer} come
     * argomento.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una collezione di valori di tipo {@code String}.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione e l'array rimangono invariati.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code ArrayStoreException}.
     */
    @Test(expected = ArrayStoreException.class)
    public void testToArrayWithIncompatibleType() {
        Object[] tmp = new Integer[9];
        // testCollection contiene valori di tipo String, non possono essere inseriti in
        // un array di Integer.
        testCollection.toArray(tmp);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa {@code toArray(Object[] a)} con un array di dimensione sufficiente.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test verifica che, se l'array fornito è abbastanza grande, venga
     * riutilizzato per ospitare gli elementi della collezione e che le posizioni
     * in eccesso vengano riempite con {@code null}, come dall'interfaccia.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea un array più grande della collezione.<br>
     * 2. Si invoca {@code toArray} con questo array e si verifica che venga
     * restituito lo stesso oggetto array.<br>
     * 3. Si itera sull'array per verificare che contenga gli elementi della
     * collezione seguiti da {@code null}.<br>
     * 4. Si svuota la collezione e si ripete l'operazione, verificando che ora
     * l'array contenga solo {@code null}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata e un array di dimensione maggiore.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * L'array fornito deve essere popolato correttamente in entrambi gli scenari.
     */
    @Test
    public void testToArrayWithSufficientlyLargeArray() {
        // Si verifica che se l'array fornito è abbastanza grande, venga popolato e
        // restituito.
        Object[] tmp = new Object[10];
        assertTrue(tmp.equals(testCollection.toArray(tmp)));
        // Si controlla che 'tmp' contenga tutti gli elementi di testCollection e null
        // nelle
        // posizioni successive.
        for (int i = 0; i < tmp.length; i++) {
            if (i < testCollection.size()) {
                assertTrue(testCollection.contains(tmp[i]));
            } else {
                assertTrue(tmp[i] == null);
            }
        }

        // Si svuota la collezione.
        testCollection.clear();
        // Si controlla che, dopo la chiamata, 'a' contenga solo elementi null.
        testCollection.toArray(tmp);
        for (int i = 0; i < tmp.length; i++) {
            assertTrue(tmp[i] == null);
        }
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa {@code toArray(Object[] a)} con un array di dimensione insufficiente.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica che, se l'array fornito è troppo piccolo, il metodo ne
     * allochi uno nuovo della dimensione corretta e lo popoli con tutti gli
     * elementi della collezione.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea un array più piccolo della collezione.<br>
     * 2. Si invoca {@code toArray} e si salva l'array restituito.<br>
     * 3. Si verifica che l'array restituito non sia lo stesso di quello passato
     * come argomento.<br>
     * 4. Si verifica che il nuovo array abbia la dimensione corretta e contenga
     * tutti gli elementi della collezione.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata e un array di dimensione minore.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Deve essere restituito un nuovo array con la dimensione e il contenuto
     * corretti.
     */
    @Test
    public void testToArrayWithInsufficientlyLargeArray() {
        // Si controlla che se l'array fornito non è abbastanza grande, venga restituito
        // un nuovo array.
        Object[] tmp1 = new Object[1];
        Object[] tmp2 = testCollection.toArray(tmp1);
        // L'array restituito non deve essere lo stesso di quello passato come
        // argomento.
        assertFalse(tmp1.equals(tmp2));
        // Si controlla che 'b' abbia la stessa dimensione della collezione e ne
        // contenga gli elementi.
        assertTrue(tmp2.length == testCollection.size());
        for (int i = 0; i < tmp2.length; i++) {
            assertTrue(testCollection.contains(tmp2[i]));
        }
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code remove(null)} sollevi una {@code NullPointerException}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che il metodo di rimozione gestisca correttamente un
     * argomento nullo, come previsto dall'interfaccia.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testCollection.remove(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveWithNull() {
        testCollection.remove(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code remove(Object)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test valida i due scenari di {@code remove}: la rimozione di un
     * valore esistente e il tentativo di rimozione di un valore non esistente. Si
     * verifica che il valore restituito sia corretto ({@code true} o
     * {@code false}) e che la modifica si propaghi alla mappa sottostante.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si rimuove un valore esistente ("noce") e si verifica che il metodo
     * restituisca {@code true}.<br>
     * 2. Si verifica che il valore non sia più contenuto nella collezione.<br>
     * 3. Si tenta di rimuovere un valore non esistente e si verifica che il
     * metodo restituisca {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione non contiene più il valore "noce".
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code remove} deve restituire {@code true} per la rimozione riuscita e
     * {@code false} altrimenti.
     */
    @Test
    public void testRemove() {
        // Si controlla che la collezione contenga il valore "noce".
        assertTrue(testCollection.contains("noce"));

        // Si rimuove il valore "noce" dalla collezione.
        assertTrue(testCollection.remove("noce"));

        // Si controlla che la collezione non contenga più il valore "noce".
        assertFalse(testCollection.contains("noce"));

        // Si verifica che la collezione non cambi
        // se si tenta di rimuovere un valore non presente.
        assertFalse(testCollection.remove("software is eating the world"));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code remove(Object)} rimuova solo una singola istanza di un
     * elemento duplicato.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * La vista dei valori può contenere duplicati. Questo test assicura che una
     * chiamata a {@code remove} elimini solo la prima occorrenza trovata
     * dell'elemento, lasciando le altre intatte, come dall'interfaccia.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si aggiunge alla mappa un'altra entry con un valore già presente
     * ("noce"), creando un duplicato.<br>
     * 2. Si verifica che la dimensione della collezione sia aumentata.<br>
     * 3. Si invoca {@code remove("noce")}.<br>
     * 4. Si verifica che la collezione contenga ancora il valore "noce" (la
     * seconda istanza) e che la sua dimensione sia diminuita solo di 1.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una collezione contenente valori duplicati.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione contiene una istanza in meno dell'elemento rimosso.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La dimensione della collezione deve diminuire di uno e l'elemento rimosso
     * deve essere ancora presente.
     */
    @Test
    public void testRemoveWithDuplicates() {
        // Si controlla la presenza del valore "noce" e si salva la dimensione.
        assertTrue(testCollection.contains("noce"));
        int size = testCollection.size();

        // Si aggiunge un'altra coppia chiave-valore con lo stesso valore "noce".
        testMap.put(55, "noce");

        // Si controlla che la dimensione della collezione sia aumentata.
        assertTrue((size + 1) == testCollection.size());

        // Si rimuove un'istanza del valore "noce".
        assertTrue(testCollection.remove("noce"));

        // Si controlla che il valore "noce" sia ancora presente.
        assertTrue(testCollection.contains("noce"));

        // Si controlla che la dimensione sia diminuita
        // di 1, tornando a quella originale.
        assertTrue(size == testCollection.size());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code removeAll(null)} sollevi {@code NullPointerException}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura la corretta gestione di input non validi per il metodo di
     * rimozione massiva.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testCollection.removeAll(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveAllWithNull() {
        testCollection.removeAll(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa {@code removeAll(HCollection)} con elementi in comune.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test valida lo scenario in cui {@code removeAll} trova e rimuove
     * elementi comuni. L'obiettivo è verificare che il metodo restituisca
     * {@code true} (indicando che la collezione è stata modificata) e che solo
     * gli elementi specificati vengano effettivamente rimossi.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione di supporto contenente un sottoinsieme dei valori
     * di {@code testCollection}.<br>
     * 2. Si invoca {@code testCollection.removeAll()} e si verifica che
     * restituisca {@code true}.<br>
     * 3. Si verifica che gli elementi comuni non siano più presenti in
     * {@code testCollection}.<br>
     * 4. Si verifica che l'unico elemento rimasto sia quello non presente nella
     * collezione argomento.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una collezione popolata e una seconda collezione che ne condivide alcuni
     * elementi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione contiene solo gli elementi che non erano presenti nella
     * collezione passata come argomento.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code true}, la dimensione deve ridursi e gli
     * elementi comuni devono essere stati rimossi.
     */
    @Test
    public void testRemoveAllWithSharedElements() {
        // Si crea una collezione copia di testCollection da cui rimuovere gli elementi.
        MapAdapter tmpMap = new MapAdapter(testMap);
        HCollection tmpCollection = tmpMap.values(); // Copia di testCollection.
        tmpCollection.remove("pippo"); // Si rimuove un elemento dalla copia.

        // Si controlla che testCollection venga modificato dalla chiamata a removeAll.
        assertTrue(testCollection.removeAll(tmpCollection)); // Si rimuovono da testCollection gli elementi della copia.

        // Si controlla che le due collezioni non abbiano più elementi in comune.
        HIterator it = tmpCollection.iterator();
        while (it.hasNext())
            // testCollection non contiene più elementi presenti anche nella copia.
            assertFalse(testCollection.contains(it.next()));

        // L'unico valore rimasto in testCollection è
        // quello precedentemente rimosso dalla copia
        assertTrue(testCollection.contains("pippo"));
        assertTrue(testCollection.size() == 1);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa {@code removeAll(HCollection)} senza elementi in comune.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test copre il caso in cui non ci sono elementi in comune tra le due
     * collezioni. Si deve verificare che il metodo restituisca {@code false} e
     * che la collezione originale rimanga inalterata.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione senza elementi in comune con
     * {@code testCollection}.<br>
     * 2. Si invoca {@code testCollection.removeAll()} e si verifica che il
     * risultato sia {@code false}.<br>
     * 3. Si verifica che {@code testCollection} contenga ancora tutti i suoi
     * elementi originali.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una collezione popolata e una seconda collezione senza elementi in comune.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Lo stato di {@code testCollection} rimane invariato.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code false} e la collezione non deve essere
     * modificata.
     */
    @Test
    public void testRemoveAllWithNoSharedElements() {
        // Si crea una nuova collezione senza elementi in comune con testCollection.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put(27, "cappello");
        HCollection tmpCollection = tmpMap.values();

        // Si controlla che le due collezioni non abbiano elementi in comune.
        HIterator it = tmpCollection.iterator();
        while (it.hasNext())
            assertFalse(testCollection.contains(it.next()));

        // Si controlla che testCollection non cambi dopo la chiamata a removeAll.
        assertFalse(testCollection.removeAll(tmpCollection));
        assertTrue(testCollection.contains("noce") &&
                testCollection.contains("mano") &&
                testCollection.contains("sasso") &&
                testCollection.contains("pippo"));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code removeAll} rimuova correttamente tutte le istanze di un
     * elemento duplicato.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che se la collezione passata come argomento contiene
     * un elemento, tutte le occorrenze di tale elemento vengano eliminate dalla
     * collezione su cui il metodo è invocato, gestendo correttamente i duplicati.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione sorgente con valori duplicati (due "A").<br>
     * 2. Si crea una seconda collezione che contiene il valore "A".<br>
     * 3. Si invoca {@code removeAll()} sulla prima collezione.<br>
     * 4. Si verifica che entrambe le istanze di "A" siano state rimosse dalla
     * collezione sorgente.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una collezione con valori duplicati e una seconda collezione contenente uno
     * di quei valori.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La prima collezione non contiene più alcuna istanza degli elementi
     * specificati nella seconda.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Tutte le occorrenze degli elementi comuni devono essere rimosse.
     */
    @Test
    public void testRemoveAllWithDuplicatesInSource() {
        MapAdapter map1 = new MapAdapter();
        map1.put(1, "A");
        map1.put(9, "A");
        map1.put(3, "B");
        HCollection collection1 = map1.values();

        MapAdapter map2 = new MapAdapter();
        map2.put(1, "A");
        map2.put(9, "C");
        HCollection collection2 = map2.values();

        collection1.removeAll(collection2);

        // collection1 dovrebbe contenere solo "B"
        assertFalse(collection1.contains("A"));
        assertTrue(collection1.contains("B"));
        assertFalse(collection1.contains("C"));

    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code retainAll(null)} sollevi {@code NullPointerException}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura la corretta gestione di input non validi per il metodo di
     * mantenimento massivo.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testCollection.retainAll(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code NullPointerException}.
     */
    @Test(expected = NullPointerException.class)
    public void testRetainAllWithNull() {
        testCollection.retainAll(null);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code retainAll} sollevi {@code ClassCastException} con tipi
     * incompatibili.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * L'interfaccia di {@code retainAll} non specifica il comportamento in caso di
     * tipi incompatibili, ma per coerenza ci si aspetta che un'operazione che
     * implica un confronto fallisca. Si tenta di mantenere elementi di tipo
     * {@code HEntry} in una collezione di {@code String}.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca {@code retainAll} passando un {@code HSet} (la vista delle entry)
     * come argomento.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} e una collezione di tipo incompatibile.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code ClassCastException}.
     */
    @Test(expected = ClassCastException.class)
    public void testRetainAllWithIncompatibleType() {
        HSet tmpSet = testMap.entrySet();
        testCollection.retainAll(tmpSet);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa {@code retainAll(HCollection)} quando la collezione viene modificata.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test verifica che {@code retainAll} mantenga nella collezione solo
     * gli elementi presenti anche nella collezione specificata, rimuovendo tutti
     * gli altri e restituendo {@code true}.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione di supporto con alcuni elementi in comune con
     * {@code testCollection}.<br>
     * 2. Si invoca {@code retainAll} e si verifica che restituisca
     * {@code true}.<br>
     * 3. Si verifica che la dimensione della collezione si sia ridotta e che
     * contenga solo gli elementi comuni.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una collezione popolata e una seconda con cui condivide solo alcuni
     * elementi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione contiene solo gli elementi in comune con la collezione
     * argomento.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code true} e la collezione deve contenere solo
     * gli elementi mantenuti.
     */
    @Test
    public void testRetainAllWhenCollectionIsModified() {
        // Si crea una nuova mappa per mantenere solo i suoi valori in testCollection
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put(15, "noce");
        tmpMap.put(5, "pippo");
        tmpMap.put(0, "istrice");

        // Collezione con alcuni elementi in comune con testCollection
        HCollection tmpCollection = tmpMap.values();

        // Si controlla che testCollection cambi dopo la chiamata a retainAll
        assertTrue(testCollection.retainAll(tmpCollection)); // testCollection dovrebbe cambiare

        // La dimensione passa da 4 a 2
        assertTrue(testCollection.size() == 2);
        assertTrue(tmpCollection.containsAll(testCollection));
        assertTrue(testCollection.contains("noce") == tmpCollection.contains("noce"));
        assertTrue(testCollection.contains("pippo") == tmpCollection.contains("pippo"));

        // tmpCollection contiene ancora elementi che testCollection non contiene
        assertTrue(!testCollection.contains("istrice") == tmpCollection.contains("istrice"));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Testa {@code retainAll(HCollection)} quando la collezione non viene
     * modificata.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test verifica che, se la collezione argomento contiene già tutti gli
     * elementi di quella di partenza, {@code retainAll} non apporti modifiche e
     * restituisca {@code false}.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione identica a {@code testCollection}.<br>
     * 2. Si invoca {@code retainAll} e si verifica che il risultato sia
     * {@code false}.<br>
     * 3. Si verifica che le due collezioni siano ancora uguali.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Due collezioni identiche.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo deve restituire {@code false} e la collezione non deve essere
     * modificata.
     */
    @Test
    public void testRetainAllWhenCollectionIsNotModified() {
        // Si crea una collezione identica a testCollection.
        MapAdapter tmpMap = new MapAdapter(testMap);
        HCollection tmpCollection = tmpMap.values();

        // Si verifica che tmpCollection contenga tutti gli elementi di testCollection.
        assertTrue(testCollection.containsAll(tmpCollection));

        // Si controlla che testCollection non cambi dopo la chiamata a retainAll.
        assertFalse(testCollection.retainAll(tmpCollection)); // testCollection non dovrebbe cambiare.

        // Si verifica che le collezioni siano rimaste uguali.
        assertEquals(testCollection, tmpCollection);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code retainAll} con una collezione vuota svuoti la
     * collezione di partenza.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test copre il caso limite in cui l'intersezione è vuota. Mantenere
     * gli elementi di una collezione vuota deve risultare in una collezione
     * vuota.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione vuota.<br>
     * 2. Si invoca {@code retainAll} con la collezione vuota come argomento.<br>
     * 3. Si verifica che la collezione di partenza sia diventata vuota.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una collezione popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione di partenza è vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La collezione deve diventare vuota e il metodo deve restituire
     * {@code true}.
     */
    @Test
    public void testRetainAllWithEmptyCollection() {
        // Si crea una collezione ausiliaria vuota.
        MapAdapter tmpMap = new MapAdapter();
        HCollection tmpCollection = tmpMap.values();

        // Si invoca retainAll() con la collezione vuota.
        assertTrue(testCollection.retainAll(tmpCollection));
        // Si verifica che la collezione di partenza sia diventata vuota.
        assertTrue(testCollection.isEmpty());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code clear()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test valida che {@code clear()} svuoti una collezione popolata e che
     * un'invocazione successiva su una collezione già vuota non causi errori e la
     * lasci vuota.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si invoca {@code clear()} su una collezione popolata.<br>
     * 2. Si verifica che la dimensione sia 0 e che la collezione sia vuota.<br>
     * 3. Si invoca nuovamente {@code clear()} e si verifica che la collezione
     * rimanga vuota.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La collezione deve essere vuota e avere dimensione 0 dopo la prima
     * chiamata a {@code clear()}.
     */
    @Test
    public void testClear() {
        // Si controlla che la collezione non sia vuota all'inizio.
        assertTrue(testCollection.size() > 0);
        // Si svuota la collezione.
        testCollection.clear();
        // Si controlla che la collezione sia ora vuota e di dimensione 0.
        assertTrue(testCollection.isEmpty() && testCollection.size() == 0);
        // Si controlla che un'ulteriore chiamata a clear() non alteri lo stato.
        testCollection.clear();
        assertTrue(testCollection.isEmpty() && testCollection.size() == 0);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code equals} con argomento {@code null} restituisca
     * {@code false}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che l'implementazione rispetti l'interfaccia generale del
     * metodo {@code equals}, che prevede che {@code x.equals(null)} sia sempre
     * {@code false}.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testCollection.equals(null)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve restituire {@code false}.
     */
    @Test
    public void testEqualsWithNull() {
        assertFalse(testCollection.equals(null));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica la proprietà riflessiva del metodo {@code equals}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Un test di base per garantire che un'istanza di collezione sia sempre
     * uguale a se stessa ({@code x.equals(x)} deve restituire {@code true}).
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca {@code testCollection.equals(testCollection)}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve restituire {@code true}.
     */
    @Test
    public void testEqualsWithSelf() {
        assertTrue(testCollection.equals(testCollection));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code hashCode()}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che {@code hashCode} rispetti la sua interfaccia:
     * due collezioni uguali devono avere lo stesso hashCode.
     * Verifica anche che l'hashCode di una collezione vuota sia 0 e che cambi
     * quando la collezione viene modificata.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si creano due collezioni uguali e si verifica che i loro hashCode siano
     * identici.<br>
     * 2. Si svuota una delle collezioni e si verifica che il suo hashCode diventi
     * 0 e sia diverso da quello della collezione piena.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione di test risulta vuota.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Due collezioni uguali devono avere lo stesso hashCode. Una collezione vuota
     * deve avere un hashCode di 0.
     */
    @Test
    public void testHashCode() {
        // Si crea una seconda collezione uguale alla prima.
        MapAdapter tmpMap = new MapAdapter(testMap);
        HCollection tmpCollection = tmpMap.values();

        // Si controlla che le due collezioni siano uguali.
        assertEquals(testCollection, tmpCollection);
        // Di conseguenza, anche i loro hashCode devono essere uguali.
        assertEquals(testCollection.hashCode(), tmpCollection.hashCode());

        testCollection.clear();
        // L'hashCode di una collezione vuota deve essere 0.
        assertTrue(testCollection.hashCode() == 0);

        // Si controlla che l'hashCode di testCollection sia
        // cambiato e ora sia diverso da quello di tmpCollection.
        assertNotEquals(testCollection.hashCode(), tmpCollection.hashCode());
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica del corretto funzionamento del metodo {@code equals(Object)}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test valida che {@code equals} funzioni come dall'interfaccia per una
     * collezione generica: due collezioni sono uguali se sono dello stesso tipo,
     * hanno la stessa dimensione e contengono gli stessi elementi con la stessa
     * cardinalità (numero di occorrenze).
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si confronta la collezione con una sua copia identica e si verifica che
     * {@code equals} restituisca {@code true}.<br>
     * 2. Si rende la copia diversa per dimensione e si verifica che
     * {@code equals} restituisca {@code false}.<br>
     * 3. Si ripristina la dimensione ma con contenuti diversi e si verifica che
     * {@code equals} restituisca {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione originale rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code equals} deve restituire {@code true} solo per collezioni identiche
     * per tipo, dimensione e contenuto.
     */
    @Test
    public void testEqualsWithEqualAndUnequalCollections() {
        // Si crea una collezione identica a testCollection.
        MapAdapter tmpMap = new MapAdapter(testMap);
        HCollection tmpCollection = tmpMap.values();

        // Una collezione è uguale a un'altra se sono HCollection, hanno la stessa
        // dimensione e gli stessi elementi.
        assertEquals(tmpCollection instanceof HCollection, testCollection instanceof HCollection);
        assertTrue(testCollection.size() == tmpCollection.size());
        assertTrue(testCollection.containsAll(tmpCollection) && tmpCollection.containsAll(testCollection));
        // Si controlla che le due collezioni siano uguali.
        assertTrue(testCollection.equals(tmpCollection));

        // Si rende la seconda collezione diversa rimuovendo un elemento.
        tmpCollection.remove("pippo");

        // Si verifica che non siano uguali (dimensione diversa).
        assertFalse(testCollection.size() == tmpCollection.size());
        assertFalse(testCollection.equals(tmpCollection));

        // Si aggiunge un duplicato a tmpMap per ripristinare la dimensione.
        tmpMap.put(6, "noce");

        // Le dimensioni sono ora di nuovo uguali.
        assertTrue(testCollection.size() == tmpCollection.size());

        // Ma i contenuti sono diversi: testCollection contiene colEquals, ma non
        // viceversa.
        assertTrue(testCollection.containsAll(tmpCollection));
        assertFalse(tmpCollection.containsAll(testCollection));

        // Si controlla che le due collezioni non siano uguali.
        assertFalse(testCollection.equals(tmpCollection));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code equals(Object)} restituisca {@code false} con oggetti di
     * tipo diverso.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Il test assicura che {@code equals} rispetti l'interfaccia generale che
     * richiede {@code false} se l'oggetto di confronto non è dello stesso tipo,
     * anche se il contenuto fosse concettualmente simile.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si confronta la collezione con un {@code HSet} (la vista delle chiavi) e
     * si verifica che il risultato sia {@code false}.<br>
     * 2. Si confronta la collezione con un {@code Integer} e si verifica che il
     * risultato sia {@code false}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * {@code equals} deve restituire {@code false} in entrambi i casi.
     */
    @Test
    public void testEqualsWithDifferentObjectTypes() {
        // Si crea una nuova mappa per ottenere un HSet.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put("noce", 5);
        tmpMap.put("mano", 9);
        tmpMap.put("sasso", 1);
        tmpMap.put("pippo", 0);
        HSet tmpSet = tmpMap.keySet();

        // Anche se la collezione contiene tutti gli elementi del set...
        assertTrue(testCollection.containsAll(tmpSet));
        // ...non è uguale al set, perché non sono della stessa classe.
        assertFalse(testCollection.equals(tmpSet));

        // Si controlla che testCollection non sia uguale a un oggetto di tipo diverso.
        assertFalse(testCollection.equals(5));
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code equals} funzioni correttamente con valori duplicati.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che il metodo {@code equals} consideri la cardinalità
     * (numero di occorrenze) degli elementi. Due collezioni sono uguali solo se
     * contengono gli stessi elementi con la stessa frequenza.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si creano due collezioni distinte che contengono esattamente gli stessi
     * elementi, inclusi i duplicati (due "mela").<br>
     * 2. Si verifica che le due collezioni siano considerate uguali.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Due collezioni con gli stessi elementi e le stesse cardinalità.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Le collezioni rimangono invariate.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code equals} deve restituire {@code true}.
     */
    @Test
    public void testEqualsWithDuplicates() {
        // Si crea la prima mappa e la relativa collezione di valori.
        MapAdapter map1 = new MapAdapter();
        map1.put(1, "mela");
        map1.put(9, "banana");
        map1.put(3, "mela"); // Valore duplicato
        map1.put(5, "pera");
        HCollection collection1 = map1.values();

        // Si crea una seconda mappa con gli stessi valori ma chiavi diverse.
        MapAdapter map2 = new MapAdapter();
        map2.put(10, "pera");
        map2.put(20, "mela");
        map2.put(30, "banana");
        map2.put(40, "mela"); // Valore duplicato
        HCollection collection2 = map2.values();

        // Le due collezioni non sono lo stesso oggetto.
        assertNotSame("Le collezioni non devono essere lo stesso oggetto in memoria", collection1,
                collection2);

        // Si verifica che le due collezioni siano considerate uguali perché
        // contengono gli stessi elementi con la stessa cardinalità.
        assertEquals("Le collezioni con gli stessi duplicati devono essere uguali", collection1,
                collection2);

        // Si verifica la proprietà simmetrica di equals.
        assertEquals("La relazione di uguaglianza deve essere simmetrica", collection2, collection1);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code equals} restituisca {@code false} per collezioni con
     * diversa cardinalità degli elementi.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * Questo test assicura che {@code equals} distingua correttamente tra
     * collezioni che, pur avendo la stessa dimensione, hanno un numero diverso di
     * occorrenze per i loro elementi.
     * <p>
     * <b>Test Description</b>
     * <p>
     * 1. Si crea una collezione con due "mela" e una "banana".<br>
     * 2. Si crea una seconda collezione con una "mela" e due "banana".<br>
     * 3. Si verifica che le due collezioni non siano considerate uguali.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Due collezioni della stessa dimensione ma con diversa cardinalità degli
     * elementi.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * Le collezioni rimangono invariate.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * Il metodo {@code equals} deve restituire {@code false}.
     */
    @Test
    public void testEqualsWithDifferentCardinality() {
        // Si crea la prima collezione: due "apple", una "banana".
        MapAdapter map1 = new MapAdapter();
        map1.put(1, "mela");
        map1.put(9, "mela");
        map1.put(3, "banana");
        HCollection collection1 = map1.values();

        // Si crea la seconda collezione: una "mela", due "banana".
        MapAdapter map2 = new MapAdapter();
        map2.put(10, "mela");
        map2.put(20, "banana");
        map2.put(30, "banana");
        HCollection collection2 = map2.values();

        // Le collezioni hanno la stessa dimensione ma contenuto diverso.
        assertEquals("Le collezioni devono avere la stessa dimensione per questo test",
                collection1.size(), collection2.size());

        // Si verifica che le collezioni non siano uguali
        // a causa della diversa cardinalità degli elementi.
        assertNotEquals("Le collezioni con cardinalità diversa non devono essere uguali", collection1,
                collection2);

        // Si verifica la proprietà simmetrica.
        assertNotEquals("La relazione di disuguaglianza deve essere simmetrica", collection2,
                collection1);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che il metodo {@code add(Object)} sollevi una
     * {@code UnsupportedOperationException}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * La vista dei valori non supporta l'aggiunta diretta di elementi. Questo
     * test assicura che qualsiasi tentativo di farlo venga bloccato con
     * l'eccezione appropriata, come dall'interfaccia.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si invoca il metodo {@code testCollection.add("Ginocchio")}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code UnsupportedOperationException}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testAdd() {
        testCollection.add(88);
    }

    /**
     * <b>Summary</b>
     * <p>
     * Verifica che {@code addAll(HCollection)} sollevi
     * {@code UnsupportedOperationException}.
     * <p>
     * <b>Test Case Design</b>
     * <p>
     * La vista dei valori non supporta l'aggiunta massiva di elementi. Questo
     * test assicura che qualsiasi tentativo di farlo venga bloccato.
     * <p>
     * <b>Test Description</b>
     * <p>
     * Si tenta di aggiungere una collezione a {@code testCollection} usando
     * {@code addAll}.
     * <p>
     * <b>Pre-Condition</b>
     * <p>
     * Una {@code ValueCollection} popolata.
     * <p>
     * <b>Post-Condition</b>
     * <p>
     * La collezione rimane invariata.
     * <p>
     * <b>Expected Results</b>
     * <p>
     * La chiamata deve sollevare una {@code UnsupportedOperationException}.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testAddAll() {
        // Si crea una nuova collezione per tentare di aggiungerla a testCollection.
        MapAdapter tmpMap = new MapAdapter();
        tmpMap.put(1, "pio");
        HCollection tmpCollection = tmpMap.values();

        // Si aggiunge la collezione a testCollection, attendendosi un'eccezione.
        testCollection.addAll(tmpCollection);
    }

}