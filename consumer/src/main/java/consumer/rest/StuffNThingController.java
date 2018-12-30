package consumer.rest;

import consumer.model.StuffNThing;
import consumer.service.StuffNThingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class StuffNThingController {

    @Autowired
    StuffNThingService stuffNThingService;

    @GetMapping(value = "/stuffnthing/{name}/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StuffNThing getStuffNThing(@PathVariable("name") String name, @PathVariable("size") Integer size) {
        return stuffNThingService.getStuffNThing(name, size);

    }
}

