package consumer.client;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import consumer.model.Thing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class ThingClient {

    public ThingClient(@Value("${thingclient.hystrix.timeoutInMilliseconds}") String hystrixTimeout) {
        ConfigurationManager
            .getConfigInstance()
            .setProperty("hystrix.command.GetThing.execution.isolation.thread.timeoutInMilliseconds", hystrixTimeout);

    }

    @HystrixCommand(
                    commandKey = "GetThing",
                    fallbackMethod = "defaultThing")
    public Thing getThing(String name) {
        return new RestTemplate()
                        .getForObject("http://localhost:9090/thing/{name}",
                                      Thing.class, name);
    }

    private Thing defaultThing(String name) {
        return new Thing("-1", "default");
    }
}
