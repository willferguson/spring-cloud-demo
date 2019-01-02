package consumer.client;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import consumer.model.Stuff;
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

    @Autowired
    private RestTemplate restTemplate;

    public StuffClient(@Value("${stuffclient.hystrix.timeoutInMilliseconds}") String hystrixTimeout) {
        ConfigurationManager
                .getConfigInstance()
                .setProperty("hystrix.command.GetStuff.execution.isolation.thread.timeoutInMilliseconds", hystrixTimeout);

    }


    @HystrixCommand(
            commandKey = "GetStuff",
            fallbackMethod = "defaultStuff")
    @HystrixCollapser(batchMethod = "getLotsOfStuff")
    public Stuff getStuff(Integer size) {
        return restTemplate
                .getForObject("http://stuffproducer/stuff/{size}",
                        Stuff.class, size);
    }

    private Stuff defaultStuff(Integer size, Throwable throwable) {
        return new Stuff("-1", 0);
    }

    @HystrixCommand
    public List<Stuff> getLotsOfStuff(List<Integer> sizes) {
        return restTemplate
                .getForObject("http://stuffproducer/lotsofStuff/{sizes}",
                        StuffList.class, sizes);
    }

    //Grrr Generics
    private class StuffList extends ArrayList<Stuff> {}
}
