// Copyright (c) 2019 Travelex Ltd

package burrito.saladservice.service;

import burrito.saladservice.model.Salad;
import org.springframework.stereotype.Component;

@Component
public class SaladService {

    public Salad order(Salad salad) {
        //Check and decrement stock
        return salad;
    }
}
