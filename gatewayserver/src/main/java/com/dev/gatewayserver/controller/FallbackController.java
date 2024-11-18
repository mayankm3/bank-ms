package com.dev.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    /**
     * General error, in case if any service is down
     * @return
     */
    @RequestMapping("/contactSupport")
    public Mono<String> contactSupport(){
        return Mono.just("An error occurred. Please try after some time or contact support team!!!");
    }
}
