// Copyright (c) 2019 Travelex Ltd

package burrito.vegservice.service;

import burrito.vegservice.model.Salad;
import org.springframework.stereotype.Component;

@Component
public class SaladService {

    public Salad order(Salad salad) {
        //Check and decrement stock
        return salad;
    }
}
