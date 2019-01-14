package fillingservice.client;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import fillingservice.model.meat.Meat;
import fillingservice.model.meat.MeatSize;
import fillingservice.model.meat.MeatType;
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
import java.util.List;

@Service
@RefreshScope
public class MeatClient {

    private RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(MeatClient.class);


    @Autowired
    public MeatClient(
            @Value("${meatclient.hystrix.timeoutInMilliseconds}") String hystrixTimeout,
            RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
        Configuration configuration = ConfigurationManager.getConfigInstance();
        configuration.setProperty("hystrix.command.getMeatList.execution.isolation.thread.timeoutInMilliseconds", hystrixTimeout);
        configuration.setProperty("hystrix.collapser.default.timerDelayInMilliseconds", "1000");
    }

    @HystrixCollapser(batchMethod = "getMeatList", scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL)
    public Observable<Meat> getMeat(Meat meat) {
        return Observable.fromCallable(() -> {
            logger.debug("Ordering meat " + meat);
            return restTemplate.postForObject("/meat", meat, Meat.class);
        });
    }

    @HystrixCommand(commandKey = "getMeatList")
    private List<Meat> getMeatList(List<Meat> meatList) {
        logger.debug("Ordering lots of meat " + meatList);
        return restTemplate.postForObject("/meats", meatList, MeatList.class);
    }

    private Meat defaultMeat(Integer size, Throwable throwable) {
        logger.debug("Falling back to default - small chicken");
        return new Meat(MeatType.CHICKEN, MeatSize.SMALL);
    }

    //Grrr Generics
    public static class MeatList extends ArrayList<Meat> {
        public MeatList() {
            super();
        }
    }
}
