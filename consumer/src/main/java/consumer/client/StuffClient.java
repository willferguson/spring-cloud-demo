package consumer.client;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import consumer.model.Stuff;
import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RefreshScope
public class StuffClient {

    private RestTemplate restTemplate;

    @Autowired
    public StuffClient(
            @Value("${stuffclient.hystrix.timeoutInMilliseconds}") String hystrixTimeout,
            RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        Configuration configuration = ConfigurationManager
                .getConfigInstance();

        configuration.setProperty("hystrix.command.getStuff.execution.isolation.thread.timeoutInMilliseconds", hystrixTimeout);
        configuration.setProperty("hystrix.command.getLotsOfStuff.execution.isolation.thread.timeoutInMilliseconds", hystrixTimeout);
        configuration.setProperty("hystrix.collapser.default.timerDelayInMilliseconds", "1000");
    }

    @HystrixCommand(commandKey = "getStuff", fallbackMethod = "defaultStuff")
    public Stuff getStuff(Integer size) {
        return getStuffInternal(size);
    }



    @HystrixCollapser(
            batchMethod = "getLotsOfStuff",
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL
    )
    private Stuff getStuffInternal(Integer size) {
        return restTemplate
                        .getForObject("/stuff/{size}",
                                      Stuff.class, size);
    }

    @HystrixCommand(commandKey = "getLotsOfStuff")
    public List<Stuff> getLotsOfStuff(List<Integer> sizes) {
        String csv = sizes.stream()
                          .map(Object::toString)
                          .reduce((s1, s2) -> s1 + "," + s2)
                          .orElseThrow(() -> new RuntimeException("Should have received data :("));

        return restTemplate
                .getForObject("/lotsofStuff/{sizes}",
                        StuffList.class, csv);
    }

    private Stuff defaultStuff(Integer size, Throwable throwable) {
        return new Stuff("-1", 0);
    }

    //Grrr Generics
    public static class StuffList extends ArrayList<Stuff> {
        public StuffList() {
            super();
        }

    }
}
