package com.jason.kuririn.rest;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/api/regions")
public class RegionRestController {

    private final static String FRIEZA = "frieza";

    @Autowired
    RegionService regionService;

    @GetMapping("/{id}")
    public Object region(@PathVariable Integer id) {
        return regionService.consumer(id);
    }

    @Service
    class RegionService {

        @Autowired
        RestTemplate restTemplate;

        @HystrixCommand(fallbackMethod = "fallback", commandProperties={
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
        })
        public Object consumer(Integer id) {
            return restTemplate.getForObject("http://" + FRIEZA + "/frieza/api/regions/" + id, Object.class);
        }

        public String fallback(Integer id) {
            return "fallback";
        }
    }

}
