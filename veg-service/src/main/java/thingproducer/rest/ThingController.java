package thingproducer.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import thingproducer.model.Thing;

import java.util.UUID;

@RefreshScope
@RestController
public class ThingController {


    private int delayTime;

    public ThingController(@Value("${delay}") int delayTime) {
        this.delayTime = delayTime;
    }

    @GetMapping(value = "/thing/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Thing getThing(@PathVariable("name") String name) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException ignored) {
        }
        return new Thing(UUID.randomUUID().toString(), name);

    }
}
