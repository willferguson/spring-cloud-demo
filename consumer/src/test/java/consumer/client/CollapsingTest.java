package consumer.client;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import consumer.ConsumerApplication;
import consumer.model.Stuff;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import rx.Observable;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ConsumerApplication.class)
@EnableHystrix
public class CollapsingTest {

    @Autowired
    StuffClient stuffClient;


    @Test
    public void test() {


        StuffClient spiedStuffClient = Mockito.spy(stuffClient);

        Executor executor = Executors.newFixedThreadPool(5);


        executor.execute(() -> {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            stuffClient.getStuff(1);

        });
        executor.execute(() -> {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            stuffClient.getStuff(2);
        });
        executor.execute(() -> {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            stuffClient.getStuff(3);
        });
        executor.execute(() -> {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            stuffClient.getStuff(4);
        });
        executor.execute(() -> {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            stuffClient.getStuff(5);
        });


        //Mockito.verify(spiedStuffClient).getLotsOfStuff(Arrays.asList(1, 2, 3, 4, 5));

//        assertEquals(1, s1.getSize());
//        assertEquals(2, s2.getSize());


       // context.shutdown();

    }
}
