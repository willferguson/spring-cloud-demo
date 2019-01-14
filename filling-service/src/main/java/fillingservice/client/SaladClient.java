// Copyright (c) 2019 Travelex Ltd

package fillingservice.client;

import fillingservice.model.salad.Salad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import rx.Observable;

@Component
public class SaladClient {

    Logger logger = LoggerFactory.getLogger(SaladClient.class);

    public Observable<Salad> getSalad(Salad salad) {
        return null;
    }
}
