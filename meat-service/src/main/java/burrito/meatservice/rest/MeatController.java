package burrito.meatservice.rest;

import burrito.meatservice.service.MeatService;
import burrito.meatservice.model.Meat;
import burrito.meatservice.model.MeatSize;
import burrito.meatservice.model.MeatType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RefreshScope
@RestController
public class MeatController {


    private int delayTime;
    private MeatService meatService;

    public MeatController(
            @Value("${delay}") int delayTime,
            MeatService meatService) {
        this.delayTime = delayTime;
        this.meatService = meatService;
    }

    @GetMapping(value = "/meat/{type}/{size}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Meat getMeat(
            @PathVariable("type") MeatType meatType,
            @PathVariable("size") MeatSize meatSize) {

        return getMeatList(Collections.singletonList(new Meat(meatType, meatSize))).get(0);

    }

    @GetMapping(value = "/meats", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Meat> getMeatList(List<Meat> meatList) {
        return meatList
                .stream()
                .map(meatIn -> meatService.getMeat(meatIn))
                .collect(Collectors.toList());
    }
}
