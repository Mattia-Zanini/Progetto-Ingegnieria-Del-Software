# Layout delle Classi e Interfacce di myTest

Questo documento descrive la struttura delle classi di test nel package `myTest`.

---

## Classe: `TestCollectionIterator`

### Variabili

- `MapAdapter testMap`
- `HCollection testCollection`
- `HIterator testIterator`

### Metodi

- `void setUp()`
- `void testCollectionIteratorHasNext()`
- `void testCollectionIteratorNext()`
- `void testCollectionIteratorNextOnEmptyCollection()`
- `void testCollectionIteratorRemove()`
- `void testCollectionIteratorRemoveWithoutNext()`

---

## Classe: `TestEntrySet`

### Variabili

- `MapAdapter testMap`
- `HSet testEntrySet`

### Metodi

- `void setUp()`
- `void testContainsWithNull()`
- `void testContainsWithInvalidType()`
- `void testContains()`
- `void testIterator()`
- `void testRemoveWithNull()`
- `void testRemoveWithInvalidType()`
- `void testRemove()`
- `void testContainsAllWithNullCollection()`
- `void testContainsAllWithInvalidTypeCollection()`
- `void testContainsAll()`
- `void testRemoveAllWithNullCollection()`
- `void testRemoveAllWithInvalidTypeCollection()`
- `void testRemoveAllWhenModified()`
- `void testRemoveAllWhenNotModified()`
- `void testEqualsWithEqualSets()`
- `void testEqualsWithDifferentObjects()`

---

## Classe: `TestEntrySetIterator`

### Variabili

- `MapAdapter testMap`
- `HSet testEntrySet`
- `HIterator testIterator`

### Metodi

- `void setUp()`
- `void testEntrySetIteratorHasNext()`
- `void testEntrySetIteratorNext()`
- `void testEntrySetIteratorNextOnEmptyMap()`
- `void testEntrySetIteratorRemove()`
- `void testEntrySetIteratorRemoveThrowsIllegalStateException()`

---

## Classe: `TestKeySet`

### Variabili

- `MapAdapter testMap`
- `HSet testKeySet`

### Metodi

- `void setUp()`
- `void testKeySetContainsNull()`
- `void testKeySetContains()`
- `void testKeySetIterator()`
- `void testKeySetRemoveNull()`
- `void testKeySetRemove()`
- `void testKeySetContainsAllNull()`
- `void testKeySetContainsAll()`
- `void testKeySetRemoveAllNull()`
- `void testKeySetRemoveAllWhenModified()`
- `void testKeySetRemoveAllWhenNotModified()`
- `void testKeySetEqualsSet()`
- `void testKeySetEqualsNotSet()`

---

## Classe: `TestMapAdapter`

### Variabili

- `MapAdapter testMap`

### Metodi

- `void setUp()`
- `void testMapSize()`
- `void testIsEmpty()`
- `void testCopyConstructor()`
- `void testCopyIsIndependent()`
- `void testContainsKeyWithNull()`
- `void testContainsKey()`
- `void testContainsValueWithNull()`
- `void testContainsValue()`
- `void testGetWithNull()`
- `void testGet()`
- `void testPutWithNullValue()`
- `void testPutWithNullKey()`
- `void testPutWithNullKeyAndValue()`
- `void testPut()`
- `void testRemoveWithNull()`
- `void testRemove()`
- `void testPutAllWithNullMap()`
- `void testPutAllWithCommonAndUncommonKeys()`
- `void testPutAllWithCommonKeys()`
- `void testPutAllOverwritesValues()`
- `void testPutAllWithIdenticalMap()`
- `void testPutAllWithSelf()`
- `void testClear()`
- `void testKeySetInstance()`
- `void testKeySetIteratorRemove()`
- `void testKeySetRemove()`
- `void testKeySetRemoveAll()`
- `void testKeySetRetainAll()`
- `void testKeySetClear()`
- `void testMapChangeReflectsOnKeySet()`
- `void testValuesInstance()`
- `void testValuesIteratorRemove()`
- `void testValuesRemove()`
- `void testValuesRemoveAll()`
- `void testValuesRetainAll()`
- `void testValuesClear()`
- `void testMapChangeReflectsOnValues()`
- `void testEntrySetInstance()`
- `void testEntrySetIteratorRemove()`
- `void testEntrySetRemove()`
- `void testEntrySetRemoveAll()`
- `void testEntrySetRetainAll()`
- `void testEntrySetClear()`
- `void testMapChangeReflectsOnEntrySet()`
- `void testEqualsForEqualMaps()`
- `void testEqualsForDifferentObjects()`
- `void testEqualsWithSelf()`
- `void testHashCodeContract()`

---

## Classe: `TestMapEntry`

### Variabili

- `MapAdapter testMap`
- `HSet testEntrySet`

### Metodi

- `void setUp()`
- `void testEntryGetKey()`
- `void testEntryGetValue()`
- `void testGetValueAfterRemove()`
- `void testSetValueWithNull()`
- `void testEntrySetValue()`
- `void testEntryEquals()`
- `void testEqualsWithWrongType()`
- `void testEntryHashCode()`

---

## Classe: `TestRunner`

### Metodi

- `void main(String[] args)`

---

## Classe: `TestSetIterator`

### Variabili

- `MapAdapter testMap`
- `HSet testKeySet`
- `HIterator testIterator`

### Metodi

- `void setUp()`
- `void test_SetIterator_HasNext()`
- `void test_SetIterator_Next()`
- `void test_SetIterator_Next_NoSuchElem()`
- `void test_SetIterator_Remove()`
- `void test_SetIterator_Remove_IllegalState()`

---

## Classe: `TestValueCollection`

### Variabili

- `MapAdapter testMap`
- `HCollection testCollection`

### Metodi

- `void setUp()`
- `void testSize()`
- `void testIsEmpty()`
- `void testContainsWithNullThrowsException()`
- `void testContains()`
- `void testIterator()`
- `void testToArray()`
- `void testToArrayDoesNotReturnReference()`
- `void testToArrayWithNullParameterThrowsException()`
- `void testToArrayWithIncompatibleTypeThrowsArrayStoreException()`
- `void testToArrayWithSufficientlyLargeArray()`
- `void testToArrayWithInsufficientlyLargeArray()`
- `void testAddThrowsUnsupportedOperationException()`
- `void testRemoveWithNullThrowsException()`
- `void testRemove()`
- `void testRemoveWithDuplicates()`
- `void testContainsAllWithNullThrowsException()`
- `void testContainsAll()`
- `void testAddAllThrowsUnsupportedOperationException()`
- `void testRemoveAllWithNullThrowsException()`
- `void testRemoveAllWithSharedElements()`
- `void testRemoveAllWithNoSharedElements()`
- `void testRetainAllWithNullThrowsException()`
- `void testRetainAllWithIncompatibleTypeThrowsClassCastException()`
- `void testRetainAllWhenCollectionIsModified()`
- `void testRetainAllWhenCollectionIsNotModified()`
- `void testRetainAllWithEmptyCollection()`
- `void testClear()`
- `void testEqualsWithEqualAndUnequalCollections()`
- `void testEqualsWithDifferentObjectTypes()`
- `void testEqualsWithDuplicates()`
- `void testEqualsWithDifferentCardinality()`
- `void testHashCode()`