package com.definition;

import com.data.CacheTableMapping;

import java.util.function.Function;

public interface PersistanceDao<K, V> {
    void save(CacheTableMapping cacheMapping, K uid, V object);

    void delete(CacheTableMapping cacheMapping, K uid);

    V fetch(CacheTableMapping cacheMapping, K uid, V cls, Function<V, Boolean> filter);

    void endOfExecution();
    boolean isExecutionEnded();
    void flushAll();
}
