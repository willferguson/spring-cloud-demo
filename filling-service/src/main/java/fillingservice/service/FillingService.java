package fillingservice.service;

import fillingservice.client.MeatClient;
import fillingservice.client.SaladClient;
import fillingservice.model.BurritoFilling;
import fillingservice.model.meat.Meat;
import fillingservice.model.salad.Salad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.functions.Func2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FillingService {

    @Autowired
    private SaladClient saladClient;
    @Autowired
    private MeatClient meatClient;

    public BurritoFilling orderFilling(BurritoFilling burritoFilling) {

        List<Meat> meatFillingRequest = burritoFilling.getMeat();
        List<Salad> saladFillingRequest = burritoFilling.getSalad();

        Observable<Meat> om = Observable.from(meatFillingRequest).flatMap(meatClient::getMeat);

        Observable<Salad> os = Observable.from(saladFillingRequest).flatMap(saladClient::getSalad);

        return Observable.
            return new BurritoFilling();
        }).toBlocking().first();
    }
}
