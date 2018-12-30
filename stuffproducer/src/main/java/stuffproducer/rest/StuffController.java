package stuffproducer.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import stuffproducer.model.Stuff;

import java.util.UUID;

@RefreshScope
@RestController
public class StuffController {


    private int delayTime;

    public StuffController(@Value("${delay}") int delayTime) {
        this.delayTime = delayTime;
    }

    @GetMapping(value = "/stuff/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Stuff getStuff(@PathVariable("size") Integer size) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException ignored) {
        }
        return new Stuff(UUID.randomUUID().toString(), size);

    }
}
