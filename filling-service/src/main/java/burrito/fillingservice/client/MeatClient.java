package burrito.fillingservice.client;

import burrito.fillingservice.model.meat.Meat;
import burrito.fillingservice.model.meat.MeatSize;
import burrito.fillingservice.model.meat.MeatType;
import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RefreshScope
public class MeatClient {

    private RestTemplate restTemplate;

    private static Logger logger = LoggerFactory.getLogger(MeatClient.class);

    @Autowired
    public MeatClient(
            @Value("${meatclient.hystrix.timeoutInMilliseconds}") String hystrixTimeout,
            RestTemplate meatServiceRestTemplate) {

        this.restTemplate = meatServiceRestTemplate;

        Configuration configuration = ConfigurationManager.getConfigInstance();
        configuration.setProperty("hystrix.command.getMeatList.execution.isolation.thread.timeoutInMilliseconds", hystrixTimeout);
        configuration.setProperty("hystrix.collapser.default.timerDelayInMilliseconds", "100");
    }

    @HystrixCollapser(batchMethod = "getMeatList", scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL)
    public Observable<Meat> getMeat(Meat meat) {
        return Observable.fromCallable(() -> {
            logger.debug("Ordering meat " + meat);
            return restTemplate.postForObject("/meat/", meat, Meat.class);
        });
    }

    @HystrixCommand(commandKey = "getMeatList", fallbackMethod = "defaultMeat")
    private List<Meat> getMeatList(List<Meat> meatList) {
        logger.debug("Ordering lots of meat " + meatList);
        return restTemplate.postForObject("/meats/", meatList, MeatList.class);
    }

    private List<Meat> defaultMeat(List<Meat> meat, Throwable throwable) {
        logger.debug("Error {}. Falling back to default - small chicken", throwable.getMessage());
        return Collections.singletonList(new Meat(MeatType.CHICKEN, MeatSize.SMALL));
    }

    //Grrr Generics
    public static class MeatList extends ArrayList<Meat> {
        public MeatList() {
            super();
        }
    }
}
