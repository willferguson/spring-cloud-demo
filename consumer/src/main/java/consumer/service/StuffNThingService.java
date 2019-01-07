package consumer.service;

import consumer.client.StuffClient;
import consumer.model.StuffNThing;
import consumer.client.ThingClient;
import consumer.model.Stuff;
import consumer.model.Thing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StuffNThingService {


    @Autowired
    private ThingClient thingClient;
    @Autowired
    private StuffClient stuffClient;

    public StuffNThing getStuffNThing(String name, Integer size) {
        Thing thing = thingClient.getThing(name);
        Stuff stuff = stuffClient.getStuff(size).toBlocking().first();

        return new StuffNThing(UUID.randomUUID().toString(), stuff.getSize(), thing.getName());
    }


}
