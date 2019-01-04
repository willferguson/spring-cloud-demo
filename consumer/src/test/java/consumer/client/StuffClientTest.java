package consumer.client;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class StuffClientTest {


    @Autowired
    StuffClient stuffClient;

    private static HystrixRequestContext context;

    @BeforeAll
    public static void setup() {
        context = HystrixRequestContext.initializeContext();
    }
    @Test
    public void testCollapsing() {

        // Initialize Hystrix context



        StuffClient spiedStuffClient = Mockito.spy(stuffClient);

        spiedStuffClient.getStuff(1);
        spiedStuffClient.getStuff(2);
        spiedStuffClient.getStuff(3);

        Mockito.verify(spiedStuffClient).getLotsOfStuff(Arrays.asList(1, 2, 3));

    }

    @AfterAll
    public static void shutdown() {
        context.shutdown();
    }
}