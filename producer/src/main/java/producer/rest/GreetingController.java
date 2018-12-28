// Copyright (c) 2018 Travelex Ltd

package producer.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greeting/{name}")
    public String greeting(@PathVariable("name") String name) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
        return "Hello " + name;
    }
}
