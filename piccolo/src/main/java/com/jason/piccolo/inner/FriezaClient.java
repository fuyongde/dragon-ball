package com.jason.piccolo.inner;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("frieza")
public interface FriezaClient {

  @GetMapping("/frieza/api/regions/{id}")
  Object region(@PathVariable("id") Integer id);
}
