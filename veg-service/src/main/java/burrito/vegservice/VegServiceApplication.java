package burrito.vegservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VegServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VegServiceApplication.class, args);
    }

}
