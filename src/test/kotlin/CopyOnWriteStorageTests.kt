import zone.bi.triplan.rule.storage.impl.CopyOnWriteStorage

class CopyOnWriteStorageTests : StorageTests(storage = CopyOnWriteStorage())