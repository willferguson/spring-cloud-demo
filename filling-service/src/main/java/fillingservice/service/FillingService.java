package fillingservice.service;

import fillingservice.client.MeatClient;
import fillingservice.client.SaladClient;
import fillingservice.model.BurritoFilling;
import fillingservice.model.meat.Meat;
import fillingservice.model.salad.Salad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func2;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FillingService {

    private SaladClient saladClient;
    private MeatClient meatClient;

    @Autowired
    public FillingService(MeatClient meatClient, SaladClient saladClient) {
        this.meatClient = meatClient;
        this.saladClient = saladClient;
    }

    public BurritoFilling orderFilling(BurritoFilling burritoFilling) {

        return Observable.zip(
                Observable
                        .from(burritoFilling.getMeat())
                        .flatMap(meatClient::getMeat)
                        .collect((Func0<ArrayList<Meat>>) ArrayList::new, ArrayList::add),

                Observable
                        .from(burritoFilling.getSalad())
                        .flatMap(saladClient::getSalad)
                        .collect((Func0<ArrayList<Salad>>) ArrayList::new, ArrayList::add),
                BurritoFilling::new)
                .toBlocking()
                .single();
    }
}
