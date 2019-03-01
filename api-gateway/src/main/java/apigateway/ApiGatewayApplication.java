package apigateway;

import apigateway.hystrix.HystrixWithCachingGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCircuitBreaker
@EnableEurekaClient
@ConditionalOnProperty(name = "eureka.client.enabled", havingValue = "true")
//@ConditionalOnProperty(name = "spring.cloud.gateway.discovery.locator.enabled", havingValue = "true")
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        HystrixWithCachingGatewayFilterFactory.Config config = new HystrixWithCachingGatewayFilterFactory.Config();
        config.setName("aaaaa");

        return builder.routes()
                .route("pricing", r -> r.path("/pricing")
                        .uri("lb://pricingservice")
                        // TODO this is in no way right, how do we get the dispatcher handler?
                        .filter((new HystrixWithCachingGatewayFilterFactory(null)).apply(config))
                )
                .build();

//        l.getRoutes().collectList().block().forEach(route -> route.getFilters().add(new HystrixWithCachingGatewayFilterFactory(new HystrixWithCachingGatewayFilterFactory.Config())));
    }
}
