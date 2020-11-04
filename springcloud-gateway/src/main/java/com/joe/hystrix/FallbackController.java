package com.joe.hystrix;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName FallbackController
 * @Desc TODO   网关断路器
 * @Version 1.0
 */
@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public String fallback() {
        return "I'm Spring Cloud Gateway fallback.";
    }

}