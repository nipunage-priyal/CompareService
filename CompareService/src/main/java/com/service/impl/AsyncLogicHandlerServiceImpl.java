package com.service.impl;

import com.data.CacheTableMapping;
import com.definition.PersistanceDao;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.guice.binder.GuiceBinder;
import org.redisson.Redisson;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.IntStream;

public class AsyncLogicHandlerServiceImpl {

    Injector injector = Guice.createInjector(new GuiceBinder());
    PersistanceDao persistenceService = injector.getInstance(PersistanceDao.class);

    public String getProcessingDataFromRedis(Function<Queue<String>, String> work) {

        final RedissonClient redisson = Redisson.create();

        System.out.print("Acquiring lock");
        final RReadWriteLock lock = redisson.getReadWriteLock(CacheTableMapping.PROCESS_QUEUE.name().toString());

        while (!lock.readLock().tryLock()) ;
        lock.readLock().lock();

        Queue queue = (Queue) persistenceService.fetch(CacheTableMapping.PROCESS_QUEUE, CacheTableMapping.PROCESS_QUEUE.name(), new LinkedList<String>(), null);

        // Work updates the queue
        String retVal = work.apply(queue);
        if (queue == null || queue.size() == 0) {
            persistenceService.delete(CacheTableMapping.PROCESS_QUEUE, CacheTableMapping.PROCESS_QUEUE.name());
            persistenceService.endOfExecution();
        } else {
            persistenceService.save(CacheTableMapping.PROCESS_QUEUE, CacheTableMapping.PROCESS_QUEUE.name(), queue);
        }
        lock.readLock().unlock();

        return retVal;
    }

    public void saveToQueue() {

        Queue queue = (Queue) persistenceService.fetch(CacheTableMapping.PROCESS_QUEUE, CacheTableMapping.PROCESS_QUEUE.name(), new LinkedList<String>(), null);

        if(queue == null && !persistenceService.isExecutionEnded()){
            Queue<String> q = new LinkedList<>();
            IntStream.range(0, 200).forEach(
                    nbr -> {
                        String s = "data" + nbr;
                        q.add(s);
                    }
            );
            persistenceService.save(CacheTableMapping.PROCESS_QUEUE, CacheTableMapping.PROCESS_QUEUE.name(), q);
        }
    }
public void persistProcessCompletion(String serverStartTime, String obj){
    persistenceService.save(CacheTableMapping.PROCESSED, serverStartTime+obj, "Done");
}
}
