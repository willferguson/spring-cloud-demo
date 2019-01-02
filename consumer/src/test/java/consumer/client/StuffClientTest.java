package consumer.client;

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

    @Bean
    public StuffClient StuffClient() {
        return new StuffClient("2500");
    }

    @Autowired
    StuffClient stuffClient;

    @Test
    public void testCollapsing() {

        StuffClient spiedStuffClient = Mockito.spy(stuffClient);

        spiedStuffClient.getStuff(1);
        spiedStuffClient.getStuff(2);
        spiedStuffClient.getStuff(3);

        Mockito.verify(spiedStuffClient).getLotsOfStuff(Arrays.asList(1, 2, 3));

    }
}