# Layout delle Classi e Interfacce di myAdapter

Questo documento descrive la struttura delle classi e delle interfacce nel package `myAdapter`.

---

## Interfaccia: `HCollection`

### Metodi

- `int size()`
- `boolean isEmpty()`
- `boolean contains(Object o)`
- `HIterator iterator()`
- `Object[] toArray()`
- `Object[] toArray(Object[] a)`
- `boolean add(Object o)`
- `boolean remove(Object o)`
- `boolean containsAll(HCollection c)`
- `boolean addAll(HCollection c)`
- `boolean removeAll(HCollection c)`
- `boolean retainAll(HCollection c)`
- `void clear()`
- `boolean equals(Object o)`
- `int hashCode()`

---

## Interfaccia: `HEntry`

### Metodi

- `Object getKey()`
- `Object getValue()`
- `Object setValue(Object v)`
- `boolean equals(Object o)`
- `int hashCode()`

---

## Interfaccia: `HIterator`

### Metodi

- `boolean hasNext()`
- `Object next()`
- `void remove()`

---

## Interfaccia: `HMap`

### Metodi

- `int size()`
- `boolean isEmpty()`
- `boolean containsKey(Object key)`
- `boolean containsValue(Object value)`
- `Object get(Object key)`
- `Object put(Object key, Object value)`
- `Object remove(Object key)`
- `void putAll(HMap t)`
- `void clear()`
- `HSet keySet()`
- `HCollection values()`
- `HSet entrySet()`
- `boolean equals(Object o)`
- `int hashCode()`

---

## Interfaccia: `HSet`

*Estende `HCollection`*

---

## Classe: `KeySet`

*Implementa `HSet`, Estende `ValueCollection`*

### Variabili

- Ereditate da `ValueCollection`

### Metodi

- `KeySet(MapAdapter p)`
- `boolean contains(Object o)`
- `HIterator iterator()`
- `boolean remove(Object o)`
- `boolean containsAll(HCollection c)`
- `boolean removeAll(HCollection c)`
- `boolean equals(Object o)`

#### Classe Interna: `SetIterator`

*Implementa `HIterator`*

##### Variabili

- `Hashtable parent`
- `Enumeration en`
- `boolean canRemove`
- `Object lastReturned`

##### Metodi

- `SetIterator(Hashtable h)`
- `boolean hasNext()`
- `Object next()`
- `void remove()`

---

## Classe: `MapAdapter`

*Implementa `HMap`*

### Variabili

- `Hashtable hash`

### Metodi

- `MapAdapter()`
- `MapAdapter(HMap m)`
- `int size()`
- `boolean isEmpty()`
- `boolean containsKey(Object key)`
- `boolean containsValue(Object value)`
- `Object get(Object key)`
- `Object put(Object key, Object value)`
- `Object remove(Object key)`
- `void putAll(HMap sourceHMap)`
- `void clear()`
- `HSet keySet()`
- `HCollection values()`
- `HSet entrySet()`
- `boolean equals(Object o)`
- `int hashCode()`

#### Classe Interna: `Entry`

*Implementa `HEntry`*

##### Variabili

- `Object key`
- `MapAdapter parentMap`

##### Metodi

- `Entry(MapAdapter p, Object k)`
- `Object getKey()`
- `Object getValue()`
- `Object setValue(Object value)`
- `boolean equals(Object o)`
- `int hashCode()`

#### Classe Interna: `EntrySet`

*Implementa `HSet`, Estende `ValueCollection`*

##### Metodi

- `EntrySet(MapAdapter parentMap)`
- `boolean contains(Object o)`
- `HIterator iterator()`
- `boolean remove(Object o)`
- `boolean containsAll(HCollection c)`
- `boolean removeAll(HCollection c)`
- `boolean equals(Object o)`

##### Classe Interna: `EntrySetIterator`

*Implementa `HIterator`*

###### Variabili

- `MapAdapter parentMap`
- `Enumeration en`
- `boolean canRemove`
- `Entry lastReturned`

###### Metodi

- `EntrySetIterator(MapAdapter p)`
- `boolean hasNext()`
- `Object next()`
- `void remove()`

---

## Classe: `ValueCollection`

*Implementa `HCollection`*

### Variabili

- `MapAdapter parentMap`

### Metodi

- `ValueCollection(MapAdapter p)`
- `int size()`
- `boolean isEmpty()`
- `boolean contains(Object o)`
- `HIterator iterator()`
- `Object[] toArray()`
- `Object[] toArray(Object[] a)`
- `boolean add(Object o)`
- `boolean remove(Object o)`
- `boolean containsAll(HCollection c)`
- `boolean addAll(HCollection c)`
- `boolean removeAll(HCollection c)`
- `boolean retainAll(HCollection c)`
- `void clear()`
- `boolean equals(Object o)`
- `int hashCode()`

#### Classe Interna: `CollectionIterator`

*Implementa `HIterator`*

##### Variabili

- `Hashtable parent`
- `Enumeration en`
- `boolean canRemove`
- `Object lastReturned`

##### Metodi

- `CollectionIterator(Hashtable h)`
- `boolean hasNext()`
- `Object next()`
- `void remove()`

---

## Classe: `ValueCollectionNew`

*Implementa `HCollection`*

### Variabili

- `MapAdapter parentMap`

### Metodi

- `ValueCollectionNew(MapAdapter p)`
- `int size()`
- `boolean isEmpty()`
- `boolean contains(Object o)`
- `HIterator iterator()`
- `Object[] toArray()`
- `Object[] toArray(Object[] a)`
- `boolean add(Object o)`
- `boolean remove(Object o)`
- `boolean containsAll(HCollection c)`
- `boolean addAll(HCollection c)`
- `boolean removeAll(HCollection c)`
- `boolean retainAll(HCollection c)`
- `void clear()`
- `boolean equals(Object o)`
- `int hashCode()`

#### Classe Interna: `CollectionIterator`

*Implementa `HIterator`*

##### Variabili

- `Hashtable parent`
- `Enumeration en`
- `boolean canRemove`
- `Object lastReturned`

##### Metodi

- `CollectionIterator(Hashtable h)`
- `boolean hasNext()`
- `Object next()`
- `void remove()`
