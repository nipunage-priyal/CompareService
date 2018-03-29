import com.data.JSONTestObject;
import com.definition.CompareService;
import com.definition.FilterService;
import com.definition.FirstService;
import com.definition.SecondService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.guice.binder.GuiceBinder;
import com.service.impl.AsyncLogicHandlerServiceImpl;
import org.junit.After;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Function;

public class CompletableFuturesTest {

    protected final ExecutorService executorService = Executors.newFixedThreadPool(10, threadFactory("Custom"));
    Injector injector = Guice.createInjector(new GuiceBinder());
    FirstService firstService = injector.getInstance(FirstService.class);
    SecondService secondService = injector.getInstance(SecondService.class);
    FilterService filterService = injector.getInstance(FilterService.class);
    CompareService compareService = injector.getInstance(CompareService.class);
    AsyncLogicHandlerServiceImpl asyncHandler = injector.getInstance(AsyncLogicHandlerServiceImpl.class);

    String serverStartTime;

    protected ThreadFactory threadFactory(String nameFormat) {
        return new ThreadFactoryBuilder().setNameFormat(nameFormat + "-%d").build();
    }

    @Test
    public void calculateAsync() throws Exception {

        serverStartTime = String.valueOf(System.currentTimeMillis());

        asyncHandler.saveToQueue();

        Function<Queue<String>, String> work = q -> {
            return q == null ? null: q.poll();
        };

        while (true) {
            String data = asyncHandler.getProcessingDataFromRedis(work);
            if (data == null) {
                return;
            }
            final ExecutorService benchPool =
                    Executors.newFixedThreadPool(4, threadFactory("obj1"));
            final ExecutorService proformaPool =
                    Executors.newFixedThreadPool(4, threadFactory("obj2"));

            final CompletableFuture<JSONTestObject> benchmarkFetch = CompletableFuture
                    .supplyAsync(
                            () -> firstService.getData(data),
                            benchPool);
            final CompletableFuture<JSONTestObject> proformaFetch = CompletableFuture
                    .supplyAsync(
                            () -> secondService.getData(secondService.getDataOneToDataTwoMap(data)),
                            proformaPool);

                    benchmarkFetch
                            .thenCombine(proformaFetch, (bench, proforma) -> compareService.compare(bench, proforma))
                            .thenCompose(l -> filterService.filter(l))
                            .thenAccept(l -> compareService.sendDataBack(l)).get();
            asyncHandler.persistProcessCompletion(serverStartTime, data);
        }
    }


@After
    public void flush(){
        System.out.print("Flushing all data");
        //persistenceService.flushAll();
}
}
