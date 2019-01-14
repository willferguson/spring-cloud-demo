package fillingservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableEurekaClient
@EnableFeignClients
@ConditionalOnProperty("eureka.client.enabled=true")
public class FillingServiceApplication {


    @Bean
    @LoadBalanced
    public RestTemplate stuffProducerRestClient() {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory("http://stuffproducer");
        return new RestTemplateBuilder().uriTemplateHandler(factory).build();
    }
    public static void main(String[] args) {
        SpringApplication.run(FillingServiceApplication.class, args);
    }

}
