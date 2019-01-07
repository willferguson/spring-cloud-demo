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

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ConsumerApplication.class)
@EnableHystrix
public class CollapsingTest {

    @Autowired
    StuffClient stuffClient;


    @Test
    public void test() {

        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        StuffClient spiedStuffClient = Mockito.spy(stuffClient);

        Stuff s1 = spiedStuffClient.getStuff(1);
        Stuff s2 = spiedStuffClient.getStuff(2);
        Stuff s3 = spiedStuffClient.getStuff(3);
        Stuff s4 = spiedStuffClient.getStuff(4);
        Stuff s5 = spiedStuffClient.getStuff(5);

        Mockito.verify(spiedStuffClient).getLotsOfStuff(Arrays.asList(1, 2, 3, 4, 5));

        assertEquals("1", s1.getId());
        assertEquals("2", s2.getId());


        context.shutdown();

    }
}
