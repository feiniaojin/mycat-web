package io.mycat.myweb.core.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/house")

public class HouseController {

    private Logger logger = LoggerFactory.getLogger(HouseController.class);

    @RequestMapping("/list")
    public void list() {
        logger.info("/house/list");
        return ;

    }
}

