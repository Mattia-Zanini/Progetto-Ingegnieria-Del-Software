package myTest;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Iterator;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Classe eseguibile che funge da punto di ingresso per avviare le suite di test
 * per il package {@code myAdapter}.
 * <p>
 * Il runner può operare in due modalità:
 * <ol>
 * <li><b>Senza argomenti:</b> Esegue una suite di test predefinita che
 * comprende tutte le classi di test del progetto.</li>
 * <li><b>Con un argomento:</b> Esegue solo la classe di test specificata
 * dal suo nome completo, passato da riga di comando (es.
 * {@code myTest.TestMapAdapter}).</li>
 * </ol>
 * Al termine dell'esecuzione, stampa un riepilogo dei risultati, indicando
 * il numero di test eseguiti, i fallimenti, i dettagli degli errori e il
 * tempo di esecuzione totale. In caso di fallimenti, questi vengono raggruppati
 * per Test Case per una migliore leggibilità.
 */
public class TestRunner {
    /**
     * Questo costruttore è vuoto e serve a soddisfare i requisiti
     * dello strumento Javadoc, evitando warning di documentazione.
     */
    private TestRunner() {
        // Costruttore intenzionalmente lasciato vuoto.
    }

    /**
     * Metodo principale che avvia l'esecuzione dei test.
     *
     * @param args Argomenti da riga di comando. Se viene fornito un argomento,
     *             verrà interpretato come il nome completo della classe di test
     *             da eseguire. Se non vengono forniti argomenti, verrà eseguita
     *             la suite di test predefinita.
     */
    public static void main(String[] args) {
        Class[] testClasses;

        // Controlla se è stato passato un argomento da riga di comando.
        if (args.length > 0) {
            try {
                // Se presente, tenta di caricare la classe di test specificata.
                // È necessario fornire il nome completo della classe (es.
                // "myTest.TestMyClass").
                System.out.println("Esecuzione della classe di test specificata: " + args[0]);
                Class testClass = Class.forName(args[0]);
                testClasses = new Class[] { testClass };
            } catch (ClassNotFoundException e) {
                // Se la classe non viene trovata, stampa un errore e termina l'esecuzione.
                System.err.println("Errore: La classe di test specificata non è stata trovata: " + args[0]);
                return;
            }
        } else {
            // Se non vengono forniti argomenti, esegue la suite di test completa.
            System.out.println("Esecuzione di tutte le classi di test predefinite...");
            testClasses = new Class[] {
                    TestValueCollection.class,
                    TestCollectionIterator.class,
                    TestEntrySet.class,
                    TestEntrySetIterator.class,
                    TestMapAdapter.class,
                    TestMapEntry.class,
                    TestKeySet.class,
                    TestSetIterator.class
            };
        }

        // Misura il tempo di esecuzione dei test.
        long startTime = System.currentTimeMillis(); // Istante di inizio

        // Esegue le classi di test usando il core di JUnit.
        Result result = JUnitCore.runClasses(testClasses);

        long endTime = System.currentTimeMillis(); // Istante di fine

        // Stampa i risultati dei test in un formato leggibile.
        System.out.println("\n--- Riepilogo dei Risultati ---");
        System.out.println("Numero totale di test eseguiti: " + result.getRunCount());
        System.out.println("Numero di test falliti: " + result.getFailureCount());

        // Se ci sono stati fallimenti, li raggruppa per classe e li stampa.
        if (result.getFailureCount() > 0) {
            System.out.println("\nDettaglio dei fallimenti:");

            // Struttura per raggruppare i fallimenti per classe.
            // Chiave: Nome della classe (String)
            // Valore: Lista di fallimenti (Vector)
            Hashtable failuresByClass = new Hashtable();

            // Itera su tutti i fallimenti per popolarla.
            Iterator it = result.getFailures().iterator();
            while (it.hasNext()) {
                Failure failure = (Failure) it.next();
                String className = failure.getDescription().getClassName();

                // Se la classe non è ancora nella Hashtable, la aggiunge.
                if (failuresByClass.containsKey(className) == false)
                    failuresByClass.put(className, new Vector());

                // Aggiunge il fallimento alla lista della sua classe.
                Vector v = (Vector) failuresByClass.get(className);
                v.addElement(failure);
            }

            // Stampa i fallimenti raggruppati.
            Enumeration classNames = failuresByClass.keys();
            while (classNames.hasMoreElements()) {
                String className = (String) classNames.nextElement();
                System.out.println("\n  --- Classe: " + className + " ---");
                Vector classFailures = (Vector) failuresByClass.get(className);
                for (int i = 0; i < classFailures.size(); i++) {
                    Failure failure = (Failure) classFailures.elementAt(i);
                    System.out.println("    - " + failure.toString());
                }
            }
        }

        // Stampa il tempo totale impiegato per l'esecuzione.
        System.out.println("\nTempo totale di esecuzione: " + (endTime - startTime) + " ms");

        // Stampa un messaggio finale sullo stato complessivo dei test.
        if (result.wasSuccessful())
            System.out.println("\nStato: TUTTI I TEST SONO STATI SUPERATI CON SUCCESSO.");
        else
            System.out.println("\nStato: ALCUNI TEST SONO FALLITI.");
    }
}