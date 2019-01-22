package burrito.saladservice.rest;

import burrito.saladservice.model.Salad;
import burrito.saladservice.service.SaladService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class SaladController {

    private static Logger logger = LoggerFactory.getLogger(SaladController.class);

    private int delayTime;
    private SaladService saladService;

    public SaladController(@Value("${delay}") int delayTime, SaladService saladService) {
        this.delayTime = delayTime;
        this.saladService = saladService;
    }

    @PostMapping(value = "/salad/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Salad orderSalad(@RequestBody Salad salad) {
        logger.info("Received salad order for {}", salad);
        try {
            logger.info("Sleeping for {}", delayTime);
            Thread.sleep(delayTime);
        } catch (InterruptedException ignored) {}
        return saladService.order(salad);

    }
}
