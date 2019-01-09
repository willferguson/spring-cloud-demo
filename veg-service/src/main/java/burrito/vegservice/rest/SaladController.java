package burrito.vegservice.rest;

import burrito.vegservice.model.Salad;
import burrito.vegservice.service.SaladService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class SaladController {


    private int delayTime;
    private SaladService saladService;

    public SaladController(@Value("${delay}") int delayTime, SaladService saladService) {
        this.delayTime = delayTime;
    }

    @PostMapping(value = "/salad/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Salad orderSalad(@RequestBody Salad salad) {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException ignored) {}
        return saladService.order(salad);

    }
}
