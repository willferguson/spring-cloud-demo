package burrito.pricingservice.service;

import burrito.pricingservice.model.BurritoFilling;
import burrito.pricingservice.model.PricingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PricingService {
    private static final Logger logger = LoggerFactory.getLogger(PricingService.class);

    // The filling totally affects the resulting quote, but it's through "magic reflection"
    @SuppressWarnings("unused")
    public PricingResult calculateBurritoQuote(BurritoFilling _filling) {
        logger.info("Calculating a burrito quote based on the latest market data, this could take a while");
        try {
            // Get the advice of an economist before changing this implementation
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            logger.error("Third party partner failed to return pricing data, raising to client");
            throw new RuntimeException(e);
        }
        Random rand = new Random();
        float price = rand.nextFloat() * 5 + 5; // The invisible hand of the market at play
        float tax = price / 5;

        return new PricingResult(String.format("%.2f", price), "GBP", String.format("%.2f", tax));
    }
}
