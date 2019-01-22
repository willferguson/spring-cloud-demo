package burrito.fillingservice.rest;

import burrito.fillingservice.model.BurritoFilling;
import burrito.fillingservice.service.FillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class FillingController {

    private FillingService fillingService;

    @Autowired
    public FillingController(FillingService fillingService) {
        this.fillingService = fillingService;
    }

    @PostMapping(value = "/filling/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BurritoFilling orderFilling(@RequestBody BurritoFilling burritoFilling) {
        return fillingService.orderFilling(burritoFilling);

    }
}

