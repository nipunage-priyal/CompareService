package com.service.impl;

import com.data.CacheTableMapping;
import com.definition.PersistanceDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.function.Function;

public class JedisServiceImpl<K, V> implements PersistanceDao<K, V> {
    Jedis jedis;

    @Inject
    public JedisServiceImpl() {
        jedis = new Jedis();
    }

    public void save(CacheTableMapping cacheMapping, K uid, V object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonInString = mapper.writeValueAsString(object);
            jedis.set(cacheMapping.cacheName() + uid.toString(), jsonInString);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public V fetch(CacheTableMapping cacheMapping, K uid, V cls, Function<V, Boolean> filter) {
        String jsonString = jedis.get(cacheMapping.cacheName() + uid.toString());
        if(jsonString == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            V retVal = (V) mapper.readValue(jsonString, cls.getClass());
            return filter == null || filter.apply(retVal) == true ? retVal : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cls;
    }

    public void delete(CacheTableMapping cacheMapping, K uid) {
        jedis.del(cacheMapping.cacheName() + uid.toString());
    }

    public void endOfExecution() {
        jedis.set("EndExecution","true");
    }
    public boolean isExecutionEnded() {
        String jsonInString = jedis.get("EndExecution");
        return (jsonInString != null && jsonInString.equals("true"));
    }
public void flushAll(){
        jedis.flushAll();
}
}
