package consumer.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import consumer.model.Stuff;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseCreator;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.config.enabled=false",
        "spring.cloud.config.discovery.enabled=false",
        "spring.cloud.service-registry.auto-registration.enabled=false"})
@EnableHystrix
@EnableAspectJAutoProxy
class StuffClientTest {

    @Configuration
    @EnableHystrix
    public static class Config {

        @Bean
        public MockRestServiceServer mockRestServiceServer(RestTemplate restTemplate) {
            return MockRestServiceServer.createServer(restTemplate);
        }
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
        @Bean
        public StuffClient stuffClient(RestTemplate restTemplate) {
            return new StuffClient("1000", restTemplate);
        }

    }

    @Autowired
    private MockRestServiceServer server;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    StuffClient stuffClient;

    @BeforeEach
    public void setUp() throws Exception {


        context = HystrixRequestContext.initializeContext();

        String testStuffAsString1 =
                objectMapper.writeValueAsString(new Stuff("1", 1));

        String testStuffAsString2 =
                objectMapper.writeValueAsString(new Stuff("2", 2));

        this.server.expect(requestTo("/stuff/1"))
                .andRespond(request -> {
//                    try {
//                        Thread.sleep(10000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    return withSuccess(testStuffAsString1, MediaType.APPLICATION_JSON).createResponse(request);
                });

        this.server.expect(ExpectedCount.manyTimes(), requestTo("/stuff/2"))
                .andRespond(withSuccess(testStuffAsString2, MediaType.APPLICATION_JSON));
    }

    private static HystrixRequestContext context;




    @Test
    public void testCollapsing() {




        StuffClient spiedStuffClient = Mockito.spy(stuffClient);

        Stuff s1 = spiedStuffClient.getStuff(1);
        Stuff s2 = spiedStuffClient.getStuff(2);
        Stuff s3 = spiedStuffClient.getStuff(2);
        Stuff s4 = spiedStuffClient.getStuff(2);
        Stuff s5 = spiedStuffClient.getStuff(2);

        //Mockito.verify(spiedStuffClient).getLotsOfStuff(Arrays.asList(1, 2));

        assertEquals("1", s1.getId());
        assertEquals("2", s2.getId());

    }

    @AfterAll
    public static void shutdown() {
        context.shutdown();
    }
}