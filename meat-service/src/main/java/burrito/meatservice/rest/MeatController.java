package burrito.meatservice.rest;

import burrito.meatservice.service.MeatService;
import burrito.meatservice.model.Meat;
import burrito.meatservice.model.MeatSize;
import burrito.meatservice.model.MeatType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/meat/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Meat getMeat(@RequestBody Meat meat) {
        return getMeatList(Collections.singletonList(meat)).get(0);
    }

    @PostMapping(value = "/meats", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Meat> getMeatList(@RequestBody List<Meat> meatList) {
        return meatList
                .stream()
                .map(meatIn -> meatService.getMeat(meatIn))
                .collect(Collectors.toList());
    }
}
