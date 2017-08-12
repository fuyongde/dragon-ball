package com.jason.piccolo.rest;

import com.jason.piccolo.inner.FriezaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/regions")
public class RegionRestController {

  @Autowired
  FriezaClient friezaClient;

  @GetMapping("/{id}")
  public Object region(@PathVariable Integer id) {
    System.out.println(id);
    return friezaClient.region(id);
  }
}
