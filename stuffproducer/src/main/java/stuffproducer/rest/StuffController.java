package stuffproducer.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import stuffproducer.model.Stuff;

import javax.print.attribute.standard.Media;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RefreshScope
@RestController
public class StuffController {


    private int delayTime;

    public StuffController(@Value("${delay}") int delayTime) {
        this.delayTime = delayTime;
    }

    @GetMapping(value = "/stuff/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Stuff getStuff(@PathVariable("size") Integer size) {
        return getLotsOfStuff(Collections.singletonList(size)).get(0);

    }

    @GetMapping(value = "lotsofStuff/{sizes}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Stuff> getLotsOfStuff(@PathVariable("sizes") List<Integer> sizes) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return sizes
                .stream()
                .map(i -> new Stuff(UUID.randomUUID().toString(), i))
                .collect(Collectors.toList());
    }
}
