// Copyright (c) 2019 Travelex Ltd

package burrito.fillingservice.client;

import burrito.fillingservice.model.salad.Salad;
import burrito.fillingservice.model.salad.SaladType;
import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

@Service
@RefreshScope
public class SaladClient {

    private static Logger logger = LoggerFactory.getLogger(SaladClient.class);

    private final RestTemplate restTemplate;

    @Autowired
    public SaladClient(
                    @Value("${saladclient.hystrix.timeoutInMilliseconds}") String hystrixTimeout,
                    RestTemplate saladServiceRestTemplate) {

        this.restTemplate = saladServiceRestTemplate;

        Configuration configuration = ConfigurationManager.getConfigInstance();
        configuration.setProperty("hystrix.command.getSalad.execution.isolation.thread.timeoutInMilliseconds", hystrixTimeout);
    }

    @HystrixCommand(commandKey = "getSalad", fallbackMethod = "defaultSalad")
    public Observable<Salad> getSalad(Salad salad) {
        return Observable.fromCallable(() -> {
            logger.debug("Ordering salad " + salad);
            return restTemplate.postForObject("/salad/", salad, Salad.class);
        });
    }

    public Observable<Salad> defaultSalad(Salad salad, Throwable throwable) {
        logger.warn("Failed call to Salad Service, falling back to default");
        return Observable.just(new Salad(SaladType.LETTUCE, 1));
    }
}
