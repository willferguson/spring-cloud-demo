package burrito.fillingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableEurekaClient
@ConditionalOnProperty(name = "eureka.client.enabled", havingValue = "true")
public class FillingServiceApplication {

    private static final String MEAT_SERVICE = "http://meatservice";
    private static final String SALAD_SERVICE = "http://saladservice";

    @Bean
    @LoadBalanced
    public RestTemplate meatServiceRestTemplate() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(MEAT_SERVICE);
        return new RestTemplateBuilder().uriTemplateHandler(factory).build();
    }

    @Bean
    @LoadBalanced
    public RestTemplate saladServiceRestTemplate() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(SALAD_SERVICE);
        return new RestTemplateBuilder().uriTemplateHandler(factory).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(FillingServiceApplication.class, args);
    }

}
