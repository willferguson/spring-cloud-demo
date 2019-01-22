// Copyright (c) 2019 Travelex Ltd

package burrito.fillingservice.client;

import burrito.fillingservice.model.salad.Salad;
import com.netflix.config.ConfigurationManager;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

@Component
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

    public Observable<Salad> getSalad(Salad salad) {
        return Observable.fromCallable(() -> {
            logger.debug("Ordering salad " + salad);
            return restTemplate.postForObject("/salad/", salad, Salad.class);
        });
    }
}
