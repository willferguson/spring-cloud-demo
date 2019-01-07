package consumer.client;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import consumer.model.Stuff;
import org.apache.commons.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@RefreshScope
public class StuffClient {

    private RestTemplate restTemplate;

    ExecutorService executor = Executors.newFixedThreadPool(10);

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

    @HystrixCollapser(
                    batchMethod = "getLotsOfStuff",
                    scope = com.netflix.hystrix.HystrixCollapser.Scope.REQUEST
    )
    public Observable<Stuff> getStuffCollapsed(Integer size) {
        return getStuffInternal(size);
    }

    @HystrixCommand(commandKey = "getStuff")
    public Observable<Stuff> getStuff(Integer size) {
        return getStuffInternal(size);
    }

    private Observable<Stuff> getStuffInternal(Integer size)  {
        return Observable.just(restTemplate.getForObject("/stuff/{size}", Stuff.class, size));

    }

    @HystrixCommand(commandKey = "getLotsOfStuff")
    public List<Stuff> getLotsOfStuff(List<Integer> sizes) {
        System.out.println(Thread.currentThread().getId() + " Lots of stuff " + sizes);
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
