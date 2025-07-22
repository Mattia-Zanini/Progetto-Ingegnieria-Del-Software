# Progetto di Ingegneria del Software A.A. 2024-2025

# Autore

ZANINI MATTIA

## Scopo del Progetto

L'obiettivo di questo progetto è lo sviluppo di un **Adapter** software in
ambiente Java. Il progetto simula la necessità di adattare una libreria di
classi (`myLib`), originariamente sviluppata per l'ambiente J2SE 1.4.2, per
essere utilizzata in un ambiente con risorse limitate come **Java Micro Edition
(CLDC 1.1)**.

In particolare, si realizza un adapter per l'interfaccia `java.util.Map` (e le
interfacce ad essa connesse come `Collection`, `Set`, `Iterator`) della libreria
J2SE 1.4.2. La classe da adattare (`adaptee`) è `java.util.Hashtable`,
disponibile in CLDC 1.1.

## Requisiti Fondamentali

Il progetto aderisce ai seguenti requisiti stringenti:

- **Ambiente di Sviluppo:** Il codice sviluppato deve utilizzare esclusivamente
  le funzionalità disponibili in **CLDC 1.1**.
- **Prevenzione Collisioni:** Per evitare conflitti con le API della versione
  Java corrente, sono state definite interfacce locali (`HMap`, `HCollection`,
  `HSet`, `HIterator`) all'interno del package `myAdapter`. Queste interfacce
  replicano fedelmente le loro controparti di J2SE 1.4.2.
- **Conformità Funzionale:** Il comportamento dell'adapter (`MapAdapter`) e di
  tutte le classi correlate (viste, iteratori) deve essere pienamente conforme
  alla documentazione ufficiale di J2SE 1.4.2, implementando anche tutte le
  operazioni opzionali.
- **Test Driven Development (TDD):** Lo sviluppo è guidato dai test. Tutte le
  classi di test sono scritte con JUnit e si trovano nel package `myTest`.
- **Esecuzione Test:** Il package di test include una classe `TestRunner` che
  permette di eseguire l'intera suite di test da riga di comando.

## Struttura del Progetto

```
/
├── myAdapter/      # Contiene le interfacce custom (HMap, etc.) e l'implementazione dell'adapter
├── myTest/         # Contiene le classi di test JUnit
├── JUnit/          # Librerie JUnit 4.13
├── FileClass/      # Output della compilazione (file .class)
├── Javadoc/        # Output della documentazione Javadoc
├── README.md       # Questo file
└── ...             # Altri file di progetto
```

## Istruzioni Operative

### 1. Compilazione

Per compilare l'intero progetto, eseguire il seguente comando dalla directory
radice. I file `.class` verranno generati nella cartella `FileClass`.

```bash
javac -d FileClass -cp :./JUnit/junit-4.13.jar:./JUnit/hamcrest-core-1.3.jar ./myAdapter/*.java myTest/*.java
```

Se si utilizza un compilatore moderno e non si vogliono visualizzare i warning relativi a funzionalità deprecate e marcate per la rimozione, compilare il codice aggiungendo il flag `-Xlint:-removal`.

```bash
javac -Xlint:-removal -d FileClass -cp :./JUnit/junit-4.13.jar:./JUnit/hamcrest-core-1.3.jar ./myAdapter/*.java myTest/*.java
```

### 2. Esecuzione della Suite di Test

Per eseguire tutti i test definiti nel package `myTest`, utilizzare il
`TestRunner` tramite il seguente comando:

```bash
java -cp :./JUnit/junit-4.13.jar:./JUnit/hamcrest-core-1.3.jar:./FileClass  myTest/TestRunner
```

È anche possibile eseguire i test per una singola classe specificandola come
argomento:

```bash
java -cp :./JUnit/junit-4.13.jar:./JUnit/hamcrest-core-1.3.jar:./FileClass myTest/TestRunner myTest.TestValueCollection
```

Se si vuole compilare il progetto (senza i warning) ed eseguire tutti i test, basta eseguire questo comando:

```bash
javac -Xlint:-removal -d FileClass -cp :./JUnit/junit-4.13.jar:./JUnit/hamcrest-core-1.3.jar ./myAdapter/*.java myTest/*.java && \ 
java -cp :./JUnit/junit-4.13.jar:./JUnit/hamcrest-core-1.3.jar:./FileClass  myTest/TestRunner
```


### 3. Generazione della Documentazione Javadoc

Per generare la documentazione tecnica del codice, che include tag
personalizzati come richiesto dalle specifiche, eseguire questo comando:

```bash
javadoc \
-d Javadoc/ \
-cp ./JUnit/junit-4.13.jar: \
./myTest/*.java ./myAdapter/*.java
```