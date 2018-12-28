// Copyright (c) 2018 Travelex Ltd

package consumer.service;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class GreetingService {

    @Value("${dummyValue}")
    String hystrixTimeout = "1500";

    @Value("${delayTime}")
    int delayTime;

    public GreetingService() {
        ConfigurationManager
            .getConfigInstance()
            .setProperty("hystrix.command.GetGreeting.execution.isolation.thread.timeoutInMilliseconds", hystrixTimeout);

    }

    @HystrixCommand(
                    commandKey = "GetGreeting",
                    fallbackMethod = "defaultGreeting")
    public String getGreeting(String username) {
        slow();
        return new RestTemplate()
                        .getForObject("http://localhost:9090/greeting/{username}",
                                      String.class, username);
    }

    private void slow() {

        try {

            Thread.sleep(  delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String defaultGreeting(String username) {
        return "Hello User!";
    }
}
