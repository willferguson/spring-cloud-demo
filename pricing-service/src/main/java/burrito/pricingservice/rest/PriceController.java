package burrito.pricingservice.rest;

import burrito.pricingservice.model.BurritoFilling;
import burrito.pricingservice.model.PricingResult;
import burrito.pricingservice.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class PriceController {
    private PricingService pricingService;

    @Autowired
    public PriceController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PricingResult getPrice(@RequestBody BurritoFilling filling) {
        return this.pricingService.calculateBurritoQuote(filling);
    }
}
