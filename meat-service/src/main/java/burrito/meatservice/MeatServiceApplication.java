package burrito.meatservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MeatServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeatServiceApplication.class, args);
    }

}
