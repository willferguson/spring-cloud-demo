package consumer.client;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import consumer.model.Stuff;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class StuffClient {

    public StuffClient(@Value("${stuffclient.hystrix.timeoutInMilliseconds}") String hystrixTimeout) {
        ConfigurationManager
                .getConfigInstance()
                .setProperty("hystrix.command.GetStuff.execution.isolation.thread.timeoutInMilliseconds", hystrixTimeout);

    }

    @HystrixCommand(
            commandKey = "GetStuff",
            fallbackMethod = "defaultStuff")
    public Stuff getStuff(Integer size) {
        return new RestTemplate()
                .getForObject("http://localhost:9091/stuff/{size}",
                        Stuff.class, size);
    }

    private Stuff defaultStuff(Integer size) {
        return new Stuff("-1", 0);
    }
}
