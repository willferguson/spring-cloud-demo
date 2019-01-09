package fillingservice.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.config.enabled=false",
        "spring.cloud.config.discovery.enabled=false",
        "spring.cloud.service-registry.auto-registration.enabled=false"})
@EnableHystrix
@EnableAspectJAutoProxy
class MeatClientTest {

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
        public MeatClient stuffClient(RestTemplate restTemplate) {
            return new MeatClient("1000", restTemplate);
        }

    }

    @Autowired
    private MockRestServiceServer server;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MeatClient stuffClient;

    @BeforeAll
    public static void setUp() throws Exception {
        context = HystrixRequestContext.initializeContext();
    }

    static RequestMatcher pathStartsWith(String path) {
        return request -> AssertionErrors.assertTrue("Path " + request.getURI().getPath() + " should start with " + path, request.getURI().getPath().startsWith(path));
    }

    private static HystrixRequestContext context;




    @Test
    public void testCollapsing() throws Exception {

        Stuff responseStuff1 = new Stuff("1", 1);
        Stuff responseStuff2 = new Stuff("2", 2);

        String response = objectMapper.writeValueAsString(Arrays.asList(responseStuff1, responseStuff2));

        this.server.expect(ExpectedCount.manyTimes(), pathStartsWith("/lotsofStuff"))
                   .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));


        Observable<Stuff> s1 = stuffClient.getStuffCollapsed(1);
        Observable<Stuff> s2 = stuffClient.getStuffCollapsed(2);

        List<Stuff> stuffList = s1.mergeWith(s2).toList().toBlocking().first();
        assertEquals(2, stuffList.size());


    }

    @AfterAll
    public static void shutdown() {
        context.shutdown();
    }
}