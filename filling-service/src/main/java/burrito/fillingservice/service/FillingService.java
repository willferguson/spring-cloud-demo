package burrito.fillingservice.service;

import burrito.fillingservice.client.SaladClient;
import burrito.fillingservice.model.BurritoFilling;
import burrito.fillingservice.model.meat.Meat;
import burrito.fillingservice.model.salad.Salad;
import burrito.fillingservice.client.MeatClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.functions.Func0;

import java.util.ArrayList;

@Service
public class FillingService {

    private static Logger logger = LoggerFactory.getLogger(FillingService.class);

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
                (meatList, saladList) -> new BurritoFilling(meatList, saladList))
                .doOnError(error -> logger.error("Error fetching filling", error))
                .toBlocking()
                .single();
    }
}
